package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditRecordEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditRecordAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditRecordUpdateRequest;

import java.util.List;

/**
 * 流程审核操作记录数据管理对象
 *
 * @author zhaozhifan02
 */
public interface FlowAuditRecordRepository {
    /**
     * 添加审核操作记录
     *
     * @param request {@link FlowAuditRecordAddRequest}
     * @return boolean
     */
    boolean addAuditRecord(FlowAuditRecordAddRequest request);

    /**
     * 更新审核操作记录
     *
     * @param request {@link FlowAuditRecordUpdateRequest}
     * @return boolean
     */
    boolean updateAuditRecord(FlowAuditRecordUpdateRequest request);

    /**
     * 基于流程审核节点ID查询审核操作记录
     *
     * @param auditNodeId 审核节点ID
     * @return boolean
     */
    List<FlowAuditRecordEntity> queryAuditRecord(Integer auditNodeId);
}
