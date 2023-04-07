package com.sankuai.avatar.workflow.core.notify;

/**
 * 周知消息类型：常规提醒消息、审核提醒消息
 *
 * @author zhaozhifan02
 */
public enum NotifyMessageType {
    /**
     * 常规消息
     */
    NORMAL("normal"),
    /**
     * 审核消息
     */
    AUDIT("audit");

    private final String value;

    NotifyMessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
