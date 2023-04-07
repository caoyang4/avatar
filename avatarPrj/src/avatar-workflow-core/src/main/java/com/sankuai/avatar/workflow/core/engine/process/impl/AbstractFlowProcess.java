package com.sankuai.avatar.workflow.core.engine.process.impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.listener.PushEvent;
import com.sankuai.avatar.workflow.core.engine.process.FlowProcess;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.Submit;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * FlowProcess 抽象模块, 增加一些列通用函数
 *
 * @author Jie.li.sh
 * @create 2022-10-12
 **/
public abstract class AbstractFlowProcess implements FlowProcess {

    @Autowired
    private PushEvent pushEvent;

    @Autowired
    private Submit submit;

    /**
     * 设置流动状态
     *
     * @param flowContext 流程上下文
     * @param flowState   流动状态
     */
    protected final void setFlowState(FlowContext flowContext, FlowState flowState) {
        flowContext.setFlowState(flowState);
        this.pushEvent.pushEvent(flowContext, flowState);
    }

    /**
     * 处理执行结果, 更新状态, 推送事件
     *
     * @param processContext  流程上下文
     * @param processResponse 执行结果
     */
    private Response updateStateAndPushEvent(ProcessContext processContext, Response processResponse) {
        if (processResponse == null) {
            return null;
        }
        FlowState oldFlowState = processContext.getFlowContext().getFlowState();

        // 更新结果
        processContext.setResponse(processResponse);
        this.setFlowState(processContext.getFlowContext(), processResponse.getFlowState());

        if (!oldFlowState.equals(processResponse.getFlowState())) {
            // 状态变化, 提交调度器
            this.submit.submit(processContext.getProcessTemplate());
        }
        return processResponse;
    }

    @Override
    public final Response process(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        processContext.getFlowContext().setCurrentProcessContext(processContext);

        if (schedulerEventContext == null) {
            return this.updateStateAndPushEvent(processContext, this.doProcess(processContext));
        } else {
            return this.updateStateAndPushEvent(processContext, this.doEventProcess(processContext, schedulerEventContext));
        }
    }

    /**
     * 做过程
     *
     * @param processContext 进程上下文
     * @return {@link Response}
     */
    protected abstract Response doProcess(ProcessContext processContext);

    /**
     * 事件过程
     *
     * @param processContext        进程上下文
     * @param schedulerEventContext 调度器事件背景
     * @return {@link Response}
     */
    protected Response doEventProcess(ProcessContext processContext, SchedulerEventContext schedulerEventContext) {
        return null;
    }
}
