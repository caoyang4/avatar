package com.sankuai.avatar.workflow.core.execute.listener;

import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;

/**
 * @author xk
 */
public interface AtomEvent {

    /**
     * 推送atom事件
     *
     * @param atomContext 原子上下文
     */
    void pushEvent(AtomContext atomContext);
}
