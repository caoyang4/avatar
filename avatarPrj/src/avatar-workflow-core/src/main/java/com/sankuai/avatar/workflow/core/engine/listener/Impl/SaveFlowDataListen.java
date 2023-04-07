package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.dao.workflow.repository.FlowDataRepository;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataAddRequest;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 保存流程公共数据
 *
 * @author zhaozhifan02
 */
@Component
public class SaveFlowDataListen implements FlowListen {

    @Autowired
    private FlowDataRepository flowDataRepository;

    @Override
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        if (flowContext == null || flowState == null) {
            return;
        }
        if (FlowState.NEW.equals(flowState)) {
            // 写入 flowData
            String input = JsonUtil.bean2Json(flowContext.getFlowInput());
            String resource = JsonUtil.bean2Json(flowContext.getResource());
            FlowDataAddRequest request = FlowDataAddRequest.builder()
                    .flowId(flowContext.getId())
                    .resource(resource)
                    .input(input)
                    .publicData(input)
                    .build();
            flowDataRepository.addFlowData(request);
        }
    }
}
