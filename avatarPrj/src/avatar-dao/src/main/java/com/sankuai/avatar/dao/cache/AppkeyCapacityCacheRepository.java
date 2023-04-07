package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.cache.model.CapacitySummary;
import com.sankuai.avatar.dao.cache.exception.CacheException;

import java.util.List;

/**
 * 由于页面只返回前一天的 paas 上报信息，故可以缓存该信息
 * paas容灾信息缓存
 * @author caoyang
 * @create 2022-12-01 19:01
 */
public interface AppkeyCapacityCacheRepository {

    /**
     * appkey 依赖的paas
     *
     * @param appkey appkey
     * @param isPaas 是否是paas appkey
     * @return {@link List}<{@link String}>
     */
    List<String> getPaasNamesByAppkey(String appkey, boolean isPaas) throws CacheException;

    /**
     * 缓存appkey依赖的paas
     *
     * @param appkey     appkey
     * @param paasNames  paas名字
     * @param isPaas     是否是paas appkey
     * @param expireTime 到期时间
     * @return {@link Boolean}
     */
    Boolean setPaasNames(String appkey, List<String> paasNames, boolean isPaas, int expireTime) throws CacheException;

    /**
     * appkey的整体paas容灾
     *
     * @param appkey appkey
     * @param isPaas 是否是paas appkey
     * @return {@link CapacitySummary}
     */
    CapacitySummary getAppkeySummary(String appkey, boolean isPaas) throws CacheException;

    /**
     * 缓存appkey的整体paas容灾
     *
     * @param appkey     appkey
     * @param summary    总结
     * @param isPaas     是否是paas appkey
     * @param expireTime 到期时间
     * @return boolean
     */
    boolean setAppkeySummary(String appkey,CapacitySummary summary, boolean isPaas, int expireTime) throws CacheException;

}
