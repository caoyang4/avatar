package com.sankuai.avatar.workflow.core.engine.scheduler.event;

import com.sankuai.avatar.workflow.core.context.FlowState;
import lombok.Getter;

/**
 * 调度器事件
 *
 * @author kui.xu
 * @date 2023/02/21
 */
public enum SchedulerEventEnum {

    /**
     * 预检警告确认事件
     */
    PRE_CHECK_CONFIRM("PRE_CHECK_CONFIRM", "预检警告确认", FlowState.PRE_CHECK_WARNING),

    /**
     * 预检警告取消事件
     */
    PRE_CHECK_CANCEL("PRE_CHECK_CANCEL", "预检取消", FlowState.PRE_CHECK_WARNING),


    /**
     * 审核通过
     */
    AUDIT_ACCEPTED("AUDIT_ACCEPTED", "审核通过", FlowState.AUDITING),

    /**
     * 审核拒绝
     */
    AUDIT_REJECTED( "AUDIT_REJECTED", "审核驳回", FlowState.AUDITING),

    /**
     * 审核撤销
     */
    AUDIT_CANCELED("AUDIT_CANCELED", "审核撤销", FlowState.AUDITING),



    /**
     * ExecuteFlowProcess 是异步执行, 当atom执行结束以后触发回调
     */
    EXECUTE_CALLBACK("EXECUTE_CALLBACK", "执行回调", FlowState.EXECUTE_PENDING),

    /**
     * atom执行失败了，重试执行
     */
    EXECUTE_RETRY("EXECUTE_RETRY", "atom重试", FlowState.EXECUTE_FAILED);



    SchedulerEventEnum(String state, String statusName, FlowState flowState) {
        this.state = state;
        this.statusName = statusName;
        this.flowState = flowState;
    }

    /**
     * 状态
     */
    @Getter
    private final String state;

    /**
     * 状态名称
     */
    @Getter
    private final String statusName;

    /**
     * 流程状态, 仅有该状态的流程才允许响应此事件
     */
    @Getter
    private final FlowState flowState;
}
