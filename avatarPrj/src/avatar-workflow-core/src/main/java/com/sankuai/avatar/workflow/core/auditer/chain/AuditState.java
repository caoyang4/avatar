package com.sankuai.avatar.workflow.core.auditer.chain;

import lombok.Getter;

/**
 * 流程审核状态
 *
 * @author zhaozhifan02
 */
public enum AuditState {
    /**
     * 审核中
     */
    AUDITING(1, "AUDITING"),
    /**
     * 审核通过
     */
    ACCEPTED(2, "ACCEPTED"),
    /**
     * 审核拒绝
     */
    REJECTED(3, "REJECTED"),

    /**
     * 审核撤销
     */
    CANCELED(4, "CANCELED");


    @Getter
    private final Integer code;

    @Getter
    private final String stateName;

    AuditState(Integer code, String stateName) {
        this.code = code;
        this.stateName = stateName;
    }

    public static AuditState getCodeOf(Integer code) {
        for (AuditState auditState : AuditState.values()) {
            if (auditState.getCode().equals(code)) {
                return auditState;
            }
        }
        return null;
    }
}
