package com.sankuai.avatar.workflow.core.notify;

/**
 * 通知类型：用户 / 群, 调用接口不同
 */
public enum NotifyReceiverType {
    /**
     * 用户
     */
    USER("user"),
    /**
     * dx群
     */
    GROUP("group");

    NotifyReceiverType(String value) {
    }
}
