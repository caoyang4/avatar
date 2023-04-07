package com.sankuai.avatar.workflow.worker.LoaderFlow;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.engine.scheduler.Submit;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 流程加载器, 根据流程id创建上下文, 并提交到调度器
 *
 * @author xk
 */
public abstract class AbstractLoaderFlow {

    @Autowired
    Submit submit;

    /**
     * 加载流程
     *
     * @param flowId 流程id
     * @return boolean
     */
    public abstract boolean loadFlowId(int flowId);

    /**
     * 提交流程掉调度器执行, 不允许重载
     *
     * @param flowId 流程id
     */
    protected final boolean submitProcessScheduler(Integer flowId) throws Exception {
        submit.submit(flowId);
        return true;
    }

    /**
     * 根据流程id创建上下文
     *
     * @param flowId 流程id
     * @return {@link FlowContext}
     * @throws Exception 流程id不存在抛出异常
     */
    private FlowContext buildFlowContext(Integer flowId) throws Exception{
        /*
        TODO
            根据流程id创建flow上下文
            流程id不存在, 就抛出异常, 把Exception异常替换为自定义的专用异常
         */
        throw  new Exception("xx");
    }
}
