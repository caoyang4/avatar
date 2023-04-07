package com.sankuai.avatar.web.util;

import com.dianping.squirrel.client.StoreKey;
import com.dianping.squirrel.client.impl.redis.RedisClientBuilder;
import com.dianping.squirrel.client.impl.redis.RedisStoreClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
            .readTimeout(3000)
            .routerType("master-only")
            .build();

    public static void set(String key, Object v){
        StoreKey sk = new StoreKey(category, key);
        redisStoreClient.set(sk, v);
    }

    public static void setEx(String key, Object v, Integer expireInSeconds){
        StoreKey sk = new StoreKey(category, key);
        redisStoreClient.set(sk, v, expireInSeconds);
    }

    public static <T> T get(String key){
        StoreKey sk = new StoreKey(category, key);
        return redisStoreClient.get(sk);
    }
}
