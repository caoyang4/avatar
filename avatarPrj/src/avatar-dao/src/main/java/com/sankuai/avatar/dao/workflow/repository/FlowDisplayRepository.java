package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowDisplayEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayUpdateRequest;

/**
 * 流程展示信息数据管理接口
 *
 * @author zhaozhifan02
 */
public interface FlowDisplayRepository {

    /**
     * 新增流程展示信息
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean addFlowDisplay(FlowDisplayAddRequest request);

    /**
     * 更新流程展示信息
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean updateFlowDisplay(FlowDisplayUpdateRequest request);

    /**
     * 获取流程展示信息
     *
     * @param flowId 流程ID
     * @return {@link FlowDisplayEntity}
     */
    FlowDisplayEntity getFlowDisplayByFlowId(Integer flowId);
}
