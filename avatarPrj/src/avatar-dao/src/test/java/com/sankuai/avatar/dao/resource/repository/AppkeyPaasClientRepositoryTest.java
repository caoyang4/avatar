package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasClientDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasClientRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2022-09-27 17:40
 */
public class AppkeyPaasClientRepositoryTest extends TestBase{

    private final AppkeyPaasClientRepository repository;

    public AppkeyPaasClientRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (AppkeyPaasClientRepository) ctx.getBean("appkeyPaasClientRepositoryImpl");
    }

    private static final AppkeyPaasClientDO appkeyPaasClientDO = new AppkeyPaasClientDO();
    static {
        appkeyPaasClientDO.setPaasName("S3");
        appkeyPaasClientDO.setLanguage("java");
        appkeyPaasClientDO.setVersion("3.10.6");
        appkeyPaasClientDO.setStandardVersion("3.16.5");
        appkeyPaasClientDO.setIsCapacityStandard(false);
        appkeyPaasClientDO.setUpdateBy("caoyang42");
        appkeyPaasClientDO.setGroupId("com.meituan.s3");
        appkeyPaasClientDO.setArtifactId("s3-client");
        appkeyPaasClientDO.setUpdateDate(new Date());
    }

    @Test
    public void testQuery() {
        List<AppkeyPaasClientDO> appkeyPaasClientDOList = repository.query(
                AppkeyPaasClientRequest.builder()
                        .paasName("S3")
                        .build());
        assert appkeyPaasClientDOList.size() > 0;
        for (AppkeyPaasClientDO paasClientDO : appkeyPaasClientDOList) {
            Assert.assertEquals("S3", paasClientDO.getPaasName());
        }
    }

    @Test
    public void testInsert() {
        appkeyPaasClientDO.setClientAppkey(UUID.randomUUID().toString());
        boolean insert = repository.insert(appkeyPaasClientDO);
        Assert.assertTrue(insert);
    }

    @Test
    public void testInsertBatch() {
        appkeyPaasClientDO.setCreateTime(new Date());
        appkeyPaasClientDO.setUpdateTime(new Date());
        String json = JsonUtil.bean2Json(appkeyPaasClientDO);
        AppkeyPaasClientDO paasClientDO1 = JsonUtil.json2Bean(json, AppkeyPaasClientDO.class);
        Assert.assertNotNull(paasClientDO1);
        paasClientDO1.setClientAppkey(UUID.randomUUID().toString());
        AppkeyPaasClientDO paasClientDO2 = JsonUtil.json2Bean(json, AppkeyPaasClientDO.class);
        Assert.assertNotNull(paasClientDO2);
        paasClientDO2.setClientAppkey(UUID.randomUUID().toString());
        int batch = repository.insertBatch(Arrays.asList(paasClientDO1, paasClientDO2));
        Assert.assertEquals(2, batch);
    }

    @Test
    public void testUpdate() {
        List<AppkeyPaasClientDO> appkeyPaasClientDOList = repository.query(
                AppkeyPaasClientRequest.builder()
                        .paasName("S3")
                        .build());
        Assert.assertNotNull(appkeyPaasClientDOList);
        if (CollectionUtils.isNotEmpty(appkeyPaasClientDOList)) {
            AppkeyPaasClientDO paasClientDO = appkeyPaasClientDOList.get(0);
            paasClientDO.setUpdateTime(new Date());
            Assert.assertTrue(repository.update(paasClientDO));
        }
    }

    @Test
    public void delete(){
        List<AppkeyPaasClientDO> appkeyPaasClientDOList = repository.query(
                AppkeyPaasClientRequest.builder()
                        .updateDate(DateUtils.localDateToDate(LocalDate.now().plusDays(-7)))
                        .build());
        Assert.assertNotNull(appkeyPaasClientDOList);
        for (AppkeyPaasClientDO paasClientDO : appkeyPaasClientDOList) {
            Assert.assertTrue(repository.delete(paasClientDO.getId()));
        }
    }
}