package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

@Slf4j
public class FlowMutexResourceRepositoryTest {

    private static final String FLOW_MUTEX_RESOURCE_CATEGORY = "avatar-workflow-mutex-resource";

    private static final String TEMPLATE_NAME = "delegate_work";

    private static final String CREATE_USER = "zhaozhifan02";

    private static final String FLOW_UUID = "28d1a3c0-dbb3-43b3-929b-e5d663f1e694";

    private static final String IP_FIELD = "1.1.1.1";

    private FlowMutexResourceRepository flowMutexResourceRepository;

    public FlowMutexResourceRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        flowMutexResourceRepository = (FlowMutexResourceRepository) ctx.getBean("flowMutexResourceRepositoryImpl");
        ReflectionTestUtils.setField(flowMutexResourceRepository, "flowMutexResourceCategory",
                FLOW_MUTEX_RESOURCE_CATEGORY);

    }

    @Test
    public void testSave() {
        String value = String.format("%s_%s", TEMPLATE_NAME, FLOW_UUID);
        boolean status = flowMutexResourceRepository.save(IP_FIELD, value);
        Assert.assertTrue(status);
        log.info("FlowMutexResourceRepository save field:{} value:{}", IP_FIELD, value);
    }

    @Test
    public void testGetResourceByField() {
        String value = flowMutexResourceRepository.getResourceByField(IP_FIELD);
        Assert.assertNotNull(value);
        log.info("FlowMutexResourceRepository get field:{} value:{}", IP_FIELD, value);
    }

    @Test
    public void testIsExist() {
        boolean status = flowMutexResourceRepository.isExist(IP_FIELD);
        Assert.assertTrue(status);
        log.info("FlowMutexResourceRepository field:{} isExist:{}", IP_FIELD, status);
    }

    @Test
    public void testGetTotalResource() {
        Map<String, String> res = flowMutexResourceRepository.getTotalResource();
        Assert.assertNotNull(res);
        log.info("FlowMutexResourceRepository get total resource:{}", res);
    }
}