package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.dao.resource.repository.model.UserDO;

import java.util.List;

/**
 * 人员信息缓存接口
 * @author caoyang
 * @create 2022-10-19 17:55
 */
public interface UserCacheRepository {
    /**
     * 得到
     * 缓存获取 user 信息
     *
     * @param mis 人员 mis
     * @return user do
     * @throws CacheException 缓存异常
     */
    UserDO get(String mis) throws CacheException;

    /**
     * 多得到
     * 缓存批量获取 user 信息
     *
     * @param misList 人员 mis
     * @return doList
     * @throws CacheException 缓存异常
     */
    List<UserDO> multiGet(List<String> misList) throws CacheException;

    /**
     * 集
     * 缓存存储 user 信息
     *
     * @param userDO     user do
     * @param expireTime 过期时间
     * @return 是否缓存成功
     * @throws CacheException 缓存异常
     */
    boolean set(UserDO userDO, int expireTime) throws CacheException;

    /**
     * 多组
     * 缓存批量存储 user 信息
     *
     * @param userDOList doList
     * @param expireTime 过期时间，单位：秒
     * @return 是否缓存成功
     * @throws CacheException 缓存异常
     */
    boolean multiSet(List<UserDO> userDOList, int expireTime) throws CacheException;

}
