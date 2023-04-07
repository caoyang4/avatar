package com.sankuai.avatar.capacity.util;

import com.dianping.squirrel.client.StoreKey;
import com.dianping.squirrel.client.impl.redis.RedisClientBuilder;
import com.dianping.squirrel.client.impl.redis.RedisStoreClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by shujian on 2020/2/13.
 *
 */
@Slf4j
public class SquirrelUtils {

    private static final String CLUSTER_NAME_KEY = "mdp.squirrel[0].clusterName";

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            InputStream inputStream = SquirrelUtils.class.getClassLoader().getResourceAsStream("squirrel.properties");
            properties.load(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            log.error("get squirrel resource config error", e.getMessage());
        }
    }

    static String getPropertyValue(String key) {
        return properties.getProperty(key);
    }

    private static final String category = "avatar-web";

    private static final RedisStoreClient redisStoreClient = new RedisClientBuilder(getPropertyValue(CLUSTER_NAME_KEY))
            .readTimeout(2000)
            .dsMonitorable(true)
            .routerType("master-slave")
            .build();

    public static void set(String key, Object v){
        StoreKey sk = new StoreKey(category, key);
        redisStoreClient.set(sk, v);
    }

    public static void setEx(String key, Object v, Integer expireInSeconds){
        StoreKey sk = new StoreKey(category, key);
        redisStoreClient.set(sk, v, expireInSeconds);
    }

    public static void setNx(String key, Object v, Integer expireInSeconds){
        StoreKey sk = new StoreKey(category, key);
        redisStoreClient.setnx(sk, v, expireInSeconds);
    }

    public static void hset(String key, String field, Object v){
        StoreKey sk = new StoreKey(category, key);
        redisStoreClient.hset(sk, field, v);
    }

    public static <T> T get(String key){
        StoreKey sk = new StoreKey(category, key);
        return redisStoreClient.get(sk);
    }

    public static <T> T hget(String key, String field){
        StoreKey sk = new StoreKey(category, key);
        return redisStoreClient.hget(sk, field);
    }

    public static <T> Map<StoreKey, T> multiGet(List<String> keys){
        int offset = 100;
        Map<StoreKey, T> keyTHashMap = new HashMap<>();
        int keySize = keys.size();
        int toIndex = 100;
        for (int i = 0; i < keySize; i += offset) {
            if (i + offset > keySize) {
                toIndex = keySize - i;
            }
            List<StoreKey> storeKeys = new ArrayList<>();
            List<String> subKeys = keys.subList(i, i + toIndex);
            for (String key: subKeys){
                if (StringUtils.isNotEmpty(key)) {
                    storeKeys.add(new StoreKey(category, key));
                }
            }
            keyTHashMap.putAll(redisStoreClient.multiGet(storeKeys));
        }
        return keyTHashMap;
    }
}
