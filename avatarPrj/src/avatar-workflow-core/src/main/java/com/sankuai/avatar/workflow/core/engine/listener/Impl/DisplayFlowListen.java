package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.workflow.core.engine.listener.ListenFlowState;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.context.FlowPublicData;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.context.transfer.FlowDisplayTransfer;
import com.sankuai.avatar.workflow.core.display.DisplayGenerator;
import com.sankuai.avatar.workflow.core.display.model.OutputDisplay;
import com.sankuai.avatar.dao.workflow.repository.FlowDisplayRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowPublicDataUpdateRequest;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 计算流程的Display属性, 每种流程的计算方式有区别, 需要分别实现
 * 监听器触发计算动作, 异步执行
 *
 * @author zhaozhifan02
 */
@Component
@ListenFlowState(FlowState.PRE_CHECK_ACCEPTED)
public class DisplayFlowListen implements FlowListen {

    private final Map<String, DisplayGenerator> displayGenerators;

    private final FlowDisplayRepository flowDisplayRepository;

    private final FlowRepository flowRepository;


    @Autowired
    public DisplayFlowListen(Map<String, DisplayGenerator> displayGenerators,
                             FlowDisplayRepository flowDisplayRepository,
                             FlowRepository flowRepository) {
        this.displayGenerators = displayGenerators;
        this.flowDisplayRepository = flowDisplayRepository;
        this.flowRepository = flowRepository;
    }

    @Override
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        if (flowContext == null || flowState == null) {
            return;
        }
        DisplayGenerator displayGenerator = displayGenerators.get(flowContext.getTemplateName());
        if (displayGenerator == null) {
            return;
        }
        FlowDisplay flowDisplay = displayGenerator.generate(flowContext, flowState);
        if (flowDisplay == null) {
            return;
        }
        FlowDisplayAddRequest request = FlowDisplayTransfer.INSTANCE.toAddRequest(flowDisplay);
        request.setFlowId(flowContext.getId());
        flowDisplayRepository.addFlowDisplay(request);

        // 兼容V1，更新 flow 表
        flowDisplay.setOutput(OutputDisplay.builder().build());
        FlowPublicDataUpdateRequest updateRequest = FlowPublicDataUpdateRequest.builder()
                .flowId(flowContext.getId())
                .publicData(FlowPublicData.builder().display(flowDisplay).build())
                .build();
        flowRepository.updatePublicData(updateRequest);
    }
}
