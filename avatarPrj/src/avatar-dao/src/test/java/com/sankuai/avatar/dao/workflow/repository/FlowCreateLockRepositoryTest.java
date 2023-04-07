package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
public class FlowCreateLockRepositoryTest {

    private static final String FLOW_LOCK_CATEGORY = "avatar-workflow-flow-lock";

    private static final String TEMPLATE_NAME = "delegate_work";

    private static final String UNLOCK_DEPLOY_TEMPLATE = "unlock_deploy";

    private static final String CREATE_USER = "zhaozhifan02";

    private static final String SYSTEM_USER = "__system";

    private static final String FLOW_UUID = "28d1a3c0-dbb3-43b3-929b-e5d663f1e694";

    private static final String FLOW_API_KEY_PREFIX = "flow_api_call";

    private static final String API_CALL_APP_KEY = "avatar-workflow-web";

    /**
     * 默认缓存过期时间
     */
    private static final int DEFAULT_EXPIRE_TIME = 0;

    private FlowCreateLockRepository flowCreateLockRepository;

    public FlowCreateLockRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        flowCreateLockRepository = (FlowCreateLockRepository) ctx.getBean("flowCreateLockRepositoryImpl");
        ReflectionTestUtils.setField(flowCreateLockRepository, "flowLockCategory", FLOW_LOCK_CATEGORY);

    }

    @Test
    public void testSaveNotExpire() {
        String key = String.format("%s_%s_%s", CREATE_USER, TEMPLATE_NAME, FLOW_UUID);
        String value = String.format("%s@%s", TEMPLATE_NAME, FLOW_UUID);
        boolean status = flowCreateLockRepository.save(key, value, DEFAULT_EXPIRE_TIME);
        Assert.assertTrue(status);
    }

    @Test
    public void testSaveWithExpireTime() {
        Long value = 1L;
        // key=flow_api_call___system_avatar-workflow-web_unlock_deploy
        String key = String.format("%s_%s_%s_%s", FLOW_API_KEY_PREFIX, SYSTEM_USER, API_CALL_APP_KEY,
                UNLOCK_DEPLOY_TEMPLATE);
        boolean status = flowCreateLockRepository.save(key, value, 360);
        Assert.assertTrue(status);
    }

    @Test
    public void testIsExists() {
        String key = String.format("%s_%s_%s", CREATE_USER, TEMPLATE_NAME, FLOW_UUID);
        boolean status = flowCreateLockRepository.isExists(key);
        Assert.assertTrue(status);
    }

    @Test
    public void testGetStringValue() {
        String key = String.format("%s_%s_%s", CREATE_USER, TEMPLATE_NAME, FLOW_UUID);
        String value = flowCreateLockRepository.get(key, String.class);
        Assert.assertNotNull(value);
    }

    @Test
    @Ignore
    public void testDelete() {
        String key = String.format("%s_%s_%s", CREATE_USER, TEMPLATE_NAME, FLOW_UUID);
        boolean status = flowCreateLockRepository.delete(key);
        Assert.assertTrue(status);
    }

    @Test
    public void testIncrement() {
        Long num = 1L;
        // key=flow_api_call___system_avatar-workflow-web_unlock_deploy
        String key = String.format("%s_%s_%s_%s", FLOW_API_KEY_PREFIX, SYSTEM_USER, API_CALL_APP_KEY,
                UNLOCK_DEPLOY_TEMPLATE);
        Boolean status = flowCreateLockRepository.increment(key, num);
        Assert.assertNotNull(status);
    }
}