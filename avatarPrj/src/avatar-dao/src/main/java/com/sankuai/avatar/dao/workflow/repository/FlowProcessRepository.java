package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowProcessEntity;

import java.util.List;

/**
 * @author zhaozhifan02
 */
public interface FlowProcessRepository {

    /**
     * 写入
     *
     * @param flowProcessEntity {@link FlowProcessEntity}
     * @return boolean
     */
    boolean addFlowProcess(FlowProcessEntity flowProcessEntity);

    /**
     * 获取流程Process列表
     *
     * @param flowId 流程Id
     * @return List<FlowProcessEntity>
     */
    List<FlowProcessEntity> getFlowProcessByFlowId(Integer flowId);

    /**
     * 获取流程Process列表
     *
     * @param flowUuid 流程UUID
     * @return List<FlowProcessEntity>
     */
    List<FlowProcessEntity> getFlowProcessByFlowUuid(String flowUuid);

    /**
     * 更新 process 状态
     *
     * @param flowProcessEntity {@link FlowProcessEntity}
     * @return boolean
     */
    boolean updateFlowProcess(FlowProcessEntity flowProcessEntity);
}
