package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.cache.impl.AppkeyCapacityCacheRepositoryImpl;
import com.sankuai.avatar.dao.cache.model.CapacitySummary;
import com.sankuai.avatar.dao.resource.repository.TestBase;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-01 20:15
 */
public class AppkeyCapacityCacheRepositoryTest extends TestBase {

    private final AppkeyCapacityCacheRepository repository;

    private final static String appkey = "test-appkey";
    private final static String paasAppkey = "test-paas-appkey";

    public AppkeyCapacityCacheRepositoryTest() {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (AppkeyCapacityCacheRepositoryImpl) ctx.getBean("appkeyCapacityCacheRepositoryImpl");
    }

    @Test
    public void getPaasNamesByAppkey() {
        repository.setPaasNames(paasAppkey, Arrays.asList("Mafka", "Pike", "RDS"), true, -1);
        List<String> paasNames = repository.getPaasNamesByAppkey(paasAppkey, true);
        Assert.assertEquals(3, paasNames.size());
    }

    @Test
    public void setPaasNames() {
        Boolean cache = repository.setPaasNames(appkey, Arrays.asList("Mafka", "Pike", "Eagle"), false, -1);
        Assert.assertTrue(cache);
    }

    @Test
    public void getAppkeySummary() {
        CapacitySummary capacitySummary = new CapacitySummary();
        capacitySummary.setCapacityLevel(4);
        capacitySummary.setStandardCapacityLevel(5);
        capacitySummary.setIsCapacityStandard(false);
        capacitySummary.setStandardTips("再续1s");
        repository.setAppkeySummary(appkey, capacitySummary, false, -1);
        CapacitySummary summary = repository.getAppkeySummary(appkey, false);
        Assert.assertEquals(capacitySummary.getCapacityLevel(), summary.getCapacityLevel());
    }

    @Test
    public void setAppkeySummary() {
        CapacitySummary capacitySummary = new CapacitySummary();
        capacitySummary.setCapacityLevel(4);
        capacitySummary.setStandardCapacityLevel(5);
        capacitySummary.setIsCapacityStandard(false);
        capacitySummary.setStandardTips("再续1s");
        boolean cache = repository.setAppkeySummary(appkey, capacitySummary, false, -1);
        Assert.assertTrue(cache);
    }
}