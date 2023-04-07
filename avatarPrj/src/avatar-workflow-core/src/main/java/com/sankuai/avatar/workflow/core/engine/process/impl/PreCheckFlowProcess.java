package com.sankuai.avatar.workflow.core.engine.process.impl;

import com.sankuai.avatar.workflow.core.checker.CheckHandler;
import com.sankuai.avatar.workflow.core.checker.CheckResult;
import com.sankuai.avatar.workflow.core.checker.CheckState;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.response.PreCheckResult;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;

import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 预检处理器
 *
 * @author xk
 */
@Component
public class PreCheckFlowProcess extends AbstractFlowProcess {

    @Autowired
    private CheckHandler checkHandler;

    @Override
    protected Response doProcess(ProcessContext processContext) {
        // 执行预检
        this.setFlowState(processContext.getFlowContext(), FlowState.PRE_CHECK_LAUNCHED);
        List<CheckResult> checkResults = this.checkHandler.checker(processContext.getFlowContext());

        // 计算最终结果
        CheckState finalCheckState = CheckState.PRE_CHECK_ACCEPTED;
        for (CheckResult checkResult : checkResults) {
            if (checkResult.getCheckState().getCode() > finalCheckState.getCode()) {
                finalCheckState = checkResult.getCheckState();
            }
        }

        if (finalCheckState.equals(CheckState.PRE_CHECK_REJECTED)) {
            // 预检拒绝
            return Response.of(FlowState.PRE_CHECK_REJECTED, PreCheckResult.ofReject(checkResults));
        } else if (finalCheckState.equals(CheckState.PRE_CHECK_WARNING)) {
            // 预检警告
            return Response.of(FlowState.PRE_CHECK_WARNING, PreCheckResult.ofWarn(checkResults));
        } else {
            // 预检通过
            return Response.of(FlowState.PRE_CHECK_ACCEPTED, PreCheckResult.ofAccept());
        }
    }

    /**
     * 预检事件：确认、撤销
     *
     * @param processContext        进程上下文
     * @param schedulerEventContext 调度器事件背景
     * @return {@link Response}
     */
    @Override
    protected Response doEventProcess(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        if (schedulerEventContext.getSchedulerEventEnum().equals(SchedulerEventEnum.PRE_CHECK_CONFIRM)) {
            // 预检告警确认
            return Response.of(FlowState.PRE_CHECK_ACCEPTED);
        } else {
            // 预检告警取消
            return Response.of(FlowState.SHUTDOWN);
        }
    }
}
