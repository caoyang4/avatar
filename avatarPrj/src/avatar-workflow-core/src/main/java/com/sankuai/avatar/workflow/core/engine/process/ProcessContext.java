package com.sankuai.avatar.workflow.core.engine.process;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 进程上下文
 *
 * @author xk
 * @date 2023/02/22
 */
@Getter
@Builder
public class ProcessContext {

    /**
     * index
     */
    private Integer seq;

    /**
     * 名字
     */
    private String name;

    /**
     * 流程上下文
     */
    @Setter
    private FlowContext flowContext;

    /**
     * 编排模板
     */
    @Setter
    @JsonIgnore
    private ProcessTemplate processTemplate;

    /**
     * process调度请求，仅用于缓存当前调度请求
     */
    private SchedulerEventContext schedulerEventContext;

    /**
     * process执行结果，仅用于缓存当前调度结果
     */
    @Setter
    private Response response;
}
