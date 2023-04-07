package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.dao.workflow.repository.FlowEventRepository;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventUpdateRequest;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import com.sankuai.avatar.workflow.core.engine.listener.ListenFlowState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * CloudTrail 事件上报监听器
 *
 * @author zhaozhifan02
 */
@Component
@ListenFlowState({FlowState.NEW, FlowState.EXECUTE_SUCCESS, FlowState.EXECUTE_FAILED, FlowState.SHUTDOWN})
public class CloudTrailEventFlowListen implements FlowListen {

    private final FlowEventRepository flowEventRepository;

    @Autowired
    public CloudTrailEventFlowListen(FlowEventRepository flowEventRepository) {
        this.flowEventRepository = flowEventRepository;
    }

    @Override
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        if (flowContext == null || flowState == null) {
            return;
        }
        if (FlowState.NEW.equals(flowState)) {
            // 保存事件信息
            FlowEventAddRequest request = FlowEventAddRequest
                    .builder()
                    .flowId(flowContext.getId())
                    .loginName(flowContext.getCreateUser())
                    .startTime(System.currentTimeMillis())
                    .build();
            if (flowContext.getFlowEvent() != null) {
                request.setSourceDomain(flowContext.getFlowEvent().getSourceDomain());
                request.setSourceIp(flowContext.getFlowEvent().getSourceIp());
            }
            flowEventRepository.addFlowEvent(request);
            return;
        }
        FlowEventUpdateRequest request = FlowEventUpdateRequest
                .builder()
                .flowId(flowContext.getId())
                .endTime(System.currentTimeMillis())
                .build();
        flowEventRepository.updateFlowEvent(request);
    }
}
