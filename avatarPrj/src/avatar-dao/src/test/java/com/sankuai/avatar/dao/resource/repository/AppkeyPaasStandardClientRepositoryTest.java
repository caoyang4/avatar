package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasStandardClientDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasStandardClientRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-09-27 17:40
 */
public class AppkeyPaasStandardClientRepositoryTest extends TestBase{

    private final AppkeyPaasStandardClientRepository repository;
    public AppkeyPaasStandardClientRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (AppkeyPaasStandardClientRepository) ctx.getBean("appkeyPaasStandardClientRepositoryImpl");
    }

    private static final AppkeyPaasStandardClientDO appkeyPaasStandardClientDO = new AppkeyPaasStandardClientDO();
    static {
        appkeyPaasStandardClientDO.setPaasName("S3");
        appkeyPaasStandardClientDO.setLanguage("java");
        appkeyPaasStandardClientDO.setStandardVersion("3.16.5");
        appkeyPaasStandardClientDO.setGroupId("com.meituan.s3");
        appkeyPaasStandardClientDO.setArtifactId("s3-client");
        appkeyPaasStandardClientDO.setUpdateBy("caoyang42");
    }

    @Test
    public void testQuery() {
        List<AppkeyPaasStandardClientDO> appkeyPaasStandardClientDOList = repository.query(AppkeyPaasStandardClientRequest.builder()
                .paasName("Cellar")
                .language("Java")
                .build());
        Assert.assertEquals(1, appkeyPaasStandardClientDOList.size());
        AppkeyPaasStandardClientDO standardClientDO = appkeyPaasStandardClientDOList.get(0);
        Assert.assertEquals("Cellar", standardClientDO.getPaasName());
        Assert.assertEquals("Java", standardClientDO.getLanguage());
    }

    @Test
    public void testInsert() {
        boolean insert = repository.insert(appkeyPaasStandardClientDO);
        Assert.assertTrue(insert);
    }

    @Test
    public void testInsertBatch() {
        appkeyPaasStandardClientDO.setCreateTime(new Date());
        appkeyPaasStandardClientDO.setUpdateTime(new Date());
        String json = JsonUtil.bean2Json(appkeyPaasStandardClientDO);
        AppkeyPaasStandardClientDO standardClientDO1 = JsonUtil.json2Bean(json, AppkeyPaasStandardClientDO.class);
        AppkeyPaasStandardClientDO standardClientDO2 = JsonUtil.json2Bean(json, AppkeyPaasStandardClientDO.class);
        int batch = repository.insertBatch(Arrays.asList(standardClientDO1, standardClientDO2));
        Assert.assertEquals(2, batch);
    }

    @Test
    public void testUpdate() {
        Assert.assertFalse(repository.update(new AppkeyPaasStandardClientDO()));
        appkeyPaasStandardClientDO.setId(636);
        appkeyPaasStandardClientDO.setCreateTime(new Date());
        appkeyPaasStandardClientDO.setUpdateTime(new Date());
        boolean update = repository.update(appkeyPaasStandardClientDO);
        Assert.assertTrue(update);
    }
}