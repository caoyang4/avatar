package com.sankuai.avatar.dao.workflow.repository.impl;

import com.google.common.base.Preconditions;
import com.sankuai.avatar.dao.workflow.repository.FlowEventRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEventEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowEventDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowEventDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowEventTransfer;
import tk.mybatis.mapper.entity.Example;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Repository
public class FlowEventRepositoryImpl implements FlowEventRepository {

    private static final String FLOW_ID_NULL_ERROR_MESSAGE = "flowId must not be null";

    private final FlowEventDOMapper mapper;

    @Autowired
    public FlowEventRepositoryImpl(FlowEventDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean addFlowEvent(FlowEventAddRequest request) {
        Preconditions.checkNotNull(request, "请求参数不能为空");
        Preconditions.checkNotNull(request.getFlowId(), FLOW_ID_NULL_ERROR_MESSAGE);
        FlowEventDO flowEventDO = FlowEventTransfer.INSTANCE.addRequestToDO(request);
        int count = mapper.insertSelective(flowEventDO);
        boolean status = count == 1;
        if (status) {
            request.setId(flowEventDO.getId());
        }
        return status;
    }

    @Override
    public boolean updateFlowEvent(FlowEventUpdateRequest request) {
        Preconditions.checkNotNull(request, "请求参数不能为空");
        Preconditions.checkNotNull(request.getFlowId(), FLOW_ID_NULL_ERROR_MESSAGE);
        if (request.getId() == null) {
            // 主键为空，则先查询
            FlowEventEntity flowEventEntity = getFlowEventByFlowId(request.getFlowId());
            if (flowEventEntity == null) {
                return false;
            }
            request.setId(flowEventEntity.getId());
        }
        FlowEventDO flowEventDO = FlowEventTransfer.INSTANCE.updateRequestToDO(request);
        int num = mapper.updateByPrimaryKeySelective(flowEventDO);
        return num == 1;
    }

    @Override
    public FlowEventEntity getFlowEventByFlowId(Integer flowId) {
        Preconditions.checkNotNull(flowId, FLOW_ID_NULL_ERROR_MESSAGE);
        Example example = new Example(FlowEventDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowId", flowId);
        List<FlowEventDO> flowEventDOList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(flowEventDOList)) {
            return null;
        }
        return FlowEventTransfer.INSTANCE.doToEntity(flowEventDOList.get(0));
    }
}
