package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * appkey测试DB交互
 *
 * @author qinwei05
 * @date 2022/11/03
 */
@Slf4j
public class AppkeyRepositoryTest extends TestBase {

    private final AppkeyRepository appkeyRepository;

    public AppkeyRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        appkeyRepository = (AppkeyRepository) ctx.getBean("appkeyRepositoryImpl");
    }

    private static final AppkeyDO appkeyDO = new AppkeyDO();

    static {
        appkeyDO.setAppkey("com.sankuai.avatar.develop");
    }


    @Test
    public void testQuery() {
        System.out.println(appkeyRepository);
        List<AppkeyDO> appkeyDOS = appkeyRepository.query(AppkeyRequest.builder()
                .appkey(testAppkey)
                .build());
        System.out.println(appkeyDOS);
        Assert.assertNotNull(appkeyDOS);
        assert appkeyDOS.size() > 0;
        AppkeyDO appkeyDO = appkeyDOS.get(0);
        Assert.assertEquals(testAppkey, appkeyDO.getAppkey());
    }

    @Test
    @Transactional
    @Rollback()
    public void testInsert(){
        appkeyDO.setAppkey(UUID.randomUUID().toString());
        boolean insert = appkeyRepository.insert(appkeyDO);
        Assert.assertTrue(insert);
    }

    @Test
    @Transactional
    @Rollback()
    public void testInsertBatch() {
        final List<AppkeyDO> appkeyDOList = Collections.singletonList(
                new AppkeyDO(0, UUID.randomUUID().toString(), "name", "description", "", "", "", "businessGroup", "srv", "pdl", "pdlName", "owt",
                        "owtName", "rank", "containerType", "serviceType", false, "statefulReason", false, false, false,
                        "tenant", "gitRepository", "projectType", false, 0, 0, 0, "capacityReason",
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "capacityUpdateBy",
                        "weekResourceUtil", "admin", "rdAdmin", "epAdmin", "opAdmin", 0, "applicationName",
                        "applicationChName", "orgId", "orgName", "type", "billingUnitId", "billingUnit", "categories",
                        "tags", "frameworks", "isBoughtExternal", false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
        int batch = appkeyRepository.insertBatch(appkeyDOList);
        Assert.assertEquals(1, batch);
    }

    @Test
    public void testUpdate() {
        boolean update = appkeyRepository.update(null);
        Assert.assertFalse(update);
        List<AppkeyDO> appkeyDOS = appkeyRepository.query(AppkeyRequest.builder()
                .appkey(testAppkey)
                .build());
        if (!appkeyDOS.isEmpty()) {
            AppkeyDO appkeyDO = appkeyDOS.get(0);
            appkeyDO.setBillingUnit(UUID.randomUUID().toString());
            update = appkeyRepository.update(appkeyDO);
            Assert.assertTrue(update);
        }
    }

}