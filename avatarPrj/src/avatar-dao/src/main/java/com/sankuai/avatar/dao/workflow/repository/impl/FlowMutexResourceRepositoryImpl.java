package com.sankuai.avatar.dao.workflow.repository.impl;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.dao.workflow.repository.FlowMutexResourceRepository;
import com.sankuai.avatar.dao.cache.SquirrelCacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zhaozhifan02
 */
@Repository
public class FlowMutexResourceRepositoryImpl implements FlowMutexResourceRepository {

    @MdpConfig("FLOW_MUTEX_RESOURCE_CATEGORY:avatar-workflow-mutex-resource")
    private String flowMutexResourceCategory;

    /**
     * squirrel Key
     */
    private static final String MUTEX_RESOURCE_KEY = "mutex_resource";

    /**
     * 默认缓存过期时间
     */
    private static final int DEFAULT_EXPIRE_TIME = 0;

    private final SquirrelCacheClient cacheClient;

    @Autowired
    public FlowMutexResourceRepositoryImpl(SquirrelCacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    @Override
    public boolean save(String field, String value) {
        return cacheClient.hset(flowMutexResourceCategory, MUTEX_RESOURCE_KEY, field, value, DEFAULT_EXPIRE_TIME);
    }

    @Override
    public boolean delete(String field) {
        return cacheClient.hdel(flowMutexResourceCategory, MUTEX_RESOURCE_KEY, field);
    }

    @Override
    public boolean isExist(String field) {
        return cacheClient.hExists(flowMutexResourceCategory, MUTEX_RESOURCE_KEY, field);
    }

    @Override
    public String getResourceByField(String field) {
        return cacheClient.hget(flowMutexResourceCategory, MUTEX_RESOURCE_KEY, field, String.class);
    }

    @Override
    public Map<String, String> getTotalResource() {
        return cacheClient.hgetAll(flowMutexResourceCategory, MUTEX_RESOURCE_KEY, String.class);
    }
}
