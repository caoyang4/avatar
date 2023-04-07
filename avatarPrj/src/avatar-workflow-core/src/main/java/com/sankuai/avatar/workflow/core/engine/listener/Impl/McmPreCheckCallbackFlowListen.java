package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.dianping.cat.Cat;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.context.transfer.FlowContextTransfer;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import com.sankuai.avatar.workflow.core.engine.listener.ListenFlowState;
import com.sankuai.avatar.workflow.core.engine.listener.SyncListen;
import com.sankuai.avatar.workflow.core.mcm.McmClient;
import com.sankuai.avatar.workflow.core.mcm.McmEventContext;
import com.sankuai.avatar.workflow.core.mcm.McmEventStatus;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import com.sankuai.avatar.workflow.core.mcm.response.McmEventChangeDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MCM 预检回调监听，更新 MCM 事件预检状态
 *
 * @author zhaozhifan02
 */
@Slf4j
@Component
@SyncListen
@ListenFlowState({FlowState.PRE_CHECK_ACCEPTED, FlowState.SHUTDOWN})
public class McmPreCheckCallbackFlowListen implements FlowListen {
    @Autowired
    private McmClient mcmClient;

    @Override
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        if (flowState.equals(FlowState.PRE_CHECK_ACCEPTED)) {
            // 预检通过
            doPreCheckAccept(flowContext);
        } else if (flowState.equals(FlowState.SHUTDOWN)) {
            // 预检撤销
            doPreCheckCancel(flowContext);
        }
    }

    /**
     * 执行MCM预检确认
     *
     * @param flowContext {@link FlowContext}
     */
    private void doPreCheckAccept(FlowContext flowContext) {
        McmEventChangeDetailResponse response = getPreCheckResponse(flowContext);
        // 预检预警才允许执行确认动作
        if (response == null || !McmEventStatus.WARNING.equals(response.getStatus())) {
            return;
        }
        try {
            mcmClient.preCheckConfirm(getMcmEventRequest(flowContext));
        } catch (Exception e) {
            Cat.logError(e);
            log.error("Do  Mcm preCheckConfirm catch error", e);
        }
    }

    /**
     * 执行MCM预检撤销
     *
     * @param flowContext {@link FlowContext}
     */
    private void doPreCheckCancel(FlowContext flowContext) {
        McmEventChangeDetailResponse response = getPreCheckResponse(flowContext);
        // 预检通过无法执行撤销动作
        if (response == null || McmEventStatus.PRECHECKED.equals(response.getStatus())) {
            return;
        }
        try {
            mcmClient.preCheckCancel(getMcmEventRequest(flowContext));
        } catch (Exception e) {
            Cat.logError(e);
            log.error("Do  Mcm preCheckCancel catch error", e);
        }
    }

    /**
     * 查询预检结果
     *
     * @param flowContext {@link FlowContext}
     * @return {@link McmEventChangeDetailResponse}
     */
    private McmEventChangeDetailResponse getPreCheckResponse(FlowContext flowContext) {
        McmEventChangeDetailResponse response = null;
        try {
            response = mcmClient.getEventChangeDetail(flowContext.getUuid());
        } catch (Exception e) {
            Cat.logError(e);
            log.error("Do get mcm preCheckResponse catch error", e);
        }
        return response;
    }

    /**
     * 获取MCM事件请求
     *
     * @param flowContext {@link FlowContext}
     * @return {@link McmEventRequest}
     */
    private McmEventRequest getMcmEventRequest(FlowContext flowContext) {
        McmEventContext eventContext = FlowContextTransfer.INSTANCE.toMcmEventContext(flowContext);
        return McmEventRequest.builder()
                .evenName(eventContext.getEventName())
                .mcmEventContext(eventContext)
                .build();
    }
}
