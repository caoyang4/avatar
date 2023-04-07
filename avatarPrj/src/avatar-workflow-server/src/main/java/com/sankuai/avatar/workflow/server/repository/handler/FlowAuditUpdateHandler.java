package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.es.FlowAuditEsRepository;
import com.sankuai.avatar.dao.es.request.FlowAuditUpdateRequest;
import com.sankuai.avatar.workflow.server.dto.es.EsFlowType;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 流程审核数据处理器
 *
 * @author zhaozhifan02
 */
@Component
public class FlowAuditUpdateHandler implements FlowUpdateHandler {

    @Autowired
    private FlowAuditEsRepository flowAuditEsRepository;

    @Override
    public EsFlowType getType() {
        return EsFlowType.AUDIT;
    }

    @Override
    public boolean update(FlowUpdateRequest request) {
        String json = GsonUtils.serialization(request.getData());
        FlowAuditUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowAuditUpdateRequest.class);
        return flowAuditEsRepository.update(updateRequest);
    }
}
