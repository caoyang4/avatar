package com.sankuai.avatar.workflow.core.engine.listener;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;

/**
 * 推送flow状态流转事件
 *
 * @author xk
 */
public interface PushEvent {
    /**
     * 推送流程状态扭转事件
     *
     * @param flowContext 流上下文
     * @param flowState   流动状态
     */
    void pushEvent(FlowContext flowContext, FlowState flowState);

}
