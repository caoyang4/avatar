package com.sankuai.avatar.dao.cache.impl;

import com.sankuai.avatar.dao.cache.AppkeyCapacityCacheRepository;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.dao.cache.model.CapacitySummary;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-12-01 19:27
 */
@Slf4j
@Repository
public class AppkeyCapacityCacheRepositoryImpl implements AppkeyCapacityCacheRepository {

    private static final String CATEGORY = "avatar-appkey-capacity";

    private final CacheClient cache;

    @Autowired
    public AppkeyCapacityCacheRepositoryImpl(CacheClient cache){
        this.cache = cache;
    }

    private String formatPaasNameKey(String appkey, boolean isPaas){
        return String.format("%s_%s_paas", appkey, isPaas ? "is" : "no");
    }
    private String formatSummaryKey(String appkey, boolean isPaas){
        return String.format("%s_%s_summary", appkey, isPaas ? "is" : "no");
    }

    @Override
    public List<String> getPaasNamesByAppkey(String appkey, boolean isPaas) throws CacheException {
        String[] paasNames = cache.get(CATEGORY, formatPaasNameKey(appkey, isPaas), String[].class);
        return Objects.nonNull(paasNames) ? Arrays.asList(paasNames) : Collections.emptyList();
    }

    @Override
    public CapacitySummary getAppkeySummary(String appkey, boolean isPaas) throws CacheException{
        return cache.get(CATEGORY, formatSummaryKey(appkey, isPaas), CapacitySummary.class);
    }

    @Override
    public Boolean setPaasNames(String appkey, List<String> paasNames, boolean isPaas, int expireTime) throws CacheException{
        return cache.set(CATEGORY, formatPaasNameKey(appkey, isPaas), paasNames, expireTime);
    }

    @Override
    public boolean setAppkeySummary(String appkey, CapacitySummary summary, boolean isPaas, int expireTime) throws CacheException{
        return cache.set(CATEGORY, formatSummaryKey(appkey,isPaas), summary, expireTime);
    }

}
