package com.sankuai.avatar.workflow.core.context;

import lombok.Getter;

/**
 * 流程状态
 *
 * @author zhaozhifan02
 */
public enum FlowState  {
    /**
     * 流程发起
     */
    NEW(0, "NEW", "NEW"),

    /**
     * 发起预检
     */
    PRE_CHECK_LAUNCHED(1, "PRE_CHECK_LAUNCHED", "发起预检"),

    /**
     * 预检通过
     */
    PRE_CHECK_ACCEPTED(2, "PRE_CHECK_ACCEPTED", "预检通过"),

    /**
     * 预检拦截
     */
    PRE_CHECK_REJECTED(3, "PRE_CHECK_REJECTED", "预检拦截"),

    /**
     * 预检预警
     */
    PRE_CHECK_WARNING(4, "PRE_CHECK_WARNING", "预检预警"),


    /**
     * 发起审核
     */
    AUDIT_LAUNCHED(11, "AUDIT_LAUNCHED", "发起审核"),

    /**
     * 审核中
     */
    AUDITING(12, "AUDITING", "审核中"),

    /**
     * 审核通过
     */
    AUDIT_ACCEPTED(12, "AUDIT_ACCEPTED", "审核通过"),

    /**
     * 审核驳回
     */
    AUDIT_REJECTED(14, "AUDIT_REJECTED", "审核驳回"),

    /**
     * 审核撤销
     */
    AUDIT_CANCELED(15, "AUDIT_CANCELED", "审核撤销"),

    /**
     * 审核忽略
     */
    AUDIT_IGNORE(15, "AUDIT_IGNORE", "审核忽略"),


    /**
     * 执行中
     */
    EXECUTING(21, "RUNNING", "执行中"),

    /**
     * 执行成功
     */
    EXECUTE_SUCCESS(22, "SUCCESS", "执行成功"),

    /**
     * 执行失败
     */
    EXECUTE_FAILED(23, "FAILED", "执行失败"),

    /**
     * 执行挂起
     */
    EXECUTE_PENDING(25, "PENDING", "执行挂起"),

    /**
     * 流程关闭
     */
    SHUTDOWN(26, "SHUTDOWN", "流程关闭");


    @Getter
    private final Integer code;

    @Getter
    private final String event;

    @Getter
    private final String statusName;

    public static FlowState getEventOf(String event) {
        for (FlowState flowState : FlowState.values()) {
            if (flowState.getEvent().equals(event)) {
                return flowState;
            }
        }
        return null;
    }

    FlowState(Integer code, String event, String statusName) {
        this.code = code;
        this.event = event;
        this.statusName = statusName;
    }

}
