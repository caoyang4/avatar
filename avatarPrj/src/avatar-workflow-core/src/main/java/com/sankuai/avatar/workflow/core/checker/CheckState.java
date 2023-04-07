package com.sankuai.avatar.workflow.core.checker;

import lombok.Getter;

/**
 * 预检状态
 *
 * @author kui.xu
 */
public enum CheckState {
    /**
     * 预检通过
     */
    PRE_CHECK_ACCEPTED(0, "PRE_CHECK_ACCEPTED", "预检通过"),

    /**
     * 预检预警
     */
    PRE_CHECK_WARNING(1, "PRE_CHECK_WARNING", "预检预警"),

    /**
     * 预检拦截
     */
    PRE_CHECK_REJECTED(2, "PRE_CHECK_REJECTED", "预检拦截");

    @Getter
    private final Integer code;

    @Getter
    private final String event;

    @Getter
    private final String statusName;

    CheckState(Integer code, String event, String statusName) {
        this.code = code;
        this.event = event;
        this.statusName = statusName;
    }
}
