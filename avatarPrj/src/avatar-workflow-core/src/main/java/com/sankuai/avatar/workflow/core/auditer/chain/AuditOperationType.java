package com.sankuai.avatar.workflow.core.auditer.chain;

import lombok.Getter;

/**
 * 审核操作类型枚举
 *
 * @author zhaozhifan02
 */
public enum AuditOperationType {
    /**
     * 通过
     */
    ACCEPT(1, "ACCEPT", "通过"),

    /**
     * 驳回
     */
    REJECT(2, "REJECT", "驳回"),

    /**
     * 撤销
     */
    CANCEL(3, "CANCEL", "撤销");

    @Getter
    private final Integer code;

    @Getter
    private final String value;

    @Getter
    private final String name;

    private AuditOperationType(Integer code, String value, String name) {
        this.code = code;
        this.value = value;
        this.name = name;
    }
}
