package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditNodeEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeUpdateRequest;

import java.util.List;

/**
 * 流程审核节点数据管理对象
 *
 * @author zhaozhifan02
 */
public interface FlowAuditNodeRepository {

    /**
     * 添加审核节点
     *
     * @param request {@link FlowAuditNodeAddRequest}
     * @return boolean
     */
    boolean addAuditNode(FlowAuditNodeAddRequest request);

    /**
     * 更新审核节点
     *
     * @param request {@link FlowAuditNodeUpdateRequest}
     * @return boolean
     */
    boolean updateAuditNode(FlowAuditNodeUpdateRequest request);

    /**
     * 基于流程ID查询审核节点
     *
     * @param flowId 流程ID
     * @return boolean
     */
    List<FlowAuditNodeEntity> queryAuditNode(Integer flowId);
}
