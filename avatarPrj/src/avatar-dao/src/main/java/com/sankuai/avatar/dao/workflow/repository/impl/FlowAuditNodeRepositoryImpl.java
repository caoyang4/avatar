package com.sankuai.avatar.dao.workflow.repository.impl;

import com.google.common.base.Preconditions;
import com.sankuai.avatar.dao.workflow.repository.FlowAuditNodeRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditNodeEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowAuditNodeMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAuditNodeDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowAuditNodeTransfer;
import tk.mybatis.mapper.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程审核节点数据管理实现接口
 *
 * @author zhaozhifan02
 */
@Repository
public class FlowAuditNodeRepositoryImpl implements FlowAuditNodeRepository {

    /**
     * 请求参数错误提示
     */
    private static final String REQUEST_PARAM_NULL_ERROR_MESSAGE = "请求参数不能为空";

    private final FlowAuditNodeMapper mapper;

    @Autowired
    public FlowAuditNodeRepositoryImpl(FlowAuditNodeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean addAuditNode(FlowAuditNodeAddRequest request) {
        Preconditions.checkNotNull(request, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        Preconditions.checkNotNull(request.getFlowId(), "flowId must not be null");
        FlowAuditNodeDO flowAuditNodeDO = FlowAuditNodeTransfer.INSTANCE.addRequestToDO(request);
        int count = mapper.insertSelective(flowAuditNodeDO);
        boolean status = count == 1;
        if (status) {
            request.setId(flowAuditNodeDO.getId());
        }
        return status;
    }

    @Override
    public boolean updateAuditNode(FlowAuditNodeUpdateRequest request) {
        Preconditions.checkNotNull(request, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        Preconditions.checkNotNull(request.getId(), "Id must not be null");
        FlowAuditNodeDO flowAuditNodeDO = FlowAuditNodeTransfer.INSTANCE.updateRequestToDO(request);
        int num = mapper.updateByPrimaryKeySelective(flowAuditNodeDO);
        return num == 1;
    }

    @Override
    public List<FlowAuditNodeEntity> queryAuditNode(Integer flowId) {
        if (flowId == null) {
            return Collections.emptyList();
        }
        Example example = new Example(FlowAuditNodeDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowId", flowId);
        List<FlowAuditNodeDO> flowAuditNodeDOList = mapper.selectByExample(example);
        List<FlowAuditNodeEntity> entityList = FlowAuditNodeTransfer.INSTANCE.toEntityList(flowAuditNodeDOList);
        return entityList.stream().sorted(Comparator.comparing(FlowAuditNodeEntity::getSeq)).collect(Collectors.toList());
    }
}
