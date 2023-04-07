package com.sankuai.avatar.resource.appkey;

import com.sankuai.avatar.client.banner.BannerHttpClient;
import com.sankuai.avatar.client.banner.response.ElasticTip;
import com.sankuai.avatar.client.dom.DomHttpClient;
import com.sankuai.avatar.client.dom.model.AppkeyResourceUtil;
import com.sankuai.avatar.client.ecs.EcsHttpClient;
import com.sankuai.avatar.client.ecs.model.BillingUnit;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.mgw.MgwHttpClient;
import com.sankuai.avatar.client.mgw.request.MgwVsRequest;
import com.sankuai.avatar.client.mgw.response.MgwVs;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.OpsSrv;
import com.sankuai.avatar.client.ops.request.SrvQueryRequest;
import com.sankuai.avatar.client.soa.ScHttpClient;
import com.sankuai.avatar.client.soa.model.ScAppkey;
import com.sankuai.avatar.client.soa.model.ScIsoltAppkey;
import com.sankuai.avatar.client.soa.model.ScPageResponse;
import com.sankuai.avatar.client.soa.model.ScV1Appkey;
import com.sankuai.avatar.client.soa.request.IsoltAppkeyRequest;
import com.sankuai.avatar.client.workflow.AvatarWorkflowHttpClient;
import com.sankuai.avatar.client.workflow.model.AppkeyFlow;
import com.sankuai.avatar.common.exception.ResourceNotFoundErrorException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.appkey.bo.*;
import com.sankuai.avatar.resource.appkey.impl.AppkeyResourceImpl;
import com.sankuai.avatar.resource.appkey.request.*;
import com.sankuai.avatar.dao.es.AppkeyEsRepository;
import com.sankuai.avatar.dao.es.exception.EsException;
import com.sankuai.avatar.dao.es.request.AppkeyQueryRequest;
import com.sankuai.avatar.dao.es.request.AppkeyTreeRequest;
import com.sankuai.avatar.dao.es.request.UserAppkeyRequest;
import com.sankuai.avatar.dao.resource.repository.AppkeyRepository;
import com.sankuai.avatar.dao.resource.repository.UserRelationRepository;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2022-12-14 13:44
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class AppkeyResourceTest extends TestBase {

    @Mock
    private AppkeyRepository appkeyRepository;
    @Mock
    private UserRelationRepository userRelationRepository;
    @Mock
    private AppkeyEsRepository esRepository;
    @Mock
    private ScHttpClient scHttpClient;
    @Mock
    private OpsHttpClient opsHttpClient;
    @Mock
    private DomHttpClient domHttpClient;
    @Mock
    private MgwHttpClient mgwHttpClient;
    @Mock
    private BannerHttpClient bannerHttpClient;
    @Mock
    private AvatarWorkflowHttpClient avatarWorkflowHttpClient;
    @Mock
    private EcsHttpClient ecsHttpClient;


    private AppkeyResource resource;

    @Override
    @Before
    public void setUp() throws Exception {
        resource = new AppkeyResourceImpl(appkeyRepository, userRelationRepository, esRepository, opsHttpClient,
                                          mgwHttpClient, scHttpClient, domHttpClient, bannerHttpClient, avatarWorkflowHttpClient, ecsHttpClient);
    }

    private static AppkeyDO appkeyDO;
    private static AppkeySrvsQueryRequest appkeySrvsQueryRequest;
    private static PageResponse<AppkeyDO> pageResponse = new PageResponse<>();

    static {
        appkeySrvsQueryRequest = new AppkeySrvsQueryRequest();
        appkeySrvsQueryRequest.setQuery("avatar");
        appkeySrvsQueryRequest.setUser("qinwei05");
        appkeySrvsQueryRequest.setCapacity("1");
        appkeySrvsQueryRequest.setRank("core");
        appkeySrvsQueryRequest.setPaas(false);
        appkeySrvsQueryRequest.setStateful(true);
        appkeySrvsQueryRequest.setCell("set");
        appkeySrvsQueryRequest.setType("mine");

        appkeyDO = new AppkeyDO(0, "appkey", "name", "description", "", "", "", "businessGroup", "srv", "pdl", "pdlName", "owt",
                "owtName", "rank", "containerType", "serviceType", false, "statefulReason", false, false, false,
                "tenant", "gitRepository", "projectType", false, 0, 0, 0, "capacityReason",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "capacityUpdateBy",
                "weekResourceUtil", "admin", "rdAdmin", "epAdmin", "opAdmin", 0, "applicationName",
                "applicationChName", "orgId", "orgName", "BACKEND", "billingUnitId", "billingUnit", "categories",
                "tags", "frameworks", "isBoughtExternal", false,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        pageResponse.setPage(1);
        pageResponse.setPageSize(1);
        pageResponse.setTotalCount(1);
        pageResponse.setTotalPage(1);
    }

    @Test
    public void getByAppkeyRandom() {
        when(appkeyRepository.query(Mockito.any(AppkeyRequest.class))).thenReturn(Arrays.asList(appkeyDO, appkeyDO, appkeyDO));
        List<AppkeyBO> appkeyBOList = resource.getByAppkeyRandom(1);
        Assert.assertEquals(1, appkeyBOList.size());
    }

    @Test
    public void getByAppkey() {
        when(appkeyRepository.query(Mockito.any(AppkeyRequest.class))).thenReturn(Collections.singletonList(appkeyDO));
        AppkeyBO appkeyBO = resource.getByAppkey("appkey");
        Assert.assertEquals("name", appkeyBO.getName());
        verify(appkeyRepository).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void getSrvKeyByAppkey() {
        when(opsHttpClient.getSrvKeyByAppkey(Mockito.anyString())).thenReturn("srv");
        String srv = resource.getSrvKeyByAppkey("appkey");
        Assert.assertEquals("srv", srv);
        verify(opsHttpClient).getSrvKeyByAppkey(Mockito.anyString());
    }

    @Test
    public void getAppkeyRelatedSrvInfo() {
        when(opsHttpClient.getSrvInfoByAppkey(Mockito.anyString())).thenReturn(OpsSrv.builder().appkey("appkey").build());
        OpsSrvBO opsSrvBO = resource.getAppkeyRelatedSrvInfo("appkey");
        Assert.assertEquals("appkey", opsSrvBO.getAppkey());
        verify(opsHttpClient).getSrvInfoByAppkey(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyByOps() {
        // Setup
        final OpsSrvBO expectedResult = new OpsSrvBO();
        expectedResult.setAppkey("appkey");

        when(opsHttpClient.getSrvKeyByAppkey("appkey")).thenReturn("srv");
        when(opsHttpClient.getSrvInfoBySrvKey("srv")).thenReturn(OpsSrv.builder().appkey("appkey").build());

        // Run the test
        final OpsSrvBO result = resource.getAppkeyByOps("appkey");

        // Verify the results
        assertThat(result.getAppkey()).isEqualTo(expectedResult.getAppkey());
    }

    @Test
    public void testGetAppkeyByOpsThatThrowsSdkCallException() {
        when(opsHttpClient.getSrvKeyByAppkey("appkey")).thenThrow(SdkCallException.class);
        assertThatThrownBy(() -> resource.getAppkeyByOps("appkey")).isInstanceOf(SdkCallException.class);
        verify(opsHttpClient).getSrvKeyByAppkey(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyByOpsThatThrowsSdkBusinessErrorException() {
        when(opsHttpClient.getSrvInfoByAppkey("appkey")).thenReturn(null);
        assertThatThrownBy(() -> resource.getAppkeyByOps("appkey")).isInstanceOf(ResourceNotFoundErrorException.class);
    }

    @Test
    public void testGetAppkeyByScV2() {
        // Setup
        final ScAppkeyBO expectedResult = new ScAppkeyBO();
        expectedResult.setAppKey("appKey");

        // Configure ScHttpClient.getAppkeysInfo(...).
        final ScAppkey scAppkey = new ScAppkey();
        scAppkey.setAppKey("appKey");

        final List<ScAppkey> scAppkeyList = Collections.singletonList(scAppkey);
        when(scHttpClient.getAppkeysInfo(any(List.class))).thenReturn(scAppkeyList);
        final ScV1Appkey scV1Appkey = new ScV1Appkey();
        scV1Appkey.setTenant("");
        scV1Appkey.setIsBoughtExternal("");
        scV1Appkey.setFrameworks(Collections.singletonList(""));
        scV1Appkey.setCompatibleIpv6(Boolean.FALSE);
        scV1Appkey.setEpAdmin("");
        scV1Appkey.setOpAdmin("");
        scV1Appkey.setRdAdmin("");
        when(scHttpClient.getAppkeyInfoByV1("appkey")).thenReturn(scV1Appkey);

        // Run the test
        final ScAppkeyBO result = resource.getAppkeyBySc("appkey");

        // Verify the results
        assertThat(result.getAppKey()).isEqualTo(expectedResult.getAppKey());
    }

    @Test
    public void testGetAppkeyByScV2ThatScHttpClientReturnsNoItems() {
        when(scHttpClient.getAppkeysInfo(any(List.class))).thenReturn(new ArrayList<>());
        assertThatThrownBy(() -> resource.getAppkeyBySc("appkey")).isInstanceOf(ResourceNotFoundErrorException.class);
    }

    @Test
    public void testGetAppkeyByScV2ThatThrowsSdkCallException() {
        when(scHttpClient.getAppkeysInfo(any(List.class))).thenThrow(SdkCallException.class);
        assertThatThrownBy(() -> resource.getAppkeyBySc("appkey")).isInstanceOf(SdkCallException.class);
        verify(scHttpClient).getAppkeysInfo(any(List.class));
    }

    @Test
    public void testGetAppkeyByScV2ThatThrowsSdkBusinessErrorException() {
        when(scHttpClient.getAppkeysInfo(any(List.class))).thenThrow(SdkBusinessErrorException.class);
        assertThatThrownBy(() -> resource.getAppkeyBySc("appkey")).isInstanceOf(SdkBusinessErrorException.class);
        verify(scHttpClient).getAppkeysInfo(any(List.class));
    }

    @Test
    public void testGetAppkeyResourceUtil() {
        final AppkeyResourceUtilBO expectedResult = new AppkeyResourceUtilBO();
        expectedResult.setAppkey("appKey");

        final AppkeyResourceUtil appkeyResourceUtil = new AppkeyResourceUtil();
        appkeyResourceUtil.setAppkey("appKey");

        when(domHttpClient.getAppkeyResourceUtil("appkey")).thenReturn(appkeyResourceUtil);

        final AppkeyResourceUtilBO result = resource.getAppkeyResourceUtil("appkey");
        assertThat(result.getAppkey()).isEqualTo(expectedResult.getAppkey());
    }

    @Test
    public void testGetAppkeyResourceUtilThatThrowsSdkCallException() {
        when(domHttpClient.getAppkeyResourceUtil("appkey")).thenThrow(SdkCallException.class);
        assertThatThrownBy(() -> resource.getAppkeyResourceUtil("appkey")).isInstanceOf(SdkCallException.class);
        verify(domHttpClient).getAppkeyResourceUtil(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyResourceUtilThatThrowsSdkBusinessErrorException() {
        when(domHttpClient.getAppkeyResourceUtil("appkey")).thenThrow(SdkBusinessErrorException.class);
        assertThatThrownBy(() -> resource.getAppkeyResourceUtil("appkey")).isInstanceOf(SdkBusinessErrorException.class);
        verify(domHttpClient).getAppkeyResourceUtil(Mockito.anyString());
    }

    @Test
    public void getBySrvByEs() {
        pageResponse.setItems(Collections.singletonList(appkeyDO));
        when(esRepository.query(Mockito.any(AppkeyQueryRequest.class))).thenReturn(pageResponse);
        AppkeyBO appkeyBO = resource.getBySrv("appkey");
        Assert.assertEquals("name", appkeyBO.getName());
        verify(esRepository).query(Mockito.any(AppkeyQueryRequest.class));
        verify(appkeyRepository, times(0)).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void getBySrvByDb() {
        when(appkeyRepository.query(Mockito.any(AppkeyRequest.class))).thenReturn(Collections.emptyList());
        AppkeyBO appkeyBO = resource.getBySrv("appkey");
        Assert.assertNull(appkeyBO);
        verify(appkeyRepository).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void queryPage() {
        when(appkeyRepository.query(Mockito.any(AppkeyRequest.class))).thenReturn(Collections.singletonList(appkeyDO));
        PageResponse<AppkeyBO> boPageResponse = resource.queryPage(new AppkeyRequestBO());
        Assert.assertEquals(1, boPageResponse.getPage());
        verify(appkeyRepository).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void searchAppkeyByEs() {
        pageResponse.setItems(Collections.singletonList(appkeyDO));
        when(esRepository.search(Mockito.any(AppkeyQueryRequest.class))).thenReturn(pageResponse);
        PageResponse<AppkeyBO> pageResponse = resource.searchAppkey(new AppkeySearchRequestBO());
        Assert.assertEquals("name", pageResponse.getItems().get(0).getName());
        verify(esRepository).search(Mockito.any(AppkeyQueryRequest.class));
        verify(appkeyRepository, times(0)).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void searchAppkeyByDb() {
        when(esRepository.search(Mockito.any(AppkeyQueryRequest.class))).thenThrow(EsException.class);
        when(appkeyRepository.query(Mockito.any(AppkeyRequest.class))).thenReturn(Collections.singletonList(appkeyDO));
        PageResponse<AppkeyBO> pageResponse = resource.searchAppkey(new AppkeySearchRequestBO());
        Assert.assertEquals(1, pageResponse.getPage());
        verify(esRepository).search(Mockito.any(AppkeyQueryRequest.class));
        verify(appkeyRepository).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void getByHost() {
        when(opsHttpClient.getAppkeyByHost(Mockito.anyString())).thenReturn("appkey");
        String appkey = resource.getByHost("127.0.0.1");
        Assert.assertEquals("appkey", appkey);
        verify(opsHttpClient).getAppkeyByHost(Mockito.anyString());
    }

    @Test
    public void getByHostThrowException() {
        when(opsHttpClient.getAppkeyByHost(Mockito.anyString())).thenThrow(SdkCallException.class);
        String appkey = resource.getByHost("127.0.0.1");
        Assert.assertNull(appkey);
        verify(opsHttpClient).getAppkeyByHost(Mockito.anyString());
    }

    @Test
    public void getOwnAppkeyByEs() {
        PageResponse<AppkeyDO> doPageResponse = new PageResponse<>();
        doPageResponse.setItems(Collections.singletonList(appkeyDO));

        when(esRepository.getOwnAppkey(Mockito.any(UserAppkeyRequest.class), Mockito.any())).thenReturn(doPageResponse);
        List<AppkeyBO> boList = resource.getOwnAppkey(new AppkeySrvsQueryRequest()).getItems();
        Assert.assertEquals("name", boList.get(0).getName());
        verify(esRepository).getOwnAppkey(Mockito.any(UserAppkeyRequest.class), Mockito.any());
        verify(appkeyRepository, times(0)).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void getOwnAppkeyByDb() {
        when(esRepository.getOwnAppkey(Mockito.any(UserAppkeyRequest.class), Mockito.any())).thenThrow(EsException.class);
        when(appkeyRepository.query(Mockito.any(AppkeyRequest.class))).thenReturn(Collections.singletonList(appkeyDO));

        List<AppkeyBO> boList = resource.getOwnAppkey(appkeySrvsQueryRequest).getItems();
        Assert.assertEquals(0, boList.size());
        verify(esRepository).getOwnAppkey(Mockito.any(UserAppkeyRequest.class), Mockito.any());
        verify(appkeyRepository).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void getByVip() {
        MgwVs mgwVs = new MgwVs();
        mgwVs.setAppkey("appkey");
        Assert.assertTrue(CollectionUtils.isEmpty(resource.getByVip("")));
        when(mgwHttpClient.getVsList(Mockito.any(MgwVsRequest.class), Mockito.any(EnvEnum.class))).thenReturn(Collections.singletonList(mgwVs));
        List<String> appkeys = resource.getByVip("0.0.0.0");
        Assert.assertEquals(1, appkeys.size());
        verify(mgwHttpClient).getVsList(Mockito.any(MgwVsRequest.class), Mockito.any(EnvEnum.class));
    }

    @Test
    public void favorAppkey() {
        when(opsHttpClient.favorAppkey(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Boolean favor = resource.favorAppkey("appkey", "mis");
        Assert.assertTrue(favor);
        verify(opsHttpClient).favorAppkey(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void favorAppkeyThrowException() {
        when(opsHttpClient.favorAppkey(Mockito.anyString(), Mockito.anyString())).thenThrow(SdkCallException.class);
        Boolean favor = resource.favorAppkey("appkey", "mis");
        Assert.assertFalse(favor);
        verify(opsHttpClient).favorAppkey(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void cancelFavorAppkey() {
        when(opsHttpClient.cancelFavorAppkey(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Boolean cancel = resource.cancelFavorAppkey("appkey", "mis");
        Assert.assertTrue(cancel);
        verify(opsHttpClient).cancelFavorAppkey(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void cancelFavorAppkeyThrowException() {
        when(opsHttpClient.cancelFavorAppkey(Mockito.anyString(), Mockito.anyString())).thenThrow(SdkCallException.class);
        Boolean cancel = resource.cancelFavorAppkey("appkey", "mis");
        Assert.assertFalse(cancel);
        verify(opsHttpClient).cancelFavorAppkey(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void getFavorAppkey() {
        when(opsHttpClient.getUserFavorAppkey(Mockito.any(SrvQueryRequest.class))).thenReturn(Collections.singletonList("appkey"));
        when(appkeyRepository.query(Mockito.any(AppkeyRequest.class))).thenReturn(Collections.singletonList(appkeyDO));
        List<AppkeyBO> boList = resource.getFavorAppkey(new AppkeySrvsQueryRequest());
        Assert.assertEquals("name", boList.get(0).getName());
        verify(opsHttpClient).getUserFavorAppkey(Mockito.any(SrvQueryRequest.class));
        verify(appkeyRepository).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void getPageAppkeyByEs() {
        pageResponse.setItems(Collections.singletonList(appkeyDO));
        when(esRepository.getPageAppkey(Mockito.any(AppkeyTreeRequest.class))).thenReturn(pageResponse);
        PageResponse<AppkeyBO> boPageResponse = resource.getPageAppkey(new AppkeyTreeQueryRequestBO());
        Assert.assertTrue(CollectionUtils.isNotEmpty(boPageResponse.getItems()));
        verify(esRepository).getPageAppkey(Mockito.any(AppkeyTreeRequest.class));
        verify(appkeyRepository, times(0)).query(Mockito.any(AppkeyRequest.class));
    }

    @Test
    public void getPageAppkeyByDb() {
        when(esRepository.getPageAppkey(Mockito.any(AppkeyTreeRequest.class))).thenThrow(EsException.class);
        when(appkeyRepository.query(Mockito.any(AppkeyRequest.class))).thenReturn(Collections.singletonList(appkeyDO));
        PageResponse<AppkeyBO> boPageResponse = resource.getPageAppkey(new AppkeyTreeQueryRequestBO());
        Assert.assertNotNull(boPageResponse);
        verify(esRepository).getPageAppkey(Mockito.any(AppkeyTreeRequest.class));
        verify(appkeyRepository).query(Mockito.any(AppkeyRequest.class));
    }


    @Test
    public void testGetIsoltAppkeys() {
        IsoltAppkeyRequestBO isoltAppkeyRequestBO = new IsoltAppkeyRequestBO();
        isoltAppkeyRequestBO.setPage(1);
        isoltAppkeyRequestBO.setPageSize(10);

        final ScPageResponse<ScIsoltAppkey> scIsoltAppkeyPageResponse = new ScPageResponse<>();
        scIsoltAppkeyPageResponse.setTn(17);
        scIsoltAppkeyPageResponse.setCn(1);
        scIsoltAppkeyPageResponse.setPn(2);
        scIsoltAppkeyPageResponse.setSn(10);
        scIsoltAppkeyPageResponse.setItems(Arrays.asList(
                ScIsoltAppkey.builder().appKey("appKey1").build(),
                ScIsoltAppkey.builder().appKey("appKey2").build()
        ));
        when(scHttpClient.getIsoltAppkeys(Mockito.any(IsoltAppkeyRequest.class))).thenReturn(scIsoltAppkeyPageResponse);
        PageResponse<ScIsoltAppkeyBO> pageResponse = resource.getIsoltAppkeys(isoltAppkeyRequestBO);
        Assert.assertEquals(2, pageResponse.getItems().size());

        // Test Throw SdkCallException
        when(scHttpClient.getIsoltAppkeys(Mockito.any(IsoltAppkeyRequest.class))).thenThrow(SdkCallException.class);
        assertThatThrownBy(() -> resource.getIsoltAppkeys(isoltAppkeyRequestBO)).isInstanceOf(SdkCallException.class);

        // Test Throw SdkBusinessErrorException
        when(scHttpClient.getIsoltAppkeys(Mockito.any(IsoltAppkeyRequest.class))).thenThrow(SdkBusinessErrorException.class);
        assertThatThrownBy(() -> resource.getIsoltAppkeys(isoltAppkeyRequestBO)).isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetElasticTips() {
        ElasticTip elasticTip = new ElasticTip();
        elasticTip.setLink("link");

        final ElasticTipBO elasticTipBO = new ElasticTipBO();
        elasticTip.setLink("link");

        when(bannerHttpClient.getElasticTips()).thenReturn(elasticTip);
        ElasticTipBO result = resource.getElasticTips();
        Assert.assertEquals(result.getLinkTip(), elasticTipBO.getLinkTip());
    }

    @Test
    public void testGetElasticGrayScale() {
        when(bannerHttpClient.getElasticGrayScale("meituan.avatar")).thenReturn(Boolean.TRUE);
        Boolean result = resource.getElasticGrayScale("meituan.avatar");
        Assert.assertTrue(result);
    }

    @Test
    public void batchGetAppkeyRunningAndHoldingFlowList() {
        PageResponse<AppkeyFlow> appkeyFlowPageResponse = new PageResponse<>();
        AppkeyFlow appkeyFlow = new AppkeyFlow();
        appkeyFlow.setAppkey("appkey");
        appkeyFlowPageResponse.setItems(Collections.singletonList(appkeyFlow));

        when(avatarWorkflowHttpClient.batchGetAppkeyFlowList(Collections.singletonList("appkey"), "RUNNING,HOLDING")).thenReturn(appkeyFlowPageResponse);
        PageResponse<AppkeyFlowBO> result = resource.batchGetAppkeyRunningAndHoldingFlowList(Collections.singletonList("appkey"));
        Assert.assertTrue(result.getItems().size() > 0);
    }

    /**
     * 测试从ECS获取服务的结算单元信息
     */
    @Test
    public void testGetAppkeyUnitList() {
        BillingUnit billingUnit = BillingUnit.builder().billingUnitId(31).build();
        when(ecsHttpClient.getAppkeyUnitList("appkey")).thenReturn(billingUnit);
        BillingUnitBO billingUnitBO = resource.getAppkeyUnitList("appkey");
        Assert.assertEquals(31, (int) billingUnitBO.getBillingUnitId());
    }
}