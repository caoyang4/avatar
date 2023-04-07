package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * 流程调度器
 *
 * @author xk
 * @date 2023/02/21
 */
@Component
public class SchedulerImpl implements Scheduler {

    @Autowired
    private Execute execute;

    @Autowired
    private Heartbeat heartbeat;

    @Override
    public Future<Response> dispatch(ProcessTemplate processTemplate) {
        return this.dispatch(processTemplate, null);
    }

    @Override
    public Future<Response> dispatch(ProcessTemplate processTemplate, SchedulerEventContext schedulerEventContext) {
        if (!this.dispatchCheck(processTemplate, schedulerEventContext)) {
            // 调度前检查不通过, 丢弃流程，停止调度
            return null;
        }

        SchedulerState schedulerState = this.dispatchDecision(processTemplate);
        ProcessContext processContext = null;

        if (schedulerState.equals(SchedulerState.NEW)) {
            processContext = this.getProcessContext(processTemplate, processTemplate.getIndex());
        } else if (schedulerState.equals(SchedulerState.SUCCESS)) {
            processContext = this.moveAndGetProcessContext(processTemplate);
        } else if (schedulerState.equals(SchedulerState.PENDING) && schedulerEventContext != null) {
            // pending 状态的process允许scheduler event进入
            processContext = this.getProcessContext(processTemplate, processTemplate.getIndex());
        }

        if (processContext == null) {
            // 获取ForProcess失败, 收尾清理后退出调度
            this.dispatchClean(processTemplate);
            return null;
        } else {
            // 成功获得下一个ForProcess, 提交给执行器继续执行
            return this.submitExecute(processContext, schedulerEventContext);
        }
    }

    /**
     * 调度前检查
     *
     * @return boolean
     */
    private boolean dispatchCheck(ProcessTemplate processTemplate, SchedulerEventContext schedulerEventContext) {
        ProcessContext processContext = this.getProcessContext(processTemplate, processTemplate.getIndex());
        if (processContext == null) {
            // 当前 FlowProcess 不存在，流程模板初始化错误
            return false;
        }

        if (schedulerEventContext != null) {
            FlowContext flowContext = processContext.getFlowContext();
            if (!schedulerEventContext.getSchedulerEventEnum().getFlowState().equals(flowContext.getFlowState())) {
                // 当前流程状态不允许响应该事件, 检查不通过, 停止调度
                return false;
            }
        }

        if (!heartbeat.checkHeartbeat(processContext.getFlowContext())) {
            // 没有心跳, 设置心跳
            return heartbeat.setHeartbeat(processContext.getFlowContext());
        }
        return true;
    }

    /**
     * 调度决策
     *
     * @param processTemplate 流程模板
     * @return {@link SchedulerState}
     */
    private SchedulerState dispatchDecision(ProcessTemplate processTemplate) {
        return SchedulerState.getValue(processTemplate.getCurrentProcesses().getFlowContext().getFlowState());
    }

    /**
     * 调度结束, 做一些清理工作
     */
    private void dispatchClean(ProcessTemplate processTemplate) {
        ProcessContext processContext = this.getProcessContext(processTemplate, processTemplate.getIndex());
        // 清理心跳
        Optional.ofNullable(processContext).ifPresent(o->heartbeat.cleanHeartbeat(o.getFlowContext()));
    }

    /**
     * 移动指针到下一步 并 获取ProcessContext上下文
     *
     * @param processTemplate 流程模板
     * @return {@link ProcessContext}
     */
    private ProcessContext moveAndGetProcessContext(ProcessTemplate processTemplate) {
        ProcessContext processContext = this.getProcessContext(processTemplate, processTemplate.getIndex() + 1);
        if (processContext != null) {
            processTemplate.setIndex(processTemplate.getIndex() + 1);
            processContext.getFlowContext().setProcessIndex(processTemplate.getIndex());
        }
        return processContext;
    }

    /**
     * 获取FlowProcess上下文
     *
     * @param processTemplate 流程模板
     * @return {@link ProcessContext}
     */
    private ProcessContext getProcessContext(ProcessTemplate processTemplate, Integer index) {
        if (processTemplate.getProcesses().size() > index) {
            return processTemplate.getProcesses().get(index);
        }
        return null;
    }

    /**
     * 提交FlowProcess上下文到执行器
     *
     * @param processContext 进程上下文
     */
    private Future<Response> submitExecute(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        return this.execute.execute(processContext, schedulerEventContext);
    }
}
