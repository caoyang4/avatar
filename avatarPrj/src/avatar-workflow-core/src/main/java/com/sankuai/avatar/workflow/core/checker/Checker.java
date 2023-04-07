package com.sankuai.avatar.workflow.core.checker;

import com.sankuai.avatar.workflow.core.context.FlowContext;

/**
 * @author Jie.li.sh
 * @create 2022-11-02
 **/
public interface Checker extends Comparable<Checker> {

    /**
     * 检查执行超时时间 ms
     * @return name
     */
    Integer getTimeout();

    /**
     * 预检优先级
     * @return int
     */
    Integer getOrder();

    /**
     * 检查器匹配
     *
     * @param flowContext 流上下文
     * @return boolean
     */
    boolean matchCheck(FlowContext flowContext);

    /**
     * 执行检查
     * @param flowContext 检查请求
     * @return 执行结果
     */
    CheckResult check(FlowContext flowContext);
}
