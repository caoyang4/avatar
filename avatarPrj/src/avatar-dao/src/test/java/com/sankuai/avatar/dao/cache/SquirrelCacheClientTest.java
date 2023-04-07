package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

@Slf4j
public class SquirrelCacheClientTest {

    private static final String FLOW_LOCK_CATEGORY = "avatar-workflow-flow-lock";

    private static final String FLOW_MUTEX_SOURCE_CATEGORY = "avatar-workflow-mutex-resource";

    private static final String TEMPLATE_NAME = "delegate_work";

    private static final String CREATE_USER = "zhaozhifan02";

    private static final String FLOW_UUID = "28d1a3c0-dbb3-43b3-929b-e5d663f1e694";

    private static final String FLOW_API_KEY_PREFIX = "flow_api_call";

    private static final String API_CALL_APP_KEY = "avatar-workflow-web";

    /**
     * squirrel Key
     */
    private static final String MUTEX_RESOURCE_KEY = "mutex_resource";

    /**
     * 默认缓存过期时间，不过期
     */
    private static final int DEFAULT_EXPIRE_TIME = -1;

    private final CacheClient cacheClient;

    public SquirrelCacheClientTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        cacheClient = (SquirrelCacheClient) ctx.getBean("squirrelCacheClient");
    }

    @Test
    public void testSet() {
        // key createUser_templateName_uuid
        String key = String.format("%s_%s_%s", CREATE_USER, TEMPLATE_NAME, FLOW_UUID);
        String value = String.format("%s@%s", TEMPLATE_NAME, FLOW_UUID);
        boolean status = cacheClient.set(FLOW_LOCK_CATEGORY, key, value, DEFAULT_EXPIRE_TIME);
        Assert.assertTrue(status);
    }

    @Test
    public void testSetLong() {
        Long value = 1L;
        // key prefix_createUser_appKey_templateName
        String key = String.format("%s_%s_%s_%s", FLOW_API_KEY_PREFIX, CREATE_USER, API_CALL_APP_KEY, TEMPLATE_NAME);
        boolean status = cacheClient.set(FLOW_LOCK_CATEGORY, key, value, DEFAULT_EXPIRE_TIME);
        Assert.assertTrue(status);
    }

    @Test
    public void testGet() {
        // key createUser_templateName_uuid
        String key = String.format("%s_%s_%s", CREATE_USER, TEMPLATE_NAME, FLOW_UUID);
        String value = cacheClient.get(FLOW_LOCK_CATEGORY, key, String.class);
        Assert.assertNotNull(value);
        log.info("Squirrel get key:{} value:{}", key, value);
    }

    @Test
    public void testHSet() {
        String ip = "1.1.1.1";
        String fieldValue = String.format("%s_%s", TEMPLATE_NAME, FLOW_UUID);
        boolean status = cacheClient.hset(FLOW_MUTEX_SOURCE_CATEGORY, MUTEX_RESOURCE_KEY,
                ip, fieldValue, DEFAULT_EXPIRE_TIME);
        Assert.assertTrue(status);
        log.info("Squirrel HSet field:{} value:{}", ip, fieldValue);
    }

    @Test
    public void testHSetEmptyValue() {
        String ip = "";
        String fieldValue = String.format("%s_%s", TEMPLATE_NAME, FLOW_UUID);
        boolean status = cacheClient.hset(FLOW_MUTEX_SOURCE_CATEGORY, MUTEX_RESOURCE_KEY,
                ip, fieldValue, DEFAULT_EXPIRE_TIME);
        Assert.assertFalse(status);
        log.info("Squirrel HSet field:{} value:{}", ip, fieldValue);
    }

    @Test
    public void testHGet() {
        // key: ip
        // field: templateName_uuid
        String ip = "1.1.1.1";
        String value = cacheClient.hget(FLOW_MUTEX_SOURCE_CATEGORY, MUTEX_RESOURCE_KEY, ip, String.class);
        Assert.assertNotNull(value);
        log.info("Squirrel HGet field:{} value:{}", ip, value);

    }

    @Test
    public void testHExistsWithExistField() {
        String ip = "1.1.1.1";
        boolean status = cacheClient.hExists(FLOW_MUTEX_SOURCE_CATEGORY, MUTEX_RESOURCE_KEY, ip);
        Assert.assertTrue(status);
        log.info("Squirrel hExists field:{} status:{}", ip, status);
    }

    @Test
    public void testHExistsWithNotExistField() {
        String ip = "1.1.1.2";
        boolean status = cacheClient.hExists(FLOW_MUTEX_SOURCE_CATEGORY, MUTEX_RESOURCE_KEY, ip);
        Assert.assertFalse(status);
    }

    @Test
    public void testHExistsWithEmptyField() {
        String ip = "";
        boolean status = cacheClient.hExists(FLOW_MUTEX_SOURCE_CATEGORY, MUTEX_RESOURCE_KEY, ip);
        Assert.assertFalse(status);
    }

    @Test
    public void testHgetAll() {
        Map<String, String> keyValueMap = cacheClient.hgetAll(FLOW_MUTEX_SOURCE_CATEGORY,
                MUTEX_RESOURCE_KEY, String.class);
        Assert.assertNotNull(keyValueMap);
        log.info("Squirrel hgetAll value:{}", keyValueMap);
    }

    @Test
    public void testIncrBy() {
        Long value = 1L;
        // key prefix_createUser_appKey_templateName
        String key = String.format("%s_%s_%s_%s", FLOW_API_KEY_PREFIX, CREATE_USER, API_CALL_APP_KEY, TEMPLATE_NAME);
        Boolean status = cacheClient.incrBy(FLOW_LOCK_CATEGORY, key, value);
        Assert.assertNotNull(status);
    }

    @Test
    public void testIncrByNegativeNum() {
        Long value = -1L;
        // key prefix_createUser_appKey_templateName
        String key = String.format("%s_%s_%s_%s", FLOW_API_KEY_PREFIX, CREATE_USER, API_CALL_APP_KEY, TEMPLATE_NAME);
        boolean status = cacheClient.incrBy(FLOW_LOCK_CATEGORY, key, value);
        Assert.assertFalse(status);
    }
}