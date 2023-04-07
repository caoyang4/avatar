package com.sankuai.avatar.workflow.core.engine.listener;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;

/**
 * 流程监听器, 监听流程状态流转事件。注意: 默认异步执行，需要同步执行请使用对应注解
 *
 * @author xk
 */
public interface FlowListen {
    /**
     * 流程事件推送
     *
     * @param flowContext 流上下文
     * @param flowState   流动状态
     */
    void receiveEvents(FlowContext flowContext, FlowState flowState);
}
