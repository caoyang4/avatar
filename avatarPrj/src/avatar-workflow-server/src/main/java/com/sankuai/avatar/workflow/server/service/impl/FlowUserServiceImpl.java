package com.sankuai.avatar.workflow.server.service.impl;

import com.dianping.rhino.annotation.Degrade;
import com.dianping.rhino.annotation.Rhino;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.workflow.server.dto.flow.FlowUserDTO;
import com.sankuai.avatar.workflow.server.service.FlowUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * @author caoyang
 * @create 2023-02-21 20:01
 */
@Rhino
public class FlowUserServiceImpl implements FlowUserService {

    private static final String CATEGORY = "avatar-user-dx";

    private final CacheClient cache;

    @Autowired
    public FlowUserServiceImpl(CacheClient cache) {
        this.cache = cache;
    }


    @Override
    @Degrade(rhinoKey = "flowUserCache", fallBackMethod = "fallBack")
    public List<FlowUserDTO> getFlowUserDTO(List<String> misList) {
        return cache.multiGet(CATEGORY, misList, FlowUserDTO.class);
    }


    /**
     * 流程获取人员信息，缓存异常熔断降级
     *
     * @param misList misList
     * @return {@link List}<{@link FlowUserDTO}>
     */
    public List<FlowUserDTO> fallBack(List<String> misList){
        return Collections.emptyList();
    }
}
