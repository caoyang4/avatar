package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;

import java.util.concurrent.Future;

/**
 * 负责流程模块的调度执行
 *
 * @author Jie.li.sh
 * @create 2023-02-20
 **/
public interface Scheduler {
    /**
     * 执行调度任务
     *
     * @param processTemplate 处理器模板
     * @return {@link Future}<{@link Response}>
     */
    Future<Response> dispatch(ProcessTemplate processTemplate);

    /**
     * 执行调度任务, 被审批、回调等事件触发
     *
     * @param processTemplate 流程模板
     * @param schedulerEventContext  调度事件
     * @return {@link Future}<{@link Response}>
     */
    Future<Response> dispatch(ProcessTemplate processTemplate, SchedulerEventContext schedulerEventContext);
}
