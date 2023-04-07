package com.sankuai.avatar.workflow.core.auditer.chain;

import lombok.Getter;

/**
 * 审核类型枚举：或签、会签
 *
 * @author zhaozhifan02
 */
public enum AuditApproveType {
    /**
     * 或签
     */
    OR(1, "OR", "或签"),

    /**
     * 会签
     */
    AND(2, "AND", "会签");

    @Getter
    private final Integer code;

    @Getter
    private final String value;

    @Getter
    private final String name;

    private AuditApproveType(Integer code, String value, String name) {
        this.code = code;
        this.value = value;
        this.name = name;
    }
}
