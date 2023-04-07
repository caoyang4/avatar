package com.sankuai.avatar.workflow.server.service;

import com.sankuai.avatar.workflow.server.dto.flow.FlowUserDTO;

import java.util.List;

/**
 * 流程用户信息接口
 * @author caoyang
 * @create 2023-02-21 19:45
 */
public interface FlowUserService {

    /**
     * 缓存批量获取用户信息
     *
     * @param misList misList
     * @return {@link List}<{@link FlowUserDTO}>
     */
    List<FlowUserDTO> getFlowUserDTO(List<String> misList);

}
