package com.sankuai.avatar.workflow.core.execute.atom;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * atom任务的上下文，维护任务的输入、输出、状态、重试次数等信息
 *
 * @author xk
 * @date 2023/02/17
 */
@Getter
@Setter
@Builder
public class AtomContext {

    /**
     * atom对象id
     */
    private Integer id;

    /**
     * 流程Id
     */
    private Integer flowId;

    /**
     * 编排序号
     */
    private Integer seq;

    /**
     * atom名称
     */
    private String name;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * atom状态
     */
    private volatile AtomStatus atomStatus;

    /**
     * atom执行结果
     */
    private AtomResult atomResult;

    /**
     * atom编排模板
     */
    private AtomTemplate atomTemplate;

    /**
     * @return {@link AtomContext}
     */
    public static AtomContext of() {
        return AtomContext.builder().build();
    }
}
