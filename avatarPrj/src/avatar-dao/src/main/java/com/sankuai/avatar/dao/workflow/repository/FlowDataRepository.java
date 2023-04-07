package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowDataEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowCheckResultAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataUpdateRequest;

/**
 * 流程数据管理接口
 *
 * @author zhaozhifan02
 */
public interface FlowDataRepository {
    /**
     * 新增
     *
     * @param request {@link FlowDataAddRequest}
     * @return boolean
     */
    boolean addFlowData(FlowDataAddRequest request);

    /**
     * 更新
     *
     * @param request {@link FlowDataUpdateRequest}
     * @return boolean
     */
    boolean updateFlowData(FlowDataUpdateRequest request);

    /**
     * 基于流程ID更新
     *
     * @param request {@link FlowDataUpdateRequest}
     * @return boolean
     */
    boolean updateFlowDataByFlowId(FlowDataUpdateRequest request);

    /**
     * 新增流程预检结果
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean addFlowCheckResult(FlowCheckResultAddRequest request);

    /**
     * 根据流程ID查询
     *
     * @param flowId 流程ID
     * @return {@link FlowDataEntity}
     */
    FlowDataEntity getFlowDataByFlowId(Integer flowId);
}
