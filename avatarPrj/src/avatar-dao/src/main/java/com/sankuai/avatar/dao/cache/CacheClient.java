package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.cache.exception.CacheException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 缓存接口
 * @author caoyang
 * @create 2022-10-19 16:01
 */
public interface CacheClient {

    /**
     * 集
     * 缓存数据
     *
     * @param prefix     前缀
     * @param key        缓存的key
     * @param value      缓存的value
     * @param expireTime 过期时间，小于0表示不设置过期时间，单位：秒
     * @return 是否成功
     * @throws CacheException 缓存异常
     */
    <T> boolean set(String prefix, String key, T value, int expireTime) throws CacheException;

    /**
     * 得到
     * 从缓存获取数据
     *
     * @param prefix 前缀
     * @param key    缓存的key
     * @param clz    class类型
     * @return 缓存的value
     * @throws CacheException 缓存异常
     */
    <T> T get(String prefix, String key, Class<T> clz) throws CacheException;

    /**
     * 从缓存获取数据，泛型
     *
     * @param prefix 前缀，可为空
     * @param key    缓存的key
     * @param type   类型
     * @param <T>    类型
     * @return 缓存的value
     */
    <T> T get(String prefix, String key, Type type);

    /**
     * 多组
     * 批量缓存
     *
     * @param prefix     前缀
     * @param keyValues  缓存的键值对
     * @param expireTime 过期时间，小于0表示不设置过期时间，单位：秒
     * @return 是否成功
     * @throws CacheException 缓存异常
     */
    <T> boolean multiSet(String prefix, Map<String, T> keyValues, int expireTime) throws CacheException;

    /**
     * 多得到
     * 批量获取
     *
     * @param prefix 前缀
     * @param keys   缓存的key
     * @param cls    class类型
     * @return 缓存的value
     * @throws CacheException 缓存异常
     */
    <T> List<T> multiGet(String prefix, List<String> keys, Class<T> cls) throws CacheException;

    /**
     * 删除
     * 删除缓存
     *
     * @param prefix 前缀
     * @param key    缓存的 key
     * @return 是否删除成功
     * @throws CacheException 缓存异常
     */
    boolean delete(String prefix, String key) throws CacheException;

    /**
     * 获取缓存原生字符串
     *
     * @param prefix 前缀
     * @param key    关键
     * @return {@link String}
     * @throws CacheException 缓存异常
     */
    String get(String prefix, String key) throws CacheException;

    /**
     * 获取缓存原生字符串
     *
     * @param prefix 前缀
     * @param keys    关键
     * @return {@link List} {@link String}
     * @throws CacheException 缓存异常
     */
    List<String> get(String prefix, List<String> keys) throws CacheException;

    /**
     * 返回哈希表 key 中给定域 field 的值
     *
     * @param category category
     * @param key      缓存的key
     * @param field    HashMap 字段
     * @param cls      类型
     * @param <T>      类型
     * @return 缓存的value
     */
    <T> T hget(String category, String key, String field, Class<T> cls);

    /**
     * 将哈希表 key 中的域 field 的值设为 value
     *
     * @param category   category
     * @param key        缓存的key
     * @param field      HashMap 字段
     * @param value      HashMap 值
     * @param expireTime 过期时间
     * @return 缓存的value
     */
    <T> boolean hset(String category, String key, String field, T value, int expireTime);

    /**
     * 删除哈希表 key 中的一个或多个指定域 field
     *
     * @param category category
     * @param key      缓存的key
     * @param field    HashMap 字段
     * @return 是否删除成功
     */
    boolean hdel(String category, String key, String... field);

    /**
     * 查询给定域 field 是否存在
     *
     * @param category category
     * @param key      缓存的key
     * @param field    HashMap 字段
     * @return field 是否存在
     */
    boolean hExists(String category, String key, String field);

    /**
     * 返回哈希表 key 中，所有的 field-value
     *
     * @param category category
     * @param key      缓存的key
     * @param cls      类型
     * @param <T>      类型
     * @return field-value Map
     */
    <T> Map<String, T> hgetAll(String category, String key, Class<T> cls);

    /**
     * 自增函数
     *
     * @param category category
     * @param key      缓存的key
     * @param amount   增加的值
     * @return 是否自增成功
     */
    boolean incrBy(String category, String key, Long amount);

}
