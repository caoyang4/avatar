package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.dao.es.FlowOceanusEsRepository;
import com.sankuai.avatar.dao.es.request.FlowOceanusUpdateRequest;
import com.sankuai.avatar.workflow.server.dto.es.EsFlowType;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * oceanus流程数据更新处理器
 *
 * @author zhaozhifan02
 */
@Component
public class FlowOceanusUpdateHandler implements FlowUpdateHandler {

    @Autowired
    private FlowOceanusEsRepository flowOceanusEsRepository;

    @Override
    public EsFlowType getType() {
        return EsFlowType.OCEANUS;
    }

    @Override
    public boolean update(FlowUpdateRequest request) {
        String json = GsonUtils.serialization(request.getData());
        FlowOceanusUpdateRequest updateRequest = GsonUtils.deserialization(json, FlowOceanusUpdateRequest.class);
        return flowOceanusEsRepository.update(updateRequest);
    }
}
