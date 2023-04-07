package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.cache.model.UserBg;
import com.sankuai.avatar.dao.cache.exception.CacheException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bg-owt-pdl服务树缓存
 * @author caoyang
 * @create 2023-01-09 15:20
 */
public interface AppkeyTreeCacheRepository {

    /**
     * 获取用户bg
     *
     * @param user 用户
     * @return {@link List}<{@link String}>
     * @throws CacheException 缓存异常
     */
    List<String> getUserBg(String user) throws CacheException;


    /**
     * 缓存用户bg
     *
     * @param user       用户
     * @param bgList     bg列表
     * @param expireTime 到期时间
     * @return {@link Boolean}
     * @throws CacheException 缓存异常
     */
    Boolean setUserBg(String user, List<String> bgList, int expireTime) throws CacheException;


    /**
     * 获取用户bg树
     *
     * @param user 用户
     * @return {@link List}<{@link UserBg}>
     * @throws CacheException 缓存异常
     */
    List<UserBg> getUserBgTree(String user) throws CacheException;

    /**
     * 缓存用户bg树
     *
     * @param user       用户
     * @param bgTree     bg树
     * @param expireTime 到期时间
     * @return {@link Boolean}
     * @throws CacheException 缓存异常
     */
    Boolean setUserBgTree(String user, List<UserBg> bgTree, int expireTime) throws CacheException;

    /**
     * 获取产品线服务数量
     *
     * @param pdl pdl
     * @return {@link Map}
     * @throws CacheException 缓存异常
     */
    Map<String, Integer> getPdlSrvCount(String pdl) throws CacheException;

    /**
     * 批量获取产品线服务数量
     *
     * @param pdlList pdl列表
     * @return {@link HashMap}
     * @throws CacheException 缓存异常
     */
    Map<String, Integer> multiGetPdlSrvCount(List<String> pdlList) throws CacheException;

    /**
     * 缓存产品线服务数量
     *
     * @param pdl        pdl
     * @param count      数量
     * @param expireTime 到期时间
     * @return {@link Boolean}
     * @throws CacheException 缓存异常
     */
    Boolean setPdlSrvCount(String pdl, int count, int expireTime) throws CacheException;


}
