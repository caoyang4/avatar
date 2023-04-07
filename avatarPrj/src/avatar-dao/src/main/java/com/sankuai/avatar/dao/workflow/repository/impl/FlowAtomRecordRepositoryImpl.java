package com.sankuai.avatar.dao.workflow.repository.impl;

import com.google.common.base.Preconditions;
import com.sankuai.avatar.dao.workflow.repository.FlowAtomRecordRepository;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowAtomRecordDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAtomRecordDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomRecordAddRequest;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowAtomRecordTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author zhaozhifan02
 */
@Repository
public class FlowAtomRecordRepositoryImpl implements FlowAtomRecordRepository {
    private final FlowAtomRecordDOMapper mapper;

    @Autowired
    public FlowAtomRecordRepositoryImpl(FlowAtomRecordDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean addAtomRecord(FlowAtomRecordAddRequest request) {
        Preconditions.checkNotNull(request, "请求参数不能为空");
        Preconditions.checkNotNull(request.getFlowId(), "flowId must not be null");
        FlowAtomRecordDO flowAtomRecordDO = FlowAtomRecordTransfer.INSTANCE.addRequestToDO(request);
        int count = mapper.insertSelective(flowAtomRecordDO);
        return count == 1;
    }
}
