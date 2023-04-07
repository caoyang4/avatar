package com.sankuai.avatar.workflow.core.execute.listener.atomListen;

import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;

/**
 * atom事件监听器, atom任务发生状态流程转时触发
 *
 * @author xk
 */
public interface AtomListener {

    /**
     * 新创建后触发
     *
     * @param atomContext 原子上下文
     */
    default void atomNew(AtomContext atomContext){ }

    /**
     * 被调度后触发
     *
     * @param atomContext 原子上下文
     */
    default void atomAtomScheduler(AtomContext atomContext) {}

    /**
     * 运行前触发
     *
     * @param atomContext 原子上下文
     */
    default void atomRunning(AtomContext atomContext){ }

    /**
     * 执行成功后触发
     *
     * @param atomContext 原子上下文
     */
    default void atomSuccess(AtomContext atomContext){ }

    /**
     * 进入挂起后触发
     *
     * @param atomContext 原子上下文
     */
    default void atomPending(AtomContext atomContext){ }

    /**
     * 执行失败触发
     *
     * @param atomContext 原子上下文
     */
    default void atomFail(AtomContext atomContext){ }

    /**
     * 所有状态都触发
     *
     * @param atomContext 原子上下文
     */
    default void atomAll(AtomContext atomContext) { }
}
