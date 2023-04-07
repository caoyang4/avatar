package com.sankuai.avatar.dao.workflow.repository;

import java.lang.reflect.Type;

/**
 * 流程创建锁数据管理
 *
 * @author zhaozhifan02
 */
public interface FlowCreateLockRepository {
    /**
     * 锁是否已存在
     *
     * @param key 缓存key
     * @return 是否存在
     */
    boolean isExists(String key);

    /**
     * 获取锁内容
     *
     * @param key  缓存key
     * @param type 类型
     * @return 锁内容
     */
    <T> T get(String key, Type type);

    /**
     * 设置缓存内容
     *
     * @param key        缓存key
     * @param value      缓存value
     * @param expireTime 过期时间
     * @return 是否保存成功
     */
    <T> boolean save(String key, T value, Integer expireTime);

    /**
     * 清理缓存内容
     *
     * @param key 缓存key
     * @return 是否清理成功
     */
    boolean delete(String key);

    /**
     * 自增函数
     *
     * @param key 缓存key
     * @param num 自增值
     * @return 是否自增成功
     */
    boolean increment(String key, Long num);
}
