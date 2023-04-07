package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.common.threadPool.ThreadPoolFactory;
import com.sankuai.avatar.common.utils.ApplicationContextUtil;
import com.sankuai.avatar.workflow.core.engine.exception.FlowException;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.process.FlowProcess;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 流程执行器
 *
 * @author xk
 * @date 2023/02/21
 */
@Slf4j
@Component
public class ExecuteImpl implements Execute {

    @Autowired
    private Heartbeat heartbeat;

    /**
     * 处理器执行线程池
     */
    private final ThreadPoolExecutor processExecutor = ThreadPoolFactory.factory("process");

    @Override
    public Future<Response> execute(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        return processExecutor.submit(() -> doExecute(processContext, schedulerEventContext));
    }

    /**
     * 执行FlowProcess任务
     *
     * @param processContext 进程上下文
     * @param schedulerEventContext 事件调度器
     * @return {@link Response}
     */
    private Response doExecute(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        FlowProcess process = this.getProcessBean(processContext);
        Transaction transaction = Cat.newTransaction("FlowProcess", process.getClass().getSimpleName());
        try {
            Response response = process.process(processContext, schedulerEventContext);
            transaction.setSuccessStatus();
            return response;
        } catch (Exception e) {
            transaction.setStatus(e);
            Cat.logError("Flow Execute error", e);
            this.heartbeat.cleanHeartbeat(processContext.getFlowContext());
        } finally {
            transaction.complete();
        }
        return null;
    }

    private FlowProcess getProcessBean(ProcessContext processContext) {
        try {
            return ApplicationContextUtil.getBean(processContext.getName(), FlowProcess.class);
        } catch (Exception e) {
            Cat.logError(e);
            log.error("loader FlowProcess error", e);
            throw new FlowException(String.format("获取不到Process处理: %s", processContext.getName()));
        }
    }
}
