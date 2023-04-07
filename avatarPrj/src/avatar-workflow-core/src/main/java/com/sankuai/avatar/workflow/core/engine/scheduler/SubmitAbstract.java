package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.common.annotation.RaptorTransaction;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import com.sankuai.avatar.workflow.core.engine.process.impl.PreCheckFlowProcess;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Future;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

/**
 * @author xk
 */
public abstract class SubmitAbstract implements Submit{

    @Autowired
    private Heartbeat heartbeat;

    @Autowired
    private Loader loader;

    @Override
    @RaptorTransaction(type = "submit", name = "id")
    public Future<Response> submit(Integer flowId) {
        return this.submit(this.loader.flowId(flowId), null);
    }

    @Override
    @RaptorTransaction(type = "submit", name = "processTemplate")
    public Future<Response> submit(ProcessTemplate processTemplate) {
        return this.submit(processTemplate, null);
    }

    @Override
    @RaptorTransaction(type = "submit", name = "schedulerEventContext")
    public Future<Response> submit(ProcessTemplate processTemplate, SchedulerEventContext schedulerEventContext) {
        if (processTemplate != null) {
            return this.doSubmit(processTemplate, schedulerEventContext);
        }
        return null;
    }

    /**
     * 根据业务场景，一系列决策，提交到合适的位置
     *
     * @param processTemplate template
     * @param schedulerEventContext context for scheduler
     * @return 调度结果
     */
    private Future<Response> doSubmit(ProcessTemplate processTemplate, SchedulerEventContext schedulerEventContext) {
        if (processTemplate == null) {
            return null;
        }

        ProcessContext currentProcesses = processTemplate.getCurrentProcesses();
        FlowContext flowContext = processTemplate.getFlowContext();

        // event类型不存在: 执行模块且不为预检则写入队列
        if (!isLocalProcess(currentProcesses, schedulerEventContext)) {
            // 提交到流程队列, 清理本地心跳
            this.heartbeat.setHeartbeat(flowContext);
            if(submitMq(flowContext.getId()) || submitDb(flowContext.getId())){
                // 队列失败兜底进入本地调度
                return null;
            }
        }
        // 预检场景, 提交到本地调度执行预检
        return submitScheduler(processTemplate, schedulerEventContext);
    }

    /**
     * 是否需要为本地调度 process。默认只有预检
     *
     * @param processContext process
     * @return 是否写入队列
     */
    private Boolean isLocalProcess(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        if (schedulerEventContext == null) {
            // 预检发起并才允许本地调度
            boolean perCheck = equalsIgnoreCase(processContext.getName(), PreCheckFlowProcess.class.getSimpleName());
            return perCheck && processContext.getFlowContext().getFlowState().equals(FlowState.NEW);
        }

        // 审核确认、预检确认等本地调度
        switch (schedulerEventContext.getSchedulerEventEnum()) {
            case PRE_CHECK_CONFIRM:
            case AUDIT_ACCEPTED:
            case AUDIT_CANCELED:
            case AUDIT_REJECTED:
                return true;
            default:return false;
        }
    }

    /**
     * 提交数据库队列
     *
     * @param flowId 流id
     * @return 是否写入成功
     */
    abstract boolean submitDb(Integer flowId);

    /**
     * 提交mq队列
     *
     * @param flowId 流id
     * @return 是否写入成功
     */
    abstract boolean submitMq(Integer flowId);

    /**
     * 提交调度器直接执行
     *
     * @param processTemplate       流程模板
     * @param schedulerEventContext 调度器事件上下文
     * @return {@link Future}<{@link ?} {@link extends} {@link Response}>
     */
    abstract Future<Response> submitScheduler(ProcessTemplate processTemplate, SchedulerEventContext schedulerEventContext);
}
