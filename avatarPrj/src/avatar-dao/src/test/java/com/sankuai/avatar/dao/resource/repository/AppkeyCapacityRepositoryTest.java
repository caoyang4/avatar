package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.impl.AppkeyCapacityRepositoryImpl;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyCapacityDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyCapacityRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2022-11-03 14:44
 */
public class AppkeyCapacityRepositoryTest extends TestBase {

    private final AppkeyCapacityRepository repository;

    public AppkeyCapacityRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (AppkeyCapacityRepositoryImpl) ctx.getBean("appkeyCapacityRepositoryImpl");
    }

    static AppkeyCapacityDO appkeyCapacityDO = new AppkeyCapacityDO();
    static {
        appkeyCapacityDO.setAppkey("unit");
        appkeyCapacityDO.setSetName(UUID.randomUUID().toString().substring(0,8));
        appkeyCapacityDO.setCapacityLevel(5);
        appkeyCapacityDO.setStandardLevel(5);
        appkeyCapacityDO.setIsCapacityStandard(true);
        appkeyCapacityDO.setResourceUtil("");
        appkeyCapacityDO.setUtilizationStandard("SKIP_STANDARD");
        appkeyCapacityDO.setWhitelists("");
        appkeyCapacityDO.setMiddleware("");
        appkeyCapacityDO.setHosts("");
        appkeyCapacityDO.setOctoHttpProvider("");
        appkeyCapacityDO.setOctoThriftProvider("");
        appkeyCapacityDO.setAccessComponent("");
        appkeyCapacityDO.setIsPaas(false);
    }

    @Test
    public void query() {
        String appkey = "com.sankuai.avatar.web";
        List<AppkeyCapacityDO> query = repository.query(AppkeyCapacityRequest.builder().appkey(appkey).setName("").build());
        Assert.assertEquals(1, query.size());
        Assert.assertEquals(appkey, query.get(0).getAppkey());

        List<AppkeyCapacityDO> capacityDOList = repository.query(AppkeyCapacityRequest.builder()
                        .appkey("com.sankuai.deliveryturing.nextbox.dispatch")
                        .isFullField(false)
                        .build());
        Assert.assertTrue(capacityDOList.size() > 1);
    }

    @Test
    public void getAllCapacityAppkeys(){
        List<AppkeyCapacityDO> query = repository.query(AppkeyCapacityRequest.builder().onlyAppkey(true).build());
        Assert.assertTrue(query.size() > 0);
    }

    @Test
    public void insert() {
        Assert.assertTrue(repository.insert(appkeyCapacityDO));
    }

    @Test
    public void update() {
        List<AppkeyCapacityDO> doList = repository.query(AppkeyCapacityRequest.builder().appkey("unit").build());
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            AppkeyCapacityDO appkeyCapacityDO = doList.get(0);
            appkeyCapacityDO.setUpdateBy("unit");
            Assert.assertTrue(repository.update(appkeyCapacityDO));
        }
    }

    @Test
    public void delete() {
        List<AppkeyCapacityDO> doList = repository.query(AppkeyCapacityRequest.builder().appkey("unit").build());
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            Assert.assertTrue(repository.delete(doList.get(0).getId()));
        }
    }
}