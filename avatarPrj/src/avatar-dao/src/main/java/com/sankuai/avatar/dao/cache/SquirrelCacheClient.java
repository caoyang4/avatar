package com.sankuai.avatar.dao.cache;

import com.dianping.cat.Cat;
import com.dianping.squirrel.client.StoreKey;
import com.dianping.squirrel.client.impl.redis.RedisStoreClient;
import com.google.common.collect.Lists;
import com.sankuai.avatar.common.utils.GsonUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * squirrel 客户端封装
 * @author caoyang
 * @create 2022-10-19 16:30
 */
@Slf4j
@Component
public class SquirrelCacheClient implements CacheClient {

    /**
     * 批量multiSet数量
     */
    private static final int BATCH_MULTI_SET_SIZE = 500;

    private final RedisStoreClient redisStoreClient;

    @Autowired
    public SquirrelCacheClient(RedisStoreClient redisStoreClient){
        this.redisStoreClient = redisStoreClient;
    }

    @Override
    public <T> boolean set(String prefix, String key, T value, int expireTime) throws CacheException{
        final String category = prefix;
        if (StringUtils.isEmpty(category) || StringUtils.isEmpty(key) || Objects.isNull(value)) {
            log.warn("invalid set cache-parameters");
            return false;
        }
        boolean success = false;
        StoreKey storeKey = null;
        try {
            storeKey = new StoreKey(category, key);
            String saveValue = JsonUtil.bean2Json(value);
            success = expireTime > 0
                    ? redisStoreClient.set(storeKey, saveValue, expireTime)
                    : redisStoreClient.set(storeKey, saveValue);
            log.info("Save storeKey: {}, value: {}, expireTime: {}, result: {}",
                    storeKey, saveValue, expireTime, success);
        } catch (Throwable t) {
            log.error("Save storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
            throw new CacheException(t.getMessage());
        }
        return success;
    }

    @Override
    public <T> T get(String prefix, String key, Class<T> clz) throws CacheException{
        final String category = prefix;
        if (StringUtils.isEmpty(category) || StringUtils.isEmpty(key) || Objects.isNull(clz)) {
            log.warn("invalid set cache-parameters");
            return null;
        }
        StoreKey storeKey = null;
        try {
            storeKey = new StoreKey(category, key);
            String value = redisStoreClient.get(storeKey);
            if (Objects.nonNull(value)) {
                log.info("Get storeKey: {}, value: {}", storeKey, value);
                return JsonUtil.json2Bean(value, clz);
            }
            return null;
        } catch (Throwable t) {
            log.error("Get storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
            throw new CacheException(t.getMessage());
        }
    }

    @Override
    public <T> boolean multiSet(String prefix, Map<String, T> keyValues, int expireTime) throws CacheException{
        final String category = prefix;
        if (StringUtils.isEmpty(category) || MapUtils.isEmpty(keyValues)) {
            log.warn("Invalid multiSet cache-parameters");
            return false;
        }
        List<StoreKey> storeKeys = Lists.newArrayList();
        List<String> values = Lists.newArrayList();
        keyValues.forEach((key, value) -> {
            try {
                StoreKey storeKey = new StoreKey(category, key);
                String saveValue = JsonUtil.bean2Json(value);
                storeKeys.add(storeKey);
                values.add(saveValue);
            } catch (Throwable t) {
                // 序列化失败，跳过
                log.warn("Key: {} serialize value: {} caught exception: ", key, value, t);
                Cat.logError(t);
                throw new CacheException(t.getMessage());
            }
        });

        if (CollectionUtils.isEmpty(storeKeys)) {
            log.warn("Wait to multiSet storeKeys is empty");
            return false;
        }

        boolean success = true;
        // 分批写入，每次500
        for (int i = 0; i * BATCH_MULTI_SET_SIZE < storeKeys.size(); i++) {
            int fromIndex = i * BATCH_MULTI_SET_SIZE;
            int toIndex = Math.min((i + 1) * BATCH_MULTI_SET_SIZE, storeKeys.size());

            List<StoreKey> batchStoreKeys = storeKeys.subList(fromIndex, toIndex);
            List<String> batchValues = values.subList(fromIndex, toIndex);
            if (!doMultiSet(batchStoreKeys, batchValues, expireTime)) {
                log.warn("Batch multiSet storeKeys: {} failed", batchStoreKeys);
                success = false;
            }
        }
        return success;
    }

    /**
     * 执行批量写缓存
     * 注意：单次写入的key数量要小于1024
     *
     * @param storeKeys  List<StoreKey>
     * @param values     List<String> values
     * @param expireTime 过期时间
     * @return 是否成功
     */
    private boolean doMultiSet(List<StoreKey> storeKeys, List<String> values, int expireTime) throws CacheException{
        boolean success = false;
        try {
            success = expireTime > 0
                      ? redisStoreClient.multiSet(storeKeys, values, expireTime)
                      : redisStoreClient.multiSet(storeKeys, values);
            log.info("MultiSet storeKeys: {}, result: {}", storeKeys, success);
        } catch (Throwable t) {
            log.error("MultiSet storeKeys: {} caught exception: ", storeKeys, t);
            Cat.logError(t);
            throw new CacheException(t.getMessage());
        }
        return success;
    }

    @Override
    public <T> List<T> multiGet(String prefix, List<String> keys, Class<T> cls) throws CacheException{
        final String category = prefix;
        if (StringUtils.isEmpty(prefix) || CollectionUtils.isEmpty(keys) || Objects.isNull(cls)) {
            log.warn("Invalid multiGet cache-parameters");
            return null;
        }
        List<StoreKey> storeKeys = null;
        List<T> values = null;
        try {
            storeKeys = keys.stream()
                    .distinct()
                    .map(key -> new StoreKey(category, key))
                    .collect(Collectors.toList());
            Map<StoreKey, String> res = redisStoreClient.multiGet(storeKeys);
            values = MapUtils.isEmpty(res)
                    ? null
                    : res.values().stream()
                            .map(value -> {
                                try {
                                    return Objects.nonNull(value) ? JsonUtil.json2Bean(value, cls) : null;
                                } catch (Throwable t) {
                                    // 序列化失败，跳过
                                    log.warn("Deserialize value: {} caught exception: ", value, t);
                                    Cat.logError(t);
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
            log.info("MultiGet storeKeys: {}, values: {}", storeKeys, values);
        } catch (Throwable t) {
            log.error("MultiGet storeKeys: {} caught exception: ", storeKeys, t);
            Cat.logError(t);
            throw new CacheException(t.getMessage());
        }
        return values;
    }

    @Override
    public boolean delete(String prefix, String key) throws CacheException{
        final String category = prefix;
        if (StringUtils.isEmpty(category) || StringUtils.isEmpty(key)) {
            log.warn("Invalid delete cache-parameters");
            return false;
        }
        StoreKey storeKey = null;
        boolean success = false;
        try {
            storeKey = new StoreKey(category, key);
            success = redisStoreClient.delete(storeKey);
            log.info("Delete storeKey: {}, result: {}", storeKey, success);
        } catch (Throwable t) {
            log.error("Delete storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
            throw new CacheException(t.getMessage());
        }

        return success;
    }

    @Override
    public String get(String prefix, String key) throws CacheException {
        final String category = prefix;
        if (StringUtils.isEmpty(category) || StringUtils.isEmpty(key)) {
            log.warn("invalid set cache-parameters");
            return null;
        }
        StoreKey storeKey = null;
        try {
            storeKey = new StoreKey(category, key);
            return redisStoreClient.get(storeKey);
        } catch (Throwable t) {
            log.error("Get storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
            throw new CacheException(t.getMessage());
        }
    }

    @Override
    public List<String> get(String prefix, List<String> keys) throws CacheException {
        final String category = prefix;
        if (StringUtils.isEmpty(prefix) || CollectionUtils.isEmpty(keys)) {
            log.warn("Invalid multiGet cache-parameters");
            return null;
        }
        List<StoreKey> storeKeys = null;
        try {
             storeKeys = keys.stream()
                    .distinct()
                    .map(key -> new StoreKey(category, key))
                    .collect(Collectors.toList());
            Map<StoreKey, String> res = redisStoreClient.multiGet(storeKeys);
            return  MapUtils.isEmpty(res)
                    ? null
                    : new ArrayList<>(res.values());
        } catch (Throwable t) {
            log.error("MultiGet storeKeys: {} caught exception: ", storeKeys, t);
            Cat.logError(t);
            throw new CacheException(t.getMessage());
        }
    }

    @Override
    public <T> T hget(String category, String key, String field, Class<T> cls) {
        if (isValidParameters(category, key) || StringUtils.isEmpty(field)) {
            log.warn("Invalid hget parameters");
            return null;
        }
        StoreKey storeKey = new StoreKey(category, key);
        try {
            String value = redisStoreClient.hget(storeKey, field);
            log.info("HGet storeKey: {}, value: {}", storeKey, value);
            return GsonUtils.deserialization(value, cls);
        } catch (Throwable t) {
            log.error("HGet storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
            return null;
        }
    }

    private boolean isValidParameters(String category, String key) {
        return StringUtils.isBlank(category) || StringUtils.isBlank(key);
    }

    @Override
    public <T> boolean hset(String category, String key, String field, T value, int expireTime) {
        if (isValidParameters(category, key) || StringUtils.isEmpty(field) || value == null) {
            log.warn("Invalid hset parameters");
            return false;
        }
        StoreKey storeKey = new StoreKey(category, key);
        try {
            if (expireTime > 0) {
                redisStoreClient.hset(storeKey, field, value, expireTime);
            } else {
                redisStoreClient.hset(storeKey, field, value);
            }
            log.info("HSet storeKey: {}, field: {} value: {}", storeKey, field, value);
            return true;
        } catch (Throwable t) {
            log.error("HSet storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
            return false;
        }
    }

    @Override
    public boolean hdel(String category, String key, String... field) {
        if (isValidParameters(category, key) || CollectionUtils.isEmpty(Arrays.asList(field))) {
            log.warn("Invalid hdel parameters");
            return false;
        }
        StoreKey storeKey = new StoreKey(category, key);
        try {
            redisStoreClient.hdel(storeKey, field);
            log.info("HDel storeKey: {}, value: {}", storeKey, field);
            return true;
        } catch (Throwable t) {
            log.error("HDel storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
            return false;
        }
    }

    @Override
    public boolean hExists(String category, String key, String field) {
        if (isValidParameters(category, key) || StringUtils.isEmpty(field)) {
            log.warn("Invalid hexists parameters");
            return false;
        }
        StoreKey storeKey = new StoreKey(category, key);
        boolean status = false;
        try {
            status = redisStoreClient.hExists(storeKey, field);
            log.info("HExists storeKey: {}, value: {} status: {}", storeKey, field, status);
        } catch (Throwable t) {
            log.error("HExists storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
        }
        return status;
    }

    @Override
    public <T> Map<String, T> hgetAll(String category, String key, Class<T> cls) {
        if (isValidParameters(category, key)) {
            log.warn("Invalid hgetAll parameters");
            return null;
        }
        StoreKey storeKey = new StoreKey(category, key);
        Map<String, T> result = new HashMap<>();
        try {
            Map<String, String> valueMap = redisStoreClient.hgetAll(storeKey);
            log.info("HGetAll storeKey: {}, value: {}", storeKey, valueMap);
            valueMap.forEach((name, value) -> {
                result.put(name, GsonUtils.deserialization(value, cls));
            });
        } catch (Throwable t) {
            log.error("HGetAll storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
        }
        return result;
    }

    @Override
    public boolean incrBy(String category, String key, Long amount) {
        if (isValidParameters(category, key) || amount <= 0L) {
            log.warn("Invalid incr parameters");
            return false;
        }
        StoreKey storeKey = new StoreKey(category, key);
        try {
            redisStoreClient.incrBy(storeKey, amount);
            log.info("Incr storeKey: {}, value: {}", storeKey, amount);
            return true;
        } catch (Throwable t) {
            log.error("Incr storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
            return false;
        }
    }

    @Override
    public <T> T get(String prefix, String key, Type type) {
        String category = prefix;
        if (isValidParameters(category, key)) {
            log.warn("Invalid get parameters");
            return null;
        }

        StoreKey storeKey = new StoreKey(category, key);
        try {
            T value = redisStoreClient.get(storeKey);
            log.info("Get storeKey: {}, value: {}", storeKey, value);
            return GsonUtils.deserialization(String.valueOf(value), type);
        } catch (Throwable t) {
            log.error("Get storeKey: {} caught exception: ", storeKey, t);
            Cat.logError(t);
            return null;
        }
    }

}
