package com.sankuai.avatar.dao.workflow.repository.impl;

import com.google.common.base.Preconditions;
import com.sankuai.avatar.dao.workflow.repository.FlowAtomContextRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowAtomContextEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowAtomContextDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAtomContextDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowAtomContextTransfer;
import tk.mybatis.mapper.entity.Example;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@Repository
public class FlowAtomContextRepositoryImpl implements FlowAtomContextRepository {

    private final FlowAtomContextDOMapper mapper;

    @Autowired
    public FlowAtomContextRepositoryImpl(FlowAtomContextDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<FlowAtomContextEntity> getFlowAtomContextByFlowId(Integer flowId) {
        if (flowId == null) {
            return Collections.emptyList();
        }
        Example example = new Example(FlowAtomContextDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowId", flowId);
        List<FlowAtomContextDO> flowAtomContextDOList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(flowAtomContextDOList)) {
            return Collections.emptyList();
        }
        return FlowAtomContextTransfer.INSTANCE.toEntityList(flowAtomContextDOList);
    }

    @Override
    public boolean addFlowAtomContext(FlowAtomContextAddRequest request) {
        Preconditions.checkNotNull(request, "请求参数不能为空");
        FlowAtomContextDO flowAtomContextDO = FlowAtomContextTransfer.INSTANCE.addRequestToDO(request);
        int count = mapper.insertSelective(flowAtomContextDO);
        boolean status = count == 1;
        if (status) {
            request.setId(flowAtomContextDO.getId());
        }
        return status;
    }

    @Override
    public boolean updateFlowAtomContext(FlowAtomContextUpdateRequest request) {
        Preconditions.checkNotNull(request, "请求参数不能为空");
        Preconditions.checkNotNull(request.getId(), "id must not be null");
        FlowAtomContextDO flowAtomContextDO = FlowAtomContextTransfer.INSTANCE.updateRequestToDO(request);
        int num = mapper.updateByPrimaryKeySelective(flowAtomContextDO);
        return num == 1;
    }
}
