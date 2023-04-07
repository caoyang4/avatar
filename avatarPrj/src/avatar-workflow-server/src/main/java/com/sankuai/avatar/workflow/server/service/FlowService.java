package com.sankuai.avatar.workflow.server.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.workflow.server.dto.flow.*;
import com.sankuai.avatar.workflow.server.exception.EsException;
import com.sankuai.avatar.workflow.server.request.FlowPageRequest;

/**
 * 流程数据读取业务层接口
 *
 * @author zhaozhifan02
 */
public interface FlowService {
    /**
     * 获取流程信息
     *
     * @param uuid UUID
     * @return {@link FlowDTO}
     */
    FlowDTO getFlowByUuid(String uuid);

    /**
     * 分页查询流程
     *
     * @param pageRequest 请求
     * @return {@link PageResponse}<{@link FlowDTO}>
     */
    PageResponse<FlowDTO> getPageFlow(FlowPageRequest pageRequest);

    /**
     * db查询流程
     *
     * @param pageRequest 请求
     * @return {@link PageResponse}<{@link FlowDTO}>
     */
    PageResponse<FlowDTO> getPageFlowByDb(FlowPageRequest pageRequest);

    /**
     * es 查询流程
     *
     * @param pageRequest 请求
     * @return {@link PageResponse}<{@link FlowDTO}>
     * @throws EsException es异常
     */
    PageResponse<FlowDTO> getPageFlowByEs(FlowPageRequest pageRequest) throws EsException;

}
