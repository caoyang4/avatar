package com.sankuai.avatar.workflow.core.execute;

import com.sankuai.avatar.workflow.core.execute.atom.AtomTemplate;

/**
 * atom调度器
 *
 * @author xk
 */
public interface AtomScheduler {

    /**
     * 调度atom任务, 解析atomContextList指针、状态、执行结果等，调度atom
     *
     * @param atomTemplate 原子模板
     */
    void dispatch(AtomTemplate atomTemplate);

}
