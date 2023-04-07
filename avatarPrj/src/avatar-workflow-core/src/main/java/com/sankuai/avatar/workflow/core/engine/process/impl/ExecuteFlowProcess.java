package com.sankuai.avatar.workflow.core.engine.process.impl;

import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventEnum;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.eventInput.ExecuteCallbackEvent;
import com.sankuai.avatar.workflow.core.execute.AtomLoader;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 执行处理器, 执行atom任务
 *
 * @author xk
 */
@Component
public class ExecuteFlowProcess extends AbstractFlowProcess {
    @Autowired
    private AtomLoader atomLoader;

    @Override
    protected Response doProcess(ProcessContext processContext) {
        this.setFlowState(processContext.getFlowContext(), FlowState.EXECUTING);
        atomLoader.loadAtomTemplate(processContext, processContext.getFlowContext());
        return null;
    }

    @Override
    public Response doEventProcess(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        if (schedulerEventContext.getSchedulerEventEnum().equals(SchedulerEventEnum.EXECUTE_CALLBACK)) {
            // atom执行结束回调事件
            return this.executeCallback(schedulerEventContext);
        } else if (schedulerEventContext.getSchedulerEventEnum().equals(SchedulerEventEnum.EXECUTE_RETRY)) {
            // atom重试事件, 重新执行
            return this.doProcess(processContext);
        }
        return null;
    }

    /**
     * atom执行回调处理
     *
     * @param schedulerEventContext 调度器事件背景
     */
    private Response executeCallback(SchedulerEventContext schedulerEventContext) {
        AtomStatus atomState = schedulerEventContext.getEventInput(ExecuteCallbackEvent.class).getAtomState();

        if (atomState.equals(AtomStatus.PENDING)) {
            // 执行挂起
            return Response.of(FlowState.EXECUTE_PENDING);
        } else if (atomState.equals(AtomStatus.FAIL)) {
            // 执行失败
            return Response.of(FlowState.EXECUTE_FAILED);
        } else {
            // 正常成功
            return Response.of(FlowState.EXECUTE_SUCCESS);
        }
    }
}
