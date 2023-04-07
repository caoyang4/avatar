package com.sankuai.avatar.dao.workflow.repository.impl;

import com.google.common.base.Preconditions;
import com.sankuai.avatar.dao.workflow.repository.FlowAuditRecordRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditRecordEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowAuditRecordMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAuditRecordDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditRecordAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditRecordUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowAuditRecordTransfer;
import tk.mybatis.mapper.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * 流程审核节点数据管理实现接口
 *
 * @author zhaozhifan02
 */
@Repository
public class FlowAuditRecordRepositoryImpl implements FlowAuditRecordRepository {

    /**
     * 请求参数错误提示
     */
    private static final String REQUEST_PARAM_NULL_ERROR_MESSAGE = "请求参数不能为空";

    private final FlowAuditRecordMapper mapper;

    @Autowired
    public FlowAuditRecordRepositoryImpl(FlowAuditRecordMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public boolean addAuditRecord(FlowAuditRecordAddRequest request) {
        Preconditions.checkNotNull(request, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        Preconditions.checkNotNull(request.getAuditNodeId(), "auditNodeId must not be null");
        FlowAuditRecordDO flowAuditRecordDO = FlowAuditRecordTransfer.INSTANCE.addRequestToDO(request);
        int count = mapper.insertSelective(flowAuditRecordDO);
        boolean status = count == 1;
        if (status) {
            request.setId(flowAuditRecordDO.getId());
        }
        return status;
    }

    @Override
    public boolean updateAuditRecord(FlowAuditRecordUpdateRequest request) {
        Preconditions.checkNotNull(request, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        Preconditions.checkNotNull(request.getId(), "Id must not be null");
        FlowAuditRecordDO flowAuditRecordDO = FlowAuditRecordTransfer.INSTANCE.updateRequestToDO(request);
        int num = mapper.updateByPrimaryKeySelective(flowAuditRecordDO);
        return num == 1;
    }

    @Override
    public List<FlowAuditRecordEntity> queryAuditRecord(Integer auditNodeId) {
        if (auditNodeId == null) {
            return Collections.emptyList();
        }
        Example example = new Example(FlowAuditRecordDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("auditNodeId", auditNodeId);
        List<FlowAuditRecordDO> flowAuditRecordDOList = mapper.selectByExample(example);
        return FlowAuditRecordTransfer.INSTANCE.toEntityList(flowAuditRecordDOList);
    }
}
