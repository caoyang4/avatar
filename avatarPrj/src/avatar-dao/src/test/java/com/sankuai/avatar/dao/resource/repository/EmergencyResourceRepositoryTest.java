package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.EmergencyResourceDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencyResourceRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2022-11-25 23:34
 */
public class EmergencyResourceRepositoryTest extends TestBase {

    private final EmergencyResourceRepository repository;

    public EmergencyResourceRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (EmergencyResourceRepository) ctx.getBean("emergencyResourceRepositoryImpl");
    }

    @Test
    public void query() {
        String appkey = "com.sankuai.avatar.develop";
        List<EmergencyResourceDO> doList = repository.query(EmergencyResourceRequest.builder().appkey(appkey).build());
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
        for (EmergencyResourceDO resourceDO : doList) {
            Assert.assertEquals(appkey, resourceDO.getAppkey());
        }
    }

    @Test
    public void save() {
        EmergencyResourceDO resourceDO = new EmergencyResourceDO();
        resourceDO.setFlowId(123);
        resourceDO.setFlowUuid(UUID.randomUUID().toString());
        resourceDO.setAppkey("test-appkey");
        resourceDO.setOperationType("ECS_ONLINE");
        resourceDO.setCreateUser("zhangzhe");
        resourceDO.setTemplate("service_expand");
        Boolean save = repository.insert(resourceDO);
        Assert.assertTrue(save);
    }

    @Test
    public void delete() {
        String appkey = "test-appkey";
        List<EmergencyResourceDO> doList = repository.query(EmergencyResourceRequest.builder().appkey(appkey).build());
        for (EmergencyResourceDO resourceDO : doList) {
            Boolean delete = repository.delete(resourceDO.getId());
            Assert.assertTrue(delete);
        }
    }
}