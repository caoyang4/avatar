package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.dao.es.FlowSearchEsRepository;
import com.sankuai.avatar.dao.es.request.FlowSearchUpdateRequest;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.context.transfer.FlowContextTransfer;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 流程存储ES监听器
 *
 * @author zhaozhifan02
 */
@Component
public class EsStorageListen implements FlowListen {

    private final FlowSearchEsRepository flowSearchEsRepository;

    @Autowired
    public EsStorageListen(FlowSearchEsRepository flowSearchEsRepository) {
        this.flowSearchEsRepository = flowSearchEsRepository;
    }

    @Override
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        if (flowContext == null || flowState == null) {
            return;
        }
        FlowSearchUpdateRequest request = FlowContextTransfer.INSTANCE.toFlowSearchUpdateRequest(flowContext);
        flowSearchEsRepository.update(request);
    }
}
