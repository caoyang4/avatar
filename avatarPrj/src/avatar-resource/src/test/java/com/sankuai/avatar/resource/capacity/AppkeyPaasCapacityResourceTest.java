package com.sankuai.avatar.resource.capacity;

import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.capacity.bo.AppkeyCapacitySummaryBO;
import com.sankuai.avatar.resource.capacity.bo.AppkeyPaasCapacityBO;
import com.sankuai.avatar.resource.capacity.constant.PaasCapacityType;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasCapacityRequestBO;
import com.sankuai.avatar.dao.cache.AppkeyCapacityCacheRepository;
import com.sankuai.avatar.dao.resource.repository.AppkeyPaasCapacityRepository;
import com.sankuai.avatar.dao.cache.model.CapacitySummary;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyPaasCapacityDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasCapacityRequest;
import com.sankuai.avatar.resource.capacity.impl.AppkeyPaasCapacityResourceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import org.junit.Assert;
import java.util.*;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2022-09-28 20:35
 */
@Slf4j
public class AppkeyPaasCapacityResourceTest extends TestBase {

    @Mock
    private AppkeyPaasCapacityRepository repository;
    @Mock
    private OpsHttpClient opsHttpClient;
    @Mock
    private AppkeyCapacityCacheRepository cacheRepository;

    private AppkeyPaasCapacityResource resource;

    final static List<Map<String, String>> CLIENT_CONFIG = Collections.singletonList(
            new HashMap<String, String>() {{put("producer.cluster.dispatch.type", "默认分配");}}
    );
    final static List<Map<String, String>> STANDARD_CONFIG = Arrays.asList(
            new HashMap<String, String>(){{put("producer.cluster.dispatch.type", "同地域集群优先");}},
            new HashMap<String, String>(){{put("producer.cluster.dispatch.type", "全部集群");}}
    );
    static AppkeyPaasCapacityRequestBO appkeyPaasCapacityRequestBO;
    static {
        appkeyPaasCapacityRequestBO = new AppkeyPaasCapacityRequestBO();
    }
    static AppkeyPaasCapacityDO appkeyPaasCapacityDO;
    static {
        appkeyPaasCapacityDO = new AppkeyPaasCapacityDO();
        appkeyPaasCapacityDO.setId(666);
        appkeyPaasCapacityDO.setClientAppkey("mapi-shop-web");
        appkeyPaasCapacityDO.setPaasName("Cellar");
        appkeyPaasCapacityDO.setType(PaasCapacityType.APPKEY.getCapacityType());
        appkeyPaasCapacityDO.setPaasAppkey("com.sankuai.tair.inf.banmasettest");
        appkeyPaasCapacityDO.setCapacityLevel(2);
        appkeyPaasCapacityDO.setStandardLevel(4);
        appkeyPaasCapacityDO.setIsCapacityStandard(false);
        appkeyPaasCapacityDO.setStandardReason("未达标");
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
        appkeyPaasCapacityDO.setUpdateDate(new Date());
    }

    static AppkeyPaasCapacityBO appkeyPaasCapacityBO;
    static {
        appkeyPaasCapacityBO = new AppkeyPaasCapacityBO();
        appkeyPaasCapacityBO.setClientAppkey("hui-jobs-service");
        appkeyPaasCapacityBO.setPaasName("Mafka");
        appkeyPaasCapacityBO.setType(PaasCapacityType.TOPIC);
        appkeyPaasCapacityBO.setPaasAppkey("com.sankuai.inf.mafka.brokerofflineyf");
        appkeyPaasCapacityBO.setCapacityLevel(4);
        appkeyPaasCapacityBO.setStandardLevel(4);
        appkeyPaasCapacityBO.setIsCapacityStandard(true);
        appkeyPaasCapacityBO.setStandardReason("x");
        appkeyPaasCapacityBO.setStandardTips("x");
        appkeyPaasCapacityBO.setClientConfig(CLIENT_CONFIG);
        appkeyPaasCapacityBO.setStandardConfig(STANDARD_CONFIG);
        appkeyPaasCapacityBO.setIsConfigStandard(false);
        appkeyPaasCapacityBO.setUpdateBy("zhangpanwei02");
        appkeyPaasCapacityBO.setTypeName("maoyan-mmdb-comment-qa");
        appkeyPaasCapacityBO.setTypeComment("猫眼电影 mmdb 对应maoyan-mmdb-comment");
        appkeyPaasCapacityBO.setIsCore(true);
        appkeyPaasCapacityBO.setOwner("lutao02");
        appkeyPaasCapacityBO.setIsWhite(false);
        appkeyPaasCapacityBO.setWhiteReason("");
        appkeyPaasCapacityBO.setUpdateDate(new Date());
        appkeyPaasCapacityBO.setIsSet(true);
        appkeyPaasCapacityBO.setSetName("test-set");
        appkeyPaasCapacityBO.setSetType("test-type");
    }

