package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.ServiceWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.ServiceWhitelistRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-10-21 19:08
 */
public class ServiceWhitelistRepositoryTest extends TestBase {

    private final ServiceWhitelistRepository repository;

    private static ServiceWhitelistDO serviceWhitelistDO;
    static {
        serviceWhitelistDO = new ServiceWhitelistDO();
        serviceWhitelistDO.setApp("capacity");
        serviceWhitelistDO.setReason("无可奉告");
        serviceWhitelistDO.setEndTime(new Date());
        serviceWhitelistDO.setInputUser("unitTest");
        serviceWhitelistDO.setAppkey("test-appkey");
        serviceWhitelistDO.setApplication("unit-test-application");
        serviceWhitelistDO.setOrgIds("100046,150042,1573,150044,1021866");
        serviceWhitelistDO.setSetName("test-set");
    }

    public ServiceWhitelistRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (ServiceWhitelistRepository) ctx.getBean("serviceWhitelistRepositoryImpl");
    }

    @Test
    public void testQuery() {
        String app = "capacity";
        List<ServiceWhitelistDO> doList = repository.query(ServiceWhitelistRequest.builder().app(app).build());
        assert CollectionUtils.isNotEmpty(doList);
        for (ServiceWhitelistDO whitelistDO : doList) {
            Assert.assertEquals(app, whitelistDO.getApp());
            assert StringUtils.isNotEmpty(whitelistDO.getAppkey());
        }
    }

    @Test
    public void testInsert() {
        boolean insert = repository.insert(serviceWhitelistDO);
        Assert.assertTrue(insert);
    }

    @Test
    public void testUpdate() {
        Assert.assertFalse(repository.update(null));
        String appkey = "test-appkey";
        List<ServiceWhitelistDO> doList = repository.query(ServiceWhitelistRequest.builder().appkeys(Collections.singletonList(appkey)).build());
        if (CollectionUtils.isNotEmpty(doList)) {
            ServiceWhitelistDO serviceWhitelistDO = doList.get(0);
            serviceWhitelistDO.setInputUser("test");
            Assert.assertTrue(repository.update(serviceWhitelistDO));
        }

    }

    @Test
    public void testDelete() {
        List<ServiceWhitelistDO> doList = repository.query(ServiceWhitelistRequest.builder().appkeys(Collections.singletonList("test-appkey")).build());
        Assert.assertNotNull(doList);
        if (!doList.isEmpty()) {
            int id = doList.get(0).getId();
            Assert.assertTrue(repository.delete(id));
        }
    }
}