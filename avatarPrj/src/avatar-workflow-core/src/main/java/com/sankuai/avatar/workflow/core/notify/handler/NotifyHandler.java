package com.sankuai.avatar.workflow.core.notify.handler;

import com.sankuai.avatar.workflow.core.notify.NotifyRequest;

/**
 * 周知消息处理器
 *
 * @author zhaozhifan02
 */
public interface NotifyHandler {
    /**
     * 接受流程通知请求
     * @param templateType  模版类型
     * @param notifyRequest 通知消息
     */
    void handle(FlowTemplateType templateType, NotifyRequest notifyRequest);
}
