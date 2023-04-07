package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowEventEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventUpdateRequest;

/**
 * 流程事件数据管理
 *
 * @author zhaozhifan02
 */
public interface FlowEventRepository {

    /**
     * 添加流程事件
     *
     * @param request 请求数据
     * @return boolean
     */
    boolean addFlowEvent(FlowEventAddRequest request);

    /**
     * 更新流程事件
     *
     * @param request 请求数据
     * @return boolean
     */
    boolean updateFlowEvent(FlowEventUpdateRequest request);

    /**
     * 根据流程ID获取事件参数
     *
     * @param flowId 流程ID
     * @return {@link FlowEventEntity}
     */
    FlowEventEntity getFlowEventByFlowId(Integer flowId);
}
