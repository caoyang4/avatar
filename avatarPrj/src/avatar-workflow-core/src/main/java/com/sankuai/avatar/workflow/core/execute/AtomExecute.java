package com.sankuai.avatar.workflow.core.execute;

import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;

/**
 * atom 任务执行器
 *
 * @author xk
 */
public interface AtomExecute {

    /**
     * 执行atom任务
     *
     * @param atomContext 原子上下文
     */
    void execute(AtomContext atomContext);
}
