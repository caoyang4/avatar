package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.context.FlowContext;

/**
 * @author xk
 */
public interface Heartbeat {

    /**
     * 设置心跳
     *
     * @param flowContext 流上下文
     * @return boolean
     */
    boolean setHeartbeat(FlowContext flowContext);

    /**
     * 检查心跳
     *
     * @param flowContext 流上下文
     * @return boolean
     */
    boolean checkHeartbeat(FlowContext flowContext);

    /**
     * 清理心跳
     *
     * @param flowContext 流上下文
     */
    void cleanHeartbeat(FlowContext flowContext);
}
