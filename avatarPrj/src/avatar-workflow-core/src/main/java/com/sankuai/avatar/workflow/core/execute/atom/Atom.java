package com.sankuai.avatar.workflow.core.execute.atom;

/**
 * @author xk
 */
public interface Atom {

    /**
     * 之前执行
     */
    void beforeProcess();

    /**
     * 执行atom
     *
     * @return {@link AtomStatus}  执行结果状态
     */
    AtomStatus process();

    /**
     * 滞后执行
     */
    void afterProcess();

    /**
     * 设置atom上下文
     *
     * @param atomContext 原子上下文
     */
    void setAtomContext(AtomContext atomContext);

    /**
     * 获取重试次数
     *
     * @return {@link Integer}
     */
    Integer getRetryTimes();

    /**
     * 获取超时时间
     *
     * @return {@link Integer}
     */
    Integer getTimeout();
}
