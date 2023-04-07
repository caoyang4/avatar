package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasCapacityDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasCapacityRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.*;

/**
 * @author caoyang
 * @create 2022-09-27 17:34
 */
@Slf4j
public class AppkeyPaasCapacityRepositoryTest extends TestBase {

    private final AppkeyPaasCapacityRepository repository;

    public AppkeyPaasCapacityRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (AppkeyPaasCapacityRepository) ctx.getBean("appkeyPaasCapacityRepositoryImpl");
    }

    final static List<Map<String, String>> CLIENT_CONFIG = Collections.singletonList(
            new HashMap<String, String>() {{put("producer.cluster.dispatch.type", "默认分配");}}
    );

    final static List<Map<String, String>> STANDARD_CONFIG = Arrays.asList(
            new HashMap<String, String>(){{put("producer.cluster.dispatch.type", "同地域集群优先");}},
            new HashMap<String, String>(){{put("producer.cluster.dispatch.type", "全部集群");}}
    );

    private static final AppkeyPaasCapacityDO appkeyPaasCapacityDO = new AppkeyPaasCapacityDO();

    static {
        appkeyPaasCapacityDO.setClientAppkey("mapi-shop-web");
        appkeyPaasCapacityDO.setPaasName("ZK");
        appkeyPaasCapacityDO.setType("CLUSTER");
        appkeyPaasCapacityDO.setPaasAppkey("com.sankuai.tair.inf.banmasettest");
        appkeyPaasCapacityDO.setCapacityLevel(2);
        appkeyPaasCapacityDO.setStandardLevel(4);
        appkeyPaasCapacityDO.setIsCapacityStandard(false);
        appkeyPaasCapacityDO.setStandardReason("未达标!");
        appkeyPaasCapacityDO.setStandardTips("未达到容灾标准");
        appkeyPaasCapacityDO.setClientConfig(JsonUtil.bean2Json(CLIENT_CONFIG));
        appkeyPaasCapacityDO.setStandardConfig(JsonUtil.bean2Json(STANDARD_CONFIG));
        appkeyPaasCapacityDO.setIsConfigStandard(false);
        appkeyPaasCapacityDO.setUpdateBy("young");
        appkeyPaasCapacityDO.setTypeName("com.sankuai.tair.inf.banmasettest");
        appkeyPaasCapacityDO.setTypeComment("");
        appkeyPaasCapacityDO.setIsCore(false);
        appkeyPaasCapacityDO.setOwner("avatar");
        appkeyPaasCapacityDO.setIsWhite(false);
        appkeyPaasCapacityDO.setWhiteReason("");
        appkeyPaasCapacityDO.setIsSet(true);
        appkeyPaasCapacityDO.setSetName("test-set");
        appkeyPaasCapacityDO.setSetType("R");
        appkeyPaasCapacityDO.setClientRole("producer");
        appkeyPaasCapacityDO.setUpdateDate(new Date());
    }


    @Test
    public void testQuery() {
        AppkeyPaasCapacityRequest request = new AppkeyPaasCapacityRequest();
        request.setPaasName("ZK");
        List<AppkeyPaasCapacityDO> paasCapacityDOList = repository.query(request);
        System.out.println(paasCapacityDOList);
        Assert.assertNotNull(paasCapacityDOList);
        assert paasCapacityDOList.size() > 0;
        AppkeyPaasCapacityDO appkeyPaasCapacityDO = paasCapacityDOList.get(0);
        Assert.assertEquals("ZK", appkeyPaasCapacityDO.getPaasName());
    }

    @Test
    public void testInsert(){
        appkeyPaasCapacityDO.setClientAppkey("com.meituan.banma.api01");
        appkeyPaasCapacityDO.setPaasAppkey("com.sankuai.tair.banma.qaset");
        boolean insert = repository.insert(appkeyPaasCapacityDO);
        Assert.assertTrue(insert);
    }

    @Test
    public void testInsertBatch() {
        String json = JsonUtil.bean2Json(appkeyPaasCapacityDO);
        AppkeyPaasCapacityDO appkeyPaasCapacityDO = JsonUtil.json2Bean(json, AppkeyPaasCapacityDO.class);
        appkeyPaasCapacityDO.setClientAppkey("com.meituan.banma.api01");
        appkeyPaasCapacityDO.setPaasAppkey("com.sankuai.tair.banma.qaset");
        appkeyPaasCapacityDO.setPaasName("Mafka");
        appkeyPaasCapacityDO.setType("Topic");
        Assert.assertNotNull(appkeyPaasCapacityDO);
        appkeyPaasCapacityDO.setCreateTime(new Date());
        appkeyPaasCapacityDO.setUpdateTime(new Date());
        appkeyPaasCapacityDO.setClientAppkey(UUID.randomUUID().toString());
        int batch = repository.insertBatch(Collections.singletonList(appkeyPaasCapacityDO));
        Assert.assertEquals(1, batch);
    }

    @Test
    public void testUpdate() {
        boolean update = repository.update(null);
        Assert.assertFalse(update);
        AppkeyPaasCapacityRequest request = new AppkeyPaasCapacityRequest();
        request.setPaasName("ZK");
        request.setPaasAppkey("com.sankuai.zk.zhoufeng12ddos2");
        List<AppkeyPaasCapacityDO> appkeyPaasCapacityDOList = repository.query(request);
        if (!appkeyPaasCapacityDOList.isEmpty()) {
            AppkeyPaasCapacityDO capacityDO = appkeyPaasCapacityDOList.get(0);
            capacityDO.setStandardReason("任一单机房故障，集群可正常提供服务。如故障为Leader机房时，集群可自动恢复并且RTO <= 1分钟");
            update = repository.update(capacityDO);
            Assert.assertTrue(update);
        }
    }

    @Test
    public void queryAggregatedClientAppkey() {
        String appkey = "com.meituan.banma.api01";
        Date yesterday = DateUtils.localDateToDate(LocalDate.now());
        List<AppkeyPaasCapacityDO> doList = repository.queryAggregatedClientAppkey(yesterday, appkey);
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
    }

    @Test
    public void queryAggregatedPaasAppkey() {
        String paasAppkey = "com.sankuai.tair.banma.qaset";
        Date yesterday = DateUtils.localDateToDate(LocalDate.now());
        List<AppkeyPaasCapacityDO> doList = repository.queryAggregatedPaasAppkey(yesterday, paasAppkey);
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
    }

    @Test
    public void testQueryAggregatedClientAppkey() {
        String appkey = "com.meituan.banma.api01";
        List<String> paasNameList = Arrays.asList("Mafka", "Cellar", "Lion", "MCC", "ZK");
        Date yesterday = DateUtils.localDateToDate(LocalDate.now());
        List<AppkeyPaasCapacityDO> doList = repository.queryAggregatedClientAppkey(yesterday, appkey, paasNameList);
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
    }

    @Test
    public void queryPaasNamesByAppkey() {
        String appkey = "com.meituan.banma.api01";
        Date yesterday = DateUtils.localDateToDate(LocalDate.now());
        List<String> paasNames = repository.queryPaasNamesByAppkey(yesterday, appkey);
        Assert.assertTrue(CollectionUtils.isNotEmpty(paasNames));
    }

    @Test
    public void testQueryAggregatedPaasAppkey() {
        String paasAppkey = "com.sankuai.tair.banma.qaset";
        List<String> paasNameList = Arrays.asList("Mafka", "Cellar", "Lion", "MCC", "ZK");
        Date yesterday = DateUtils.localDateToDate(LocalDate.now());
        List<AppkeyPaasCapacityDO> doList = repository.queryAggregatedPaasAppkey(yesterday, paasAppkey, paasNameList);
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
    }

    @Test
    public void queryPaasNamesByPaasAppkey() {
        String paasAppkey = "com.sankuai.tair.banma.qaset";
        Date yesterday = DateUtils.localDateToDate(LocalDate.now());
        List<String> paasNames = repository.queryPaasNamesByPaasAppkey(yesterday, paasAppkey);
        Assert.assertTrue(CollectionUtils.isNotEmpty(paasNames));
    }

    @Test
    public void queryPaasAppkeysByUpdateDate() {
        Date yesterday = DateUtils.localDateToDate(LocalDate.now());
        List<String> paasAppkeys = repository.queryPaasAppkeysByUpdateDate(yesterday);
        Assert.assertTrue(CollectionUtils.isNotEmpty(paasAppkeys));
    }

    @Test
    public void queryClientAppkeysByUpdateDate(){
        Date yesterday = DateUtils.localDateToDate(LocalDate.now());
        List<String> clientAppkeys = repository.queryClientAppkeysByUpdateDate(yesterday);
        Assert.assertTrue(CollectionUtils.isNotEmpty(clientAppkeys));
    }

    @Test
    public void delete(){
        AppkeyPaasCapacityRequest request = new AppkeyPaasCapacityRequest();
        request.setUpdateDate(DateUtils.localDateToDate(LocalDate.now().plusDays(-7)));
        List<AppkeyPaasCapacityDO> paasCapacityDOList = repository.query(request);
        Assert.assertNotNull(paasCapacityDOList);
        for (AppkeyPaasCapacityDO capacityDO : paasCapacityDOList) {
            Assert.assertTrue(repository.delete(capacityDO.getId()));
        }
    }
}