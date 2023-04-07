package com.sankuai.avatar.workflow.core.checker.global;

import com.sankuai.avatar.workflow.core.checker.AbstractChecker;
import com.sankuai.avatar.workflow.core.context.FlowContext;

/**
 * 全局预检抽象类
 *
 * @author Jie.li.sh
 * @create 2022-11-02
 **/
public abstract class AbstractGlobalChecker extends AbstractChecker {

    /**
     * 重写跳过函数, 任何流程都被预检
     *
     * @param flowContext 流上下文
     * @return boolean
     */
    @Override
    public boolean matchCheck(FlowContext flowContext) {
        return !this.shouldSkipAppkeyUser(flowContext);
    }

}
