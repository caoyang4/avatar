package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.es.FlowAtomEsRepository;
import com.sankuai.avatar.dao.es.request.FlowAtomUpdateRequest;
import com.sankuai.avatar.workflow.server.dto.es.EsFlowType;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 流程atom数据更新处理器
 *
 * @author zhaozhifan02
 */
@Component
public class FlowAtomUpdateHandler implements FlowUpdateHandler {

    @Autowired
    private FlowAtomEsRepository flowAtomEsRepository;

    @Override
    public EsFlowType getType() {
        return EsFlowType.ATOM;
    }

    @Override
    public boolean update(FlowUpdateRequest request) {
        String json = GsonUtils.serialization(request.getData());
        FlowAtomUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowAtomUpdateRequest.class);
        return flowAtomEsRepository.update(updateRequest);
    }

}
