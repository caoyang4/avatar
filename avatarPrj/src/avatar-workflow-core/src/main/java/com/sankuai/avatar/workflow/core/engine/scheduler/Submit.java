package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;

import java.util.concurrent.Future;

/**
 * 流程调度提交, 把流程提交给调度器
 *
 * 调度器有多个入口, 如从api发起、消费流程队列发起、执行时正常扭转发起等
 * 提交有多个提交重载, 支持所有提交场景, 自动判断提交到什么什么位置(流程队列、直接调度)
 *
 * @author xk
 */
public interface Submit {

    /**
     * 已有流程, 提交调度执行
     *
     * @param flowId 流程id
     * @return {@link Future}<{@link Response}>
     */
    Future<Response> submit(Integer flowId);

    /**
     * 直接提交ProcessTemplate编排模板
     *
     * @param processTemplate 流程模板
     * @return {@link Future}<{@link Response}>
     */
    Future<Response> submit(ProcessTemplate processTemplate);

    /**
     * 调度事件场景, 提交ProcessTemplate编排模板 和 调度事件
     *
     * @param processTemplate       流程模板
     * @param schedulerEventContext 调度器事件背景
     * @return {@link Future}<{@link Response}>
     */
    Future<Response> submit(ProcessTemplate processTemplate, SchedulerEventContext schedulerEventContext);
}