    @Override
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(AppkeyPaasCapacityDO.class, new Config());
        resource = new AppkeyPaasCapacityResourceImpl(repository, opsHttpClient, cacheRepository);
        when(repository.query(Mockito.any(AppkeyPaasCapacityRequest.class))).thenReturn(Collections.singletonList(appkeyPaasCapacityDO));
        when(repository.update(Mockito.any(AppkeyPaasCapacityDO.class))).thenReturn(true);
        when(repository.delete(Mockito.any(Integer.class))).thenReturn(true);
    }

    @Test
    public void testQuery() {
        List<AppkeyPaasCapacityBO> appkeyPaasCapacityBOList = resource.query(appkeyPaasCapacityRequestBO);
        Assert.assertNotNull(appkeyPaasCapacityBOList);
        Assert.assertEquals(1, appkeyPaasCapacityBOList.size());
        AppkeyPaasCapacityBO paasCapacityRO = appkeyPaasCapacityBOList.get(0);
        Assert.assertEquals(appkeyPaasCapacityDO.getId(), paasCapacityRO.getId());
        Assert.assertEquals("mapi-shop-web", paasCapacityRO.getClientAppkey());
        Assert.assertEquals("Cellar", paasCapacityRO.getPaasName());
        Assert.assertEquals(PaasCapacityType.APPKEY, paasCapacityRO.getType());
        List<Map<String, String>> clientConfig = paasCapacityRO.getClientConfig();
        Assert.assertEquals(1, clientConfig.size());
        Assert.assertEquals("默认分配", clientConfig.get(0).get("producer.cluster.dispatch.type"));
        List<Map<String, String>> standardConfig = paasCapacityRO.getStandardConfig();
        Assert.assertEquals(2, standardConfig.size());
        Assert.assertEquals("同地域集群优先", standardConfig.get(0).get("producer.cluster.dispatch.type"));
    }

    @Test
    public void testQueryPage() {
        when(repository.query(Mockito.any(AppkeyPaasCapacityRequest.class))).thenReturn(Collections.singletonList(appkeyPaasCapacityDO));
        PageResponse<AppkeyPaasCapacityBO> pageResponseBO = resource.queryPage(appkeyPaasCapacityRequestBO);
        Assert.assertNotNull(pageResponseBO);
        Assert.assertEquals(1, pageResponseBO.getPage());
    }

    @Test
    public void testSave() {
        Assert.assertTrue(resource.save(appkeyPaasCapacityBO));
    }

    @Test
    public void testDeleteByCondition() {
        Assert.assertFalse(resource.deleteByCondition(null));
        Assert.assertTrue(resource.deleteByCondition(AppkeyPaasCapacityRequestBO.builder().appkey("test").build()));
    }

    @Test
    public void getPageAggregatedByAppkey() {
        String appkey = "mapi-shop-web";
        when(repository.queryAggregatedClientAppkey(Mockito.any(Date.class), Mockito.anyString())).thenReturn(Collections.singletonList(appkeyPaasCapacityDO));
        PageResponse<AppkeyPaasCapacityBO> boPageResponse1 = resource.getPageAggregatedByAppkey(appkey, new Date(), null,1, 10, false);
        Assert.assertEquals(1, boPageResponse1.getPage());
        when(repository.queryAggregatedPaasAppkey(Mockito.any(Date.class), Mockito.anyString())).thenReturn(Collections.singletonList(appkeyPaasCapacityDO));
        PageResponse<AppkeyPaasCapacityBO> boPageResponse2 = resource.getPageAggregatedByAppkey(appkey, new Date(), null,1, 10, true);
        Assert.assertEquals(1, boPageResponse2.getPage());
    }

    @Test
    public void getPaasNamesByAppkeyWithNoCache() {
        when(cacheRepository.getPaasNamesByAppkey(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(Collections.emptyList());
        when(repository.queryPaasNamesByAppkey(Mockito.any(Date.class),Mockito.anyString())).thenReturn(Collections.singletonList("Mafka"));
        when(cacheRepository.setPaasNames(Mockito.anyString(),Mockito.anyList(),Mockito.anyBoolean(),Mockito.anyInt())).thenReturn(true);
        List<String> paasNames = resource.getPaasNamesByAppkey("x", new Date(), false);
        Assert.assertEquals(1, paasNames.size());
        verify(repository).queryPaasNamesByAppkey(Mockito.any(), Mockito.any());
        verify(cacheRepository, times(1)).setPaasNames(Mockito.anyString(),Mockito.anyList(),Mockito.anyBoolean(),Mockito.anyInt());

        when(repository.queryPaasNamesByPaasAppkey(Mockito.any(Date.class),Mockito.anyString())).thenReturn(Collections.singletonList("Mafka"));
        paasNames = resource.getPaasNamesByAppkey("x", new Date(), true);
        Assert.assertEquals(1, paasNames.size());
        verify(repository).queryPaasNamesByPaasAppkey(Mockito.any(), Mockito.any());
    }
    @Test
    public void getPaasNamesByAppkeyWithCache() {
        when(cacheRepository.getPaasNamesByAppkey(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(Collections.singletonList("Mafka"));
        List<String> paasNames = resource.getPaasNamesByAppkey("x", new Date(), true);
        Assert.assertEquals(1, paasNames.size());
        verify(repository, times(0)).queryPaasNamesByPaasAppkey(Mockito.any(), Mockito.any());
        verify(cacheRepository,times(0)).setPaasNames(Mockito.anyString(),Mockito.any(),Mockito.anyBoolean(),Mockito.anyInt());
    }

    @Test
    public void getPaasAppkeys() {
        when(repository.queryPaasAppkeysByUpdateDate(Mockito.any())).thenReturn(Collections.singletonList("xxx"));
        when(opsHttpClient.isExistAppkey(Mockito.any())).thenReturn(true);
        List<String> paasAppkeys = resource.getPaasAppkeys(new Date());
        verify(repository).queryPaasAppkeysByUpdateDate(Mockito.any());
        verify(opsHttpClient).isExistAppkey(Mockito.anyString());
        Assert.assertTrue(CollectionUtils.isNotEmpty(paasAppkeys));
    }

    @Test
    public void getClientAppkeys(){
        when(repository.queryClientAppkeysByUpdateDate(Mockito.any())).thenReturn(Collections.singletonList("xxx"));
        when(opsHttpClient.isExistAppkey(Mockito.any())).thenReturn(true);
        List<String> paasAppkeys = resource.getClientAppkeys(new Date());
        verify(repository).queryClientAppkeysByUpdateDate(Mockito.any());
        verify(opsHttpClient).isExistAppkey(Mockito.anyString());
        Assert.assertTrue(CollectionUtils.isNotEmpty(paasAppkeys));
    }

    @Test
    public void getPaasAppkeysThrowsException() {
        when(repository.queryPaasAppkeysByUpdateDate(Mockito.any())).thenReturn(Collections.singletonList("xxx"));
        when(opsHttpClient.isExistAppkey(Mockito.any())).thenThrow(SdkCallException.class);
        List<String> paasAppkeys = resource.getPaasAppkeys(new Date());
        verify(repository).queryPaasAppkeysByUpdateDate(Mockito.any());
        verify(opsHttpClient).isExistAppkey(Mockito.anyString());
        Assert.assertTrue(CollectionUtils.isEmpty(paasAppkeys));
    }

    @Test
    public void getAppkeyCapacitySummaryBO() {
        CapacitySummary capacitySummary = new CapacitySummary();
        capacitySummary.setCapacityLevel(3);
        when(cacheRepository.getAppkeySummary(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(capacitySummary);
        AppkeyCapacitySummaryBO summaryBO = resource.getAppkeyCapacitySummaryBO("x", true);
        verify(cacheRepository).getAppkeySummary(Mockito.anyString(),Mockito.anyBoolean());
        Assert.assertEquals(3, summaryBO.getCapacityLevel().intValue());
    }

    @Test
    public void testSetAppkeyCapacitySummaryBO(){
        when(cacheRepository.setAppkeySummary(Mockito.any(),Mockito.any(),Mockito.anyBoolean(),Mockito.anyInt())).thenReturn(true);
        Boolean cache = resource.setAppkeyCapacitySummaryBO("x", new AppkeyCapacitySummaryBO(), false);
        Assert.assertTrue(cache);
    }
}