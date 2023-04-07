package com.sankuai.avatar.dao.workflow.repository.impl;

import com.sankuai.avatar.dao.workflow.repository.FlowProcessRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowProcessEntity;
import com.sankuai.avatar.dao.workflow.repository.mapper.FlowProcessDOMapper;
import com.sankuai.avatar.dao.workflow.repository.model.FlowProcessDO;
import com.sankuai.avatar.dao.workflow.repository.transfer.FlowProcessTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Repository
public class FlowProcessRepositoryImpl implements FlowProcessRepository {

    private final FlowProcessDOMapper mapper;

    @Autowired
    public FlowProcessRepositoryImpl(FlowProcessDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean addFlowProcess(FlowProcessEntity flowProcessEntity) {
        if (flowProcessEntity == null) {
            return false;
        }
        FlowProcessDO flowProcessDO = FlowProcessTransfer.INSTANCE.toDO(flowProcessEntity);
        int count = mapper.insertSelective(flowProcessDO);
        boolean status = count == 1;
        if (status) {
            flowProcessEntity.setId(flowProcessDO.getId());
        }
        return status;
    }

    @Override
    public List<FlowProcessEntity> getFlowProcessByFlowId(Integer flowId) {
        if (flowId == null) {
            return Collections.emptyList();
        }
        Example example = new Example(FlowProcessDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowId", flowId);
        return queryByExample(example);
    }

    @Override
    public List<FlowProcessEntity> getFlowProcessByFlowUuid(String flowUuid) {
        if (StringUtils.isEmpty(flowUuid)) {
            return Collections.emptyList();
        }
        Example example = new Example(FlowProcessDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowUuid", flowUuid);
        return queryByExample(example);
    }

    @Override
    public boolean updateFlowProcess(FlowProcessEntity flowProcessEntity) {
        if (flowProcessEntity == null || flowProcessEntity.getId() == null) {
            return false;
        }
        FlowProcessDO flowProcessDO = FlowProcessTransfer.INSTANCE.toDO(flowProcessEntity);
        int count = mapper.updateByPrimaryKeySelective(flowProcessDO);
        return count == 1;
    }

    /**
     * 根据查询条件获取返回值
     *
     * @param example {@link Example}
     * @return List<FlowProcessEntity>
     */
    private List<FlowProcessEntity> queryByExample(Example example) {
        List<FlowProcessDO> flowProcessDOList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(flowProcessDOList)) {
            return Collections.emptyList();
        }
        return FlowProcessTransfer.INSTANCE.toEntityList(flowProcessDOList);
    }
}
