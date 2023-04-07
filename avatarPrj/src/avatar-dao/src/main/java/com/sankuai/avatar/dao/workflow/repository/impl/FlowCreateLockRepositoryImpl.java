package com.sankuai.avatar.dao.workflow.repository.impl;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.dao.workflow.repository.FlowCreateLockRepository;
import com.sankuai.avatar.dao.cache.SquirrelCacheClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;

/**
 * @author zhaozhifan02
 */
@Repository
public class FlowCreateLockRepositoryImpl implements FlowCreateLockRepository {

    @MdpConfig("FLOW_LOCK_CATEGORY:avatar-workflow-flow-lock")
    private String flowLockCategory;

    /**
     * 默认缓存过期时间
     */
    private static final int DEFAULT_EXPIRE_TIME = 0;

    private final SquirrelCacheClient cacheClient;

    @Autowired
    public FlowCreateLockRepositoryImpl(SquirrelCacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    @Override
    public boolean isExists(String key) {
        String value = cacheClient.get(flowLockCategory, key, String.class);
        return StringUtils.isNotEmpty(value);
    }

    @Override
    public <T> T get(String key, Type type) {
        return cacheClient.get(flowLockCategory, key, type);
    }

    @Override
    public <T> boolean save(String key, T value, Integer expireTime) {
        if (expireTime == null || expireTime == 0) {
            return cacheClient.set(flowLockCategory, key, value, DEFAULT_EXPIRE_TIME);
        }
        return cacheClient.set(flowLockCategory, key, value, expireTime);
    }

    @Override
    public boolean delete(String key) {
        return cacheClient.delete(flowLockCategory, key);
    }

    @Override
    public boolean increment(String key, Long num) {
        return cacheClient.incrBy(flowLockCategory, key, num);
    }
}
