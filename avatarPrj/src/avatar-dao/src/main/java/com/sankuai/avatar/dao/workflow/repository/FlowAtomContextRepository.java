package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAtomContextEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextUpdateRequest;

import java.util.List;

/**
 * @author zhaozhifan02
 */
public interface FlowAtomContextRepository {

    /**
     * 通过流程 ID 获取 Atom 上下文
     *
     * @param flowId 流程ID
     * @return List<FlowAtomContextEntity>
     */
    List<FlowAtomContextEntity> getFlowAtomContextByFlowId(Integer flowId);

    /**
     * 写入 Atom 上下文
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean addFlowAtomContext(FlowAtomContextAddRequest request);

    /**
     * 更新 Atom 上下文
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean updateFlowAtomContext(FlowAtomContextUpdateRequest request);
}
