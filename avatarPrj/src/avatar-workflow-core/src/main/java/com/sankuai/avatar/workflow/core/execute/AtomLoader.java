package com.sankuai.avatar.workflow.core.execute;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;

/**
 * atom加载器, 根据流程上下文加载atom编排列表
 *
 * @author xk
 */
public interface AtomLoader {

    /**
     * atom加载器, 根据flow上下文加载atom任务列表, 并提交给调度器
     *
     * @param processContext process 上下文
     * @param flowContext    flow流程上下文
     */
    void loadAtomTemplate(ProcessContext processContext, FlowContext flowContext);
}
