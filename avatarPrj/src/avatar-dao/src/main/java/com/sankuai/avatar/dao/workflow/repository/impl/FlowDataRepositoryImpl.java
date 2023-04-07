package com.sankuai.avatar.dao.workflow.repository.impl;

import com.google.common.base.Preconditions;
import com.sankuai.avatar.dao.workflow.repository.FlowDataRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowDataEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowDataDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowDataDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowCheckResultAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowDataTransfer;
import tk.mybatis.mapper.entity.Example;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Repository
public class FlowDataRepositoryImpl implements FlowDataRepository {

    private static final String REQUEST_PARAM_NULL_ERROR_MESSAGE = "请求参数不能为空";

    private static final String FLOW_ID_NULL_ERROR_MESSAGE = "flowId must not be null";

    private final FlowDataDOMapper mapper;

    @Autowired
    public FlowDataRepositoryImpl(FlowDataDOMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public boolean addFlowData(FlowDataAddRequest request) {
        Preconditions.checkNotNull(request, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        Preconditions.checkNotNull(request.getFlowId(), FLOW_ID_NULL_ERROR_MESSAGE);
        FlowDataDO flowDataDO = FlowDataTransfer.INSTANCE.addRequestToDO(request);
        int count = mapper.insertSelective(flowDataDO);
        boolean status = count == 1;
        if (status) {
            request.setId(flowDataDO.getId());
        }
        return status;
    }

    /**
     * 流程预检结果
     * @param request 请求参数
     * @return boolean
     */
    @Override
    public boolean addFlowCheckResult(FlowCheckResultAddRequest request) {
        Preconditions.checkNotNull(request, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        Preconditions.checkNotNull(request.getFlowId(), FLOW_ID_NULL_ERROR_MESSAGE);
        FlowDataDO flowDataDO = FlowDataTransfer.INSTANCE.checkResultRequestToDO(request);
        // 如果存在则更新
        FlowDataEntity flowDataEntity = getFlowDataByFlowId(request.getFlowId());
        if (flowDataEntity != null) {
            flowDataDO.setId(flowDataEntity.getId());
            int num = mapper.updateByPrimaryKeySelective(flowDataDO);
            return num == 1;
        }
        // 如果不存在则insert
        int num = mapper.insertSelective(flowDataDO);
        return num == 1;
    }

    @Override
    public boolean updateFlowData(FlowDataUpdateRequest request) {
        Preconditions.checkNotNull(request, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        Preconditions.checkNotNull(request.getId(), "id must not be null");
        Preconditions.checkNotNull(request.getFlowId(), FLOW_ID_NULL_ERROR_MESSAGE);
        FlowDataDO flowDataDO = FlowDataTransfer.INSTANCE.updateRequestToDO(request);
        int num = mapper.updateByPrimaryKeySelective(flowDataDO);
        return num == 1;
    }

    @Override
    public boolean updateFlowDataByFlowId(FlowDataUpdateRequest request) {
        Preconditions.checkNotNull(request, REQUEST_PARAM_NULL_ERROR_MESSAGE);
        Preconditions.checkNotNull(request.getFlowId(), FLOW_ID_NULL_ERROR_MESSAGE);
        FlowDataEntity flowDataEntity = getFlowDataByFlowId(request.getFlowId());
        FlowDataDO flowDataDO = new FlowDataDO();
        flowDataDO.setId(flowDataEntity.getId());
        flowDataDO.setPublicData(request.getPublicData());
        int num = mapper.updateByPrimaryKeySelective(flowDataDO);
        return num == 1;
    }

    @Override
    public FlowDataEntity getFlowDataByFlowId(Integer flowId) {
        Preconditions.checkNotNull(flowId, FLOW_ID_NULL_ERROR_MESSAGE);
        Example example = new Example(FlowDataDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowId", flowId);
        List<FlowDataDO> flowDataDOList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(flowDataDOList)) {
            return null;
        }
        return FlowDataTransfer.INSTANCE.toEntity(flowDataDOList.get(0));
    }
}
