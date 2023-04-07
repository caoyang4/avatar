package com.sankuai.avatar.workflow.core.auditer.chain;

import lombok.Getter;

/**
 * 流程审核链类型枚举
 *
 * @author zhaozhifan02
 */
public enum FlowAuditChainType {
    /**
     * 本地默认审核链
     */
    DEFAULT(0),

    /**
     * MCM审核链
     */
    MCM(1);

    @Getter
    private final Integer code;

    FlowAuditChainType(Integer code) {
        this.code = code;
    }

    public static FlowAuditChainType getCodeOf(Integer code) {
        for (FlowAuditChainType auditChainType : FlowAuditChainType.values()) {
            if (auditChainType.getCode().equals(code)) {
                return auditChainType;
            }
        }
        return null;
    }
}
