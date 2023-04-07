package com.sankuai.avatar.dao.workflow.repository.impl;

import com.google.common.base.Preconditions;
import com.sankuai.avatar.dao.workflow.repository.FlowDisplayRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowDisplayEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowDisplayDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowDisplayDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowDisplayTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Repository
public class FlowDisplayRepositoryImpl implements FlowDisplayRepository {

    private static final String FLOW_ID_NULL_ERROR_MESSAGE = "flowId must not be null";

    private final FlowDisplayDOMapper mapper;

    @Autowired
    public FlowDisplayRepositoryImpl(FlowDisplayDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean addFlowDisplay(FlowDisplayAddRequest request) {
        Preconditions.checkNotNull(request, "请求参数不能为空");
        Preconditions.checkNotNull(request.getFlowId(), FLOW_ID_NULL_ERROR_MESSAGE);
        FlowDisplayDO flowDisplayDO = FlowDisplayTransfer.INSTANCE.addRequestToDO(request);
        int count = mapper.insertSelective(flowDisplayDO);
        boolean status = count == 1;
        if (status) {
            request.setId(flowDisplayDO.getId());
        }
        return status;
    }

    @Override
    public boolean updateFlowDisplay(FlowDisplayUpdateRequest request) {
        Preconditions.checkNotNull(request, "请求参数不能为空");
        Preconditions.checkNotNull(request.getId(), "id must not be null");
        Preconditions.checkNotNull(request.getFlowId(), FLOW_ID_NULL_ERROR_MESSAGE);
        FlowDisplayDO flowDisplayDO = FlowDisplayTransfer.INSTANCE.updateRequestToDO(request);
        int num = mapper.updateByPrimaryKeySelective(flowDisplayDO);
        return num == 1;
    }

    @Override
    public FlowDisplayEntity getFlowDisplayByFlowId(Integer flowId) {
        Preconditions.checkNotNull(flowId, FLOW_ID_NULL_ERROR_MESSAGE);
        Example example = new Example(FlowDisplayDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowId", flowId);
        List<FlowDisplayDO> flowDisplayDOList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(flowDisplayDOList)) {
            return null;
        }
        return FlowDisplayTransfer.INSTANCE.doToEntity(flowDisplayDOList.get(0));
    }
}
