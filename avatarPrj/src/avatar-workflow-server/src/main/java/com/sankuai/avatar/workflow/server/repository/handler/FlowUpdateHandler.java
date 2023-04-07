package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.workflow.server.dto.es.EsFlowType;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;

/**
 * 流程更新处理器接口
 *
 * @author zhaozhifan02
 */
public interface FlowUpdateHandler {

    /**
     * 获取流程类型
     *
     * @return EsFlowType
     */
    EsFlowType getType();

    /**
     * 更新流程数据
     *
     * @param request 流程更新数据
     * @return boolean
     */
    boolean update(FlowUpdateRequest request);
}
