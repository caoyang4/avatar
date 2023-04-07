package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.es.FlowSearchEsRepository;
import com.sankuai.avatar.dao.es.request.FlowSearchUpdateRequest;
import com.sankuai.avatar.workflow.server.dto.es.EsFlowType;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 流程查询数据更新处理器
 *
 * @author zhaozhifan02
 */
@Component
public class FlowSearchUpdateHandler implements FlowUpdateHandler {

    @Autowired
    private FlowSearchEsRepository flowSearchEsRepository;

    @Override
    public EsFlowType getType() {
        return EsFlowType.SEARCH;
    }

    @Override
    public boolean update(FlowUpdateRequest request) {
        String json = GsonUtils.serialization(request.getData());
        FlowSearchUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowSearchUpdateRequest.class);
        return flowSearchEsRepository.update(updateRequest);
    }

}
