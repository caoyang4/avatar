package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.es.FlowDataEsRepository;
import com.sankuai.avatar.dao.es.request.FlowDataUpdateRequest;
import com.sankuai.avatar.workflow.server.dto.es.EsFlowType;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 流程统计数据更新处理器
 *
 * @author zhaozhifan02
 */
@Component
public class FlowDataUpdateHandler implements FlowUpdateHandler {

    @Autowired
    private FlowDataEsRepository flowDataEsRepository;

    @Override
    public EsFlowType getType() {
        return EsFlowType.STATISTICS;
    }

    @Override
    public boolean update(FlowUpdateRequest request) {
        String json = GsonUtils.serialization(request.getData());
        FlowDataUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowDataUpdateRequest.class);
        updateRequest.setId(request.getId());
        return flowDataEsRepository.update(updateRequest);
    }

}
