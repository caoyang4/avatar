package com.sankuai.avatar.workflow.server.service;

import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;

/**
 * @author zhaozhifan02
 */
public interface FlowDataService {

    /**
     * 异步更新流程
     *
     * @param request 流程数据
     * @return boolean
     */
    boolean asyncUpdate(FlowUpdateRequest request);

    /**
     * 同步更新流程
     *
     * @param request 流程数据
     * @return boolean
     */
    boolean update(FlowUpdateRequest request);
}
