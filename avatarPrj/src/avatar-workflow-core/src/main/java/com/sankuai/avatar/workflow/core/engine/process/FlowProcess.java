package com.sankuai.avatar.workflow.core.engine.process;

import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;

/**
 * 处理模块：对流程状态有影响的核心操作模块
 *
 * @author Jie.li.sh
 * @create 2022-10-12
 **/
public interface FlowProcess {

    /**
     * 执行处理任务
     *
     * @param processContext        进程上下文
     * @param schedulerEventContext 调度器事件上下文
     * @return {@link Response}
     */
    Response process(ProcessContext processContext, SchedulerEventContext schedulerEventContext);
}
