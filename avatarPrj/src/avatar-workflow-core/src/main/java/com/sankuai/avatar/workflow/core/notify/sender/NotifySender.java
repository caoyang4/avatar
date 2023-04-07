package com.sankuai.avatar.workflow.core.notify.sender;

import com.sankuai.avatar.workflow.core.notify.NotifyResult;

/**
 * 周知发送器
 *
 * @author zhaozhifan02
 */
public interface NotifySender {
    /**
     * 消息推送
     *
     * @param notifyResult NotifyResult
     */
    void send(NotifyResult notifyResult);
}
