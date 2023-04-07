package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.workflow.repository.FlowDataRepository;
import com.sankuai.avatar.dao.workflow.repository.request.FlowCheckResultAddRequest;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import com.sankuai.avatar.workflow.core.engine.listener.ListenFlowState;
import com.sankuai.avatar.workflow.core.engine.process.response.PreCheckResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 预检检查结果保存
 *
 * @author xk
 */
@Component
@ListenFlowState({FlowState.PRE_CHECK_REJECTED, FlowState.PRE_CHECK_WARNING, FlowState.PRE_CHECK_ACCEPTED})
public class PreCheckResultFlowListen implements FlowListen {

    @Autowired
    private FlowDataRepository flowDataRepository;

    @Override
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        // 获取预检结果
        PreCheckResult preCheckResult = flowContext.getCurrentProcessContext().getResponse().getResult(PreCheckResult.class);
        if (preCheckResult == null) {
            return;
        }
        // 存储结果
        flowDataRepository.addFlowCheckResult(FlowCheckResultAddRequest.builder()
                .flowId(flowContext.getId())
                .checkerResult(JsonUtil.bean2Json(flowContext.getCurrentProcessContext().getResponse()))
                .build());
    }

}
