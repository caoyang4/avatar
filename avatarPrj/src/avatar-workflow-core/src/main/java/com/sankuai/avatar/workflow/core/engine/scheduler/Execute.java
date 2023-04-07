package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;

import java.util.concurrent.Future;

/**
 * @author xk
 */
public interface Execute {

    /**
     * 执行Process实例
     *
     * @param processContext 进程上下文
     * @param schedulerEventContext 调度事件
     * @return {@link Future}<{@link Object}>
     */
    Future<Response> execute(ProcessContext processContext, SchedulerEventContext schedulerEventContext);
}
