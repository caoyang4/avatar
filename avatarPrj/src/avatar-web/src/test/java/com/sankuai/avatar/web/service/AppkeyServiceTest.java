package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.exception.ResourceNotFoundErrorException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.*;
import com.sankuai.avatar.resource.appkey.request.*;
import com.sankuai.avatar.resource.favor.UserRelationResource;
import com.sankuai.avatar.resource.octo.OctoResource;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.*;
import com.sankuai.avatar.resource.tree.request.SrvQueryRequestBO;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.web.constant.UtilizationStandardTypeEnum;
import com.sankuai.avatar.web.dal.entity.CasUser;
import com.sankuai.avatar.web.dto.appkey.*;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeSrvDTO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.request.AppkeyQueryPageRequest;
import com.sankuai.avatar.web.request.AppkeySearchPageRequest;
import com.sankuai.avatar.web.request.AppkeyTreeQueryRequest;
import com.sankuai.avatar.web.request.IsoltAppkeyPageRequest;
import com.sankuai.avatar.web.service.impl.AppkeyServiceImpl;
import com.sankuai.avatar.web.util.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author caoyang
 * @create 2022-12-14 14:41
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {UserUtils.class})
public class AppkeyServiceTest  {

    private AppkeyService service;
    @Mock
    private AppkeyResource appkeyResource;
    @Mock
    private UserResource userResource;
    @Mock
    private UserRelationResource relationResource;
    @Mock
    private AppkeyTreeResource appkeyTreeResource;
    @Mock
    private WhitelistService whitelistService;
    @Mock
    private UserService userService;
    @Mock
    private OctoResource octoResource;

    static AppkeyBO appkeyBO = new AppkeyBO();
    static PageResponse<AppkeyBO> boPageResponse = new PageResponse<>();
    static {
        appkeyBO.setAppkey("appkey");
        appkeyBO.setName("name");
        appkeyBO.setSrv("meituan.unit.test.appkey");
        appkeyBO.setIsBoughtExternal(null);

        boPageResponse.setTotalPage(1);
        boPageResponse.setTotalCount(1);
        boPageResponse.setPage(1);
        boPageResponse.setPageSize(1);
    }

    @Before
    public void setUp() throws Exception {
        service = new AppkeyServiceImpl(appkeyResource, userResource, relationResource, appkeyTreeResource, whitelistService, userService, octoResource);
    }

    @Test
    public void getByAppkeyRandom() {
        // Setup
        final AppkeyDTO appkeyDTO = new AppkeyDTO();
        appkeyDTO.setAppkey("appkey");
        final List<AppkeyDTO> expectedResult = Collections.singletonList(appkeyDTO);

        // Configure AppkeyResource.getByAppkeyRandom(...).
        final AppkeyBO appkeyBO = new AppkeyBO();
        appkeyBO.setAppkey("appkey");
        final List<AppkeyBO> appkeyBOList = Collections.singletonList(appkeyBO);
        when(appkeyResource.getByAppkeyRandom(1)).thenReturn(appkeyBOList);

        // Run the test
        final List<AppkeyDTO> result = service.getByAppkeyRandom(1);

        // Verify the results
        Assert.assertEquals(result.size(), expectedResult.size());
        assertThat(result.get(0).getAppkey()).isEqualTo(expectedResult.get(0).getAppkey());
    }

    @Test
    public void testGetByAppkeyRandomThatReturnsNoItems() {
        // Setup
        when(appkeyResource.getByAppkeyRandom(1)).thenReturn(Collections.emptyList());

        // Run the test
        final List<AppkeyDTO> result = service.getByAppkeyRandom(1);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    /**
     * 获取appkey资源利用率
     */
    @Test
    public void getAppkeyUtilization() {
        // Setup
        final AppkeyResourceUtilDTO expectedResult = AppkeyResourceUtilDTO.builder().util(0.0).isWhite(true).build();

        // Configure AppkeyResource.getAppkeyResourceUtil(...).
        final AppkeyResourceUtilBO appkeyResourceUtilBO = new AppkeyResourceUtilBO();
        appkeyResourceUtilBO.setStandardReason("standardReason");
        final AppkeyResourceUtilBO.CellList cellList = new AppkeyResourceUtilBO.CellList();
        cellList.setLastWeekValue(0.0);
        cellList.setStandardReason("standardReason");
        appkeyResourceUtilBO.setOptimizeMsg(Collections.singletonList("value"));
        final AppkeyResourceUtilBO.MsgConfig msgConfig = new AppkeyResourceUtilBO.MsgConfig();
        msgConfig.setBaseline(0.0);
        appkeyResourceUtilBO.setMsgConfig(msgConfig);
        appkeyResourceUtilBO.setUtilizationStandard("SKIP_STANDARD");
        final AppkeyResourceUtilBO.ResourceUtil resourceUtil = new AppkeyResourceUtilBO.ResourceUtil();
        resourceUtil.setLastWeekValue(0.0);
        appkeyResourceUtilBO.setResourceUtil(resourceUtil);
        when(appkeyResource.getAppkeyResourceUtil("appkey")).thenReturn(appkeyResourceUtilBO);

        // Configure WhitelistService.getServiceWhitelistByAppkey(...).
        final ServiceWhitelistDTO serviceWhitelistDTO = new ServiceWhitelistDTO();
        serviceWhitelistDTO.setAppkey("appkey");

        final List<ServiceWhitelistDTO> serviceWhitelistDTOS = Collections.singletonList(serviceWhitelistDTO);
        when(whitelistService.getServiceWhitelistByAppkey("appkey", WhiteType.UTILIZATION)).thenReturn(serviceWhitelistDTOS);

        // Run the test
        final AppkeyResourceUtilDTO result = service.getAppkeyUtilization("appkey");

        // Verify the results
        assertThat(result.getUtil()).isEqualTo(expectedResult.getUtil());
        assertThat(result.getIsWhite()).isEqualTo(expectedResult.getIsWhite());
    }

    /**
     * 获取appkey资源利用率: DOM接口异常
     */
    @Test
    public void testGetAppkeyUtilizationThatThrowsSdkCallException() {
        // Setup
        final AppkeyResourceUtilDTO expectedResult = AppkeyResourceUtilDTO.builder()
                .standardTips("")
                .standardData(">20%")
                .standardReason("")
                .standardType(UtilizationStandardTypeEnum.SKIP_STANDARD.getName())
                .standardTypeCn(UtilizationStandardTypeEnum.SKIP_STANDARD.getCnName())
                .util((double) 0)
                .isStandard(true)
                .isWhite(false)
                .isApplyWhite(true)
                .build();
        when(appkeyResource.getAppkeyResourceUtil("appkey")).thenThrow(SdkCallException.class);
        when(appkeyResource.getAppkeyResourceUtil("appkey1")).thenThrow(SdkBusinessErrorException.class);
        when(appkeyResource.getAppkeyResourceUtil("appkey2")).thenThrow(ResourceNotFoundErrorException.class);


        // Run the test
        final AppkeyResourceUtilDTO result = service.getAppkeyUtilization("appkey");
        final AppkeyResourceUtilDTO result1 = service.getAppkeyUtilization("appkey1");
        final AppkeyResourceUtilDTO result2 = service.getAppkeyUtilization("appkey2");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        assertThat(result1).isEqualTo(expectedResult);
        assertThat(result2).isEqualTo(expectedResult);
    }

    @Test
    public void getByAppkey() {
        // mock 静态方法
        PowerMockito.mockStatic(UserUtils.class);
        CasUser casUser = new CasUser();
        casUser.setLoginName("testUser");
        PowerMockito.when(UserUtils.getCurrentCasUser()).thenReturn(casUser);

        when(appkeyResource.getByAppkey(Mockito.anyString())).thenReturn(appkeyBO);
        AppkeyDetailDTO appkeyDetailDTO = service.getAppkeyDetailByRepository("appkey");
        Assert.assertEquals("meituan.unit.test.appkey", appkeyDetailDTO.getKey());
        assertThat(appkeyDetailDTO.getIsBoughtExternal()).isEqualTo("UNCERTAIN");
        verify(appkeyResource).getByAppkey(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyDetailInfoByHttpClient() {
        // Setup
        final AppkeyDetailDTO expectedResult = new AppkeyDetailDTO();
        final TeamDTO team = new TeamDTO();
        team.setOrgName("A/B/C");
        team.setOrgId("1/2/3");
        expectedResult.setTeam(team);
        final AppkeyUserDTO appkeyUserDTO = new AppkeyUserDTO();
        appkeyUserDTO.setLoginName("srvRdValue");
        appkeyUserDTO.setName("srvRdValue");
        expectedResult.setAdmin(appkeyUserDTO);
        expectedResult.setRdAdmin(Collections.singletonList(appkeyUserDTO));
        expectedResult.setEpAdmin(Collections.singletonList(appkeyUserDTO));
        expectedResult.setOpAdmin(Collections.singletonList(appkeyUserDTO));
        expectedResult.setKey("key");

        when(appkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");

        // mock 静态方法
        PowerMockito.mockStatic(UserUtils.class);
        CasUser casUser = new CasUser();
        casUser.setLoginName("testUser");
        PowerMockito.when(UserUtils.getCurrentCasUser()).thenReturn(casUser);

        // Configure AppkeyTreeResource.getAppkeyTreeByKey(...).
        final AppkeyTreeBO appkeyTreeBO = new AppkeyTreeBO(
                new AppkeyTreeSrvBO("appkey", "comment", "name", "key",
                        "soaapp", "module", "soasrv", "rank",
                        "projectType", "category", "soamod",
                        "containerType", "serviceType", false, "statefulReason",
                        false, "capacityReason",
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        "capacityUpdateBy", 0, "tenant",
                        Collections.singletonList("srvRdValue"),
                        Collections.singletonList("srvOpValue"),
                        Collections.singletonList("srvEpValue"), false, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()),
                new AppkeyTreePdlBO(0, "name", "key", "comment",
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        Collections.singletonList("value"),
                        Collections.singletonList("value"),
                        Collections.singletonList("value"), AppkeyTreeOwtBO.builder()
                        .rdAdmin(Collections.singletonList("pdlRdValue"))
                        .opAdmin(Collections.singletonList("pdlOpValue"))
                        .epAdmin(Collections.singletonList("pdlEpValue"))
                        .build(), 0),
                AppkeyTreeOwtBO.builder()
                        .rdAdmin(Collections.singletonList("owtRdValue"))
                        .opAdmin(Collections.singletonList("owtOpValue"))
                        .epAdmin(Collections.singletonList("owtEpValue"))
                        .build(),
                new AppkeyTreeBgBO(0, "name")
        );

        when(appkeyTreeResource.getAppkeyTreeByKey("srv")).thenReturn(appkeyTreeBO);
        when(appkeyTreeResource.getSrvSubscribers("srv")).thenReturn(Collections.singletonList("value"));

        // Configure AppkeyResource.getAppkeyBySc(...).
        final ScAppkeyBO scAppkeyBO = new ScAppkeyBO();
        ScAppkeyBO.Team team1 = new ScAppkeyBO.Team();
        team1.setDisplayName("A/B/C");
        team1.setOrgIdList("1/2/3");
        scAppkeyBO.setAppKey("appKey");
        scAppkeyBO.setTeam(team1);
        ScAppkeyBO.Admin admin = new ScAppkeyBO.Admin();
        admin.setMis("value");
        scAppkeyBO.setAdmin(admin);
        scAppkeyBO.setIsBoughtExternal(null);
        when(appkeyResource.getAppkeyBySc("appkey")).thenReturn(scAppkeyBO);

        // Configure UserResource.queryByMisWithOrder(...).
        UserBO userBO = UserBO.builder().mis("srvRdValue").name("srvRdValue").build();
        final List<UserBO> userBOS = Collections.singletonList(userBO);
        when(userResource.queryByMisWithOrder(any())).thenReturn(userBOS);

        // Run the test
        final AppkeyDetailDTO result = service.getAppkeyDetailInfoByHttpClient("appkey");

        // Verify the results
        assertThat(result.getKey()).isEqualTo(expectedResult.getKey());
        assertThat(result.getIsBoughtExternal()).isEqualTo("UNCERTAIN");

        List<String> rdAdmis = result.getRdAdmin().stream().map(AppkeyUserDTO::getLoginName).collect(Collectors.toList());
        assertThat(rdAdmis).contains("srvRdValue");
    }

    @Test
    public void testGetAppkeyDetailInfoByHttpClientThatThrowsSdkCallException() {
        // Setup
        when(appkeyResource.getSrvKeyByAppkey("appkey")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> service.getAppkeyDetailInfoByHttpClient("appkey")).isInstanceOf(SdkCallException.class);
        verify(appkeyResource).getSrvKeyByAppkey(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyDetailInfoByHttpClientThatThrowsSdkBusinessErrorException() {
        // Setup
        when(appkeyResource.getSrvKeyByAppkey("appkey")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> service.getAppkeyDetailInfoByHttpClient("appkey")).isInstanceOf(SdkBusinessErrorException.class);
        verify(appkeyResource).getSrvKeyByAppkey(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyDetailInfoByHttpClientThatAllThrowsSdkCallException() {
        // Setup
        when(appkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");
        when(appkeyTreeResource.getAppkeyTreeByKey("srv")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> service.getAppkeyDetailInfoByHttpClient("appkey")).isInstanceOf(SdkCallException.class);
        verify(appkeyResource).getSrvKeyByAppkey(Mockito.anyString());
        verify(appkeyTreeResource).getAppkeyTreeByKey(Mockito.anyString());
    }

    @Test
    public void testGetAppkeyDetailInfoByHttpClientThatAllThrowsSdkBusinessErrorException() {
        // Setup
        when(appkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");
        when(appkeyTreeResource.getAppkeyTreeByKey("srv")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> service.getAppkeyDetailInfoByHttpClient("appkey")).isInstanceOf(SdkBusinessErrorException.class);
        verify(appkeyResource).getSrvKeyByAppkey(Mockito.anyString());
        verify(appkeyTreeResource).getAppkeyTreeByKey(Mockito.anyString());
    }

    @Test
    public void testGetUserRelatedAppkeysByHttpClient() {
        // Setup
        final AppkeyTreeQueryRequest appkeyTreeQueryRequest = new AppkeyTreeQueryRequest();
        appkeyTreeQueryRequest.setPage(1);
        appkeyTreeQueryRequest.setPageSize(10);
        appkeyTreeQueryRequest.setUser("user");
        appkeyTreeQueryRequest.setType("type");
        appkeyTreeQueryRequest.setQuery("query");
        appkeyTreeQueryRequest.setStateful(false);
        appkeyTreeQueryRequest.setRank("rank");
        appkeyTreeQueryRequest.setCapacity("capacity");
        appkeyTreeQueryRequest.setRdAdmin("rdAdmin");
        appkeyTreeQueryRequest.setEpAdmin("epAdmin");
        appkeyTreeQueryRequest.setOpAdmin("opAdmin");

        final AppkeyUserDTO appkeyUserDTO = new AppkeyUserDTO();
        appkeyUserDTO.setLoginName("loginName");
        appkeyUserDTO.setName("name");
        appkeyUserDTO.setAvatarUrl("avatarUrl");
        appkeyUserDTO.setOrgName("orgName");
        AppkeyTreeSrvDTO appkeyTreeSrvDTO = AppkeyTreeSrvDTO.builder().appkey("appkey")
                .rdAdmin(Collections.singletonList(appkeyUserDTO))
                .epAdmin(Collections.singletonList(appkeyUserDTO))
                .opAdmin(Collections.singletonList(appkeyUserDTO))
                .build();
        final List<AppkeyTreeSrvDTO> expectedResult = Collections.singletonList(appkeyTreeSrvDTO);

        // Configure AppkeyTreeResource.getSrvsByQueryRequest(...).
        AppkeyTreeSrvBO appkeyTreeSrvBO = new AppkeyTreeSrvBO("appkey", "comment", "name",
                "key", "soaapp", "module", "soasrv", "rank",
                "projectType", "category", "soamod",
                "containerType", "serviceType", false,
                "statefulReason", false, "capacityReason",
                new Date(), "capacityUpdateBy", 0,
                "tenant", Collections.singletonList("value"),
                Collections.singletonList("value"), Collections.singletonList("value"), false,
                false, new Date());
        AppkeyTreeSrvDetailBO appkeyTreeSrvDetailBO = AppkeyTreeSrvDetailBO.builder()
                .hostCount(0)
                .appkeys(Collections.singletonList("value"))
                .users(new HashMap<>())
                .srv(appkeyTreeSrvBO)
                .build();
        final List<AppkeyTreeSrvDetailBO> appkeyTreeSrvDetailBOS = Collections.singletonList(appkeyTreeSrvDetailBO);
        AppkeyTreeSrvDetailResponseBO appkeyTreeSrvDetailResponseBO = new AppkeyTreeSrvDetailResponseBO();
        appkeyTreeSrvDetailResponseBO.setSrvs(appkeyTreeSrvDetailBOS);
        when(appkeyTreeResource.getSrvsByQueryRequest(any(SrvQueryRequestBO.class))).thenReturn(appkeyTreeSrvDetailResponseBO);

        // Configure AppkeyResource.batchGetAppkeyBySc(...).
        final ScAppkeyBO scAppkeyBO = new ScAppkeyBO();
        scAppkeyBO.setAppKey("appKey");
        scAppkeyBO.setDescription("description");
        scAppkeyBO.setApplicationName("applicationName");
        scAppkeyBO.setModuleName("moduleName");
        scAppkeyBO.setServiceName("serviceName");
        final ScAppkeyBO.ApplicationAdmin applicationAdmin = new ScAppkeyBO.ApplicationAdmin();
        applicationAdmin.setMis("mis");
        applicationAdmin.setName("name");
        applicationAdmin.setOnJob(false);
        scAppkeyBO.setApplicationAdmin(applicationAdmin);
        final ScAppkeyBO.Admin admin = new ScAppkeyBO.Admin();
        admin.setMis("mis");
        admin.setName("name");
        admin.setOnJob(false);
        scAppkeyBO.setAdmin(admin);
        final ScAppkeyBO.Members members = new ScAppkeyBO.Members();
        scAppkeyBO.setMembers(Collections.singletonList(members));
        scAppkeyBO.setType("type");
        final List<ScAppkeyBO> scAppkeyBOList = Collections.singletonList(scAppkeyBO);

        when(appkeyResource.batchGetAppkeyBySc(Collections.singletonList("value"))).thenReturn(scAppkeyBOList);
        when(relationResource.getUserTopAppkey("user")).thenReturn(Collections.singletonList("value"));

        // Run the test
        final List<AppkeyTreeSrvDTO> result = service.getMineAppkeysByHttpClient(appkeyTreeQueryRequest);

        // Verify the results
        assertThat(result).hasSameSizeAs(expectedResult).hasSize(1);
        assertThat(result.get(0).getAppkey()).isEqualTo(expectedResult.get(0).getAppkey());
    }

    @Test
    public void testGetUserRelatedAppkeysByHttpClientThatThrowsSdkCallException() {
        // Setup
        final AppkeyTreeQueryRequest appkeyTreeQueryRequest = new AppkeyTreeQueryRequest();
        appkeyTreeQueryRequest.setPage(1);
        appkeyTreeQueryRequest.setPageSize(10);
        appkeyTreeQueryRequest.setUser("user");
        appkeyTreeQueryRequest.setType("type");

        when(appkeyTreeResource.getSrvsByQueryRequest(any(SrvQueryRequestBO.class)))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> service.getMineAppkeysByHttpClient(appkeyTreeQueryRequest))
                .isInstanceOf(SdkCallException.class);
        verify(appkeyTreeResource).getSrvsByQueryRequest(any(SrvQueryRequestBO.class));
    }

    @Test
    public void testGetUserRelatedAppkeysByHttpClientThatThrowsSdkBusinessErrorException() {
        // Setup
        final AppkeyTreeQueryRequest appkeyTreeQueryRequest = new AppkeyTreeQueryRequest();
        appkeyTreeQueryRequest.setPage(1);
        appkeyTreeQueryRequest.setPageSize(10);
        appkeyTreeQueryRequest.setUser("user");
        appkeyTreeQueryRequest.setType("type");

        when(appkeyTreeResource.getSrvsByQueryRequest(any(SrvQueryRequestBO.class)))
                .thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> service.getMineAppkeysByHttpClient(appkeyTreeQueryRequest))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(appkeyTreeResource).getSrvsByQueryRequest(any(SrvQueryRequestBO.class));
    }

    @Test
    public void getBySrv() {
        when(appkeyResource.getBySrv(Mockito.anyString())).thenReturn(appkeyBO);
        AppkeyDTO appkeyDTO = service.getBySrv("meituan.unit.test.appkey");
        Assert.assertEquals("meituan.unit.test.appkey", appkeyDTO.getSrv());
        verify(appkeyResource).getBySrv(Mockito.anyString());
    }

    @Test
    public void queryPage() {
        boPageResponse.setItems(Collections.singletonList(appkeyBO));
        when(appkeyResource.queryPage(Mockito.any(AppkeyRequestBO.class))).thenReturn(boPageResponse);
        PageResponse<AppkeyDTO> dtoPageResponse = service.queryPage(new AppkeyQueryPageRequest());
        Assert.assertEquals("meituan.unit.test.appkey", dtoPageResponse.getItems().get(0).getSrv());
        verify(appkeyResource).queryPage(Mockito.any(AppkeyRequestBO.class));
    }

    @Test
    public void searchPage() {
        when(appkeyResource.searchAppkey(Mockito.any(AppkeySearchRequestBO.class))).thenReturn(boPageResponse);
        PageResponse<AppkeyDTO> dtoPageResponse = service.searchPage(new AppkeySearchPageRequest());
        Assert.assertEquals("meituan.unit.test.appkey", dtoPageResponse.getItems().get(0).getSrv());
        verify(appkeyResource).searchAppkey(Mockito.any(AppkeySearchRequestBO.class));
    }

    @Test
    public void getByHost() {
        when(appkeyResource.getByHost(Mockito.anyString())).thenReturn("appkey");
        String appkey = service.getByHost("0.0.0.0");
        Assert.assertEquals("appkey", appkey);
        verify(appkeyResource).getByHost(Mockito.anyString());
    }

    @Test
    public void getMinePageAppkey() {
        AppkeyBO appkeyBO1 = JsonUtil.json2Bean(JsonUtil.bean2Json(AppkeyServiceTest.appkeyBO), AppkeyBO.class);
        appkeyBO1.setAppkey("appkey1");
        PageResponse<AppkeyBO> boPageResponse = new PageResponse<>();
        boPageResponse.setItems(Arrays.asList(appkeyBO, appkeyBO1));
        when(appkeyResource.getOwnAppkey(Mockito.any(AppkeySrvsQueryRequest.class))).thenReturn(boPageResponse);
        AppkeyTreeQueryRequest request = new AppkeyTreeQueryRequest();
        request.setUser("user");
        request.setQuery("app");
        PageResponse<AppkeyDTO> minePageAppkey = service.getMineAppkeysByRepository(request);
        Assert.assertEquals(2, minePageAppkey.getItems().size());
        Assert.assertEquals("appkey", minePageAppkey.getItems().get(0).getAppkey());
        verify(appkeyResource).getOwnAppkey(Mockito.any(AppkeySrvsQueryRequest.class));
    }

    @Test
    public void getFavorPageAppkey() {
        appkeyBO.setAppkey("appkey");
        when(appkeyResource.getFavorAppkey(Mockito.any(AppkeySrvsQueryRequest.class))).thenReturn(Collections.singletonList(appkeyBO));
        AppkeyTreeQueryRequest request = new AppkeyTreeQueryRequest();
        request.setUser("user");
        request.setQuery("app");
        PageResponse<AppkeyDTO> dtoPageResponse = service.getFavorPageAppkey(request);
        Assert.assertEquals("appkey", dtoPageResponse.getItems().get(0).getAppkey());
        verify(appkeyResource).getFavorAppkey(Mockito.any(AppkeySrvsQueryRequest.class));
    }

    @Test
    public void isUserFavorAppkey() {
        // 1、正常用户关注
        when(appkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");
        when(appkeyTreeResource.getSrvSubscribers("srv")).thenReturn(Collections.singletonList("user"));
        Boolean favor = service.isUserFavorAppkey("user", "appkey");
        Assert.assertTrue(favor);
        // 2、appkey无服务树节点情形
        when(appkeyResource.getSrvKeyByAppkey("appkey1")).thenReturn("");
        Boolean favor1 = service.isUserFavorAppkey("user", "appkey1");
        Assert.assertFalse(favor1);
        // 3、OPS接口异常
        when(appkeyResource.getSrvKeyByAppkey("appkey2")).thenReturn("srv2");
        when(appkeyTreeResource.getSrvSubscribers("srv2")).thenThrow(SdkCallException.class);
        Boolean favor2 = service.isUserFavorAppkey("user", "appkey2");
        Assert.assertFalse(favor2);
    }

    @Test
    public void getByVip() {
        when(appkeyResource.getByVip(Mockito.anyString())).thenReturn(Collections.singletonList("appkey"));
        List<String> appkeys = service.getByVip("0.0.0.0");
        Assert.assertEquals("appkey", appkeys.get(0));
        verify(appkeyResource).getByVip(Mockito.anyString());
    }

    @Test
    public void favorAppkey() {
        when(appkeyResource.favorAppkey(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Boolean favor = service.favorAppkey("appkey", "user");
        Assert.assertTrue(favor);
        verify(appkeyResource).favorAppkey(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void cancelFavorAppkey() {
        when(appkeyResource.cancelFavorAppkey(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Boolean cancel = service.cancelFavorAppkey("appkey", "user");
        Assert.assertTrue(cancel);
        verify(appkeyResource).cancelFavorAppkey(Mockito.anyString(), Mockito.anyString());
    }

    /**
     * 生成演练服务名称
     */
    @Test
    public void generateIsoltAppkeyName() {
        String originAppkey = "com.sankuai.avatar.develop.web";
        OpsSrvBO opsSrvBO = new OpsSrvBO();
        opsSrvBO.setSoamod("develop");
        opsSrvBO.setSoasrv("web");
        when(appkeyResource.getAppkeyRelatedSrvInfo(originAppkey)).thenReturn(opsSrvBO);

        ScAppkeyBO scAppkeyBO = new ScAppkeyBO();
        scAppkeyBO.setApplicationName("avatar");
        when(appkeyResource.batchGetAppkeyBySc(Collections.singletonList(originAppkey))).thenReturn(Collections.singletonList(scAppkeyBO));

        IsoltAppkeyGenerateDisplayDTO isoltAppkeyGenerateDisplayDTO = service.generateIsoltAppkeyName(originAppkey, "", "");
        Assert.assertEquals("com.sankuai.avatar.develop.webisolt", isoltAppkeyGenerateDisplayDTO.getIsoltAppkeyName());

        IsoltAppkeyGenerateDisplayDTO isoltAppkeyGenerateDisplayDTO2 = service.generateIsoltAppkeyName(originAppkey, "soamod", "soasrv");
        Assert.assertEquals("com.sankuai.avatar.soamod.soasrv", isoltAppkeyGenerateDisplayDTO2.getIsoltAppkeyName());

        // Test Throw SdkCallException
        when(appkeyResource.getAppkeyRelatedSrvInfo(originAppkey)).thenThrow(SdkCallException.class);
        assertThatThrownBy(() -> service.generateIsoltAppkeyName(originAppkey, "", "")).isInstanceOf(SdkCallException.class);
    }

    /**
     * 生成演练服务名称: appkey服务树信息缺少mod与srv
     */
    @Test
    public void generateIsoltAppkeyName_hasNoOpsTreeSrv() {
        // 格式1: com.sankuai.xxx.xxx.xxx
        String originAppkey = "com.sankuai.avatar.develop.web";
        OpsSrvBO opsSrvBO = new OpsSrvBO();
        opsSrvBO.setSoamod("");
        opsSrvBO.setSoasrv("");
        when(appkeyResource.getAppkeyRelatedSrvInfo(originAppkey)).thenReturn(opsSrvBO);

        ScAppkeyBO scAppkeyBO = new ScAppkeyBO();
        scAppkeyBO.setApplicationName("avatar");
        when(appkeyResource.batchGetAppkeyBySc(Collections.singletonList(originAppkey))).thenReturn(Collections.singletonList(scAppkeyBO));

        IsoltAppkeyGenerateDisplayDTO isoltAppkeyGenerateDisplayDTO = service.generateIsoltAppkeyName(originAppkey, "", "");
        Assert.assertEquals("com.sankuai.avatar.develop.webisolt", isoltAppkeyGenerateDisplayDTO.getIsoltAppkeyName());

        // 格式2: com.sankuai.xxx.xxx
        String originAppkey2 = "com.sankuai.avatar.develop";
        OpsSrvBO opsSrvBO2 = new OpsSrvBO();
        opsSrvBO.setSoamod("");
        opsSrvBO.setSoasrv("");
        when(appkeyResource.getAppkeyRelatedSrvInfo(originAppkey2)).thenReturn(opsSrvBO2);
        when(appkeyResource.batchGetAppkeyBySc(Collections.singletonList(originAppkey2))).thenReturn(Collections.singletonList(scAppkeyBO));
        IsoltAppkeyGenerateDisplayDTO isoltAppkeyGenerateDisplayDTO2 = service.generateIsoltAppkeyName(originAppkey2, "", "");
        Assert.assertEquals("com.sankuai.avatar.developisolt", isoltAppkeyGenerateDisplayDTO2.getIsoltAppkeyName());
    }

    @Test
    public void testGetIsoltAppkeys() {

        IsoltAppkeyPageRequest isoltAppkeyRequest = new IsoltAppkeyPageRequest();
        isoltAppkeyRequest.setPage(1);
        isoltAppkeyRequest.setPageSize(10);

        final PageResponse<ScIsoltAppkeyBO> scIsoltAppkeyPageResponse = new PageResponse<>();
        scIsoltAppkeyPageResponse.setItems(Arrays.asList(
                ScIsoltAppkeyBO.builder().appKey("appKey1").build(),
                ScIsoltAppkeyBO.builder().appKey("appKey2").build()
        ));
        when(appkeyResource.getIsoltAppkeys(Mockito.any(IsoltAppkeyRequestBO.class))).thenReturn(scIsoltAppkeyPageResponse);
        PageResponse<IsoltAppkeyDTO> pageResponse = service.getIsoltAppkeysByHttpClient(isoltAppkeyRequest);
        Assert.assertEquals(2, pageResponse.getItems().size());

        // Test Throw SdkCallException
        when(appkeyResource.getIsoltAppkeys(Mockito.any(IsoltAppkeyRequestBO.class))).thenThrow(SdkCallException.class);
        assertThatThrownBy(() -> service.getIsoltAppkeysByHttpClient(isoltAppkeyRequest)).isInstanceOf(SdkCallException.class);

        // Test Throw SdkBusinessErrorException
        when(appkeyResource.getIsoltAppkeys(Mockito.any(IsoltAppkeyRequestBO.class))).thenThrow(SdkBusinessErrorException.class);
        assertThatThrownBy(() -> service.getIsoltAppkeysByHttpClient(isoltAppkeyRequest)).isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetElasticTips() {
        ElasticTipBO elasticTipBO = new ElasticTipBO();
        elasticTipBO.setLink("link");

        when(appkeyResource.getElasticTips()).thenReturn(elasticTipBO);
        ElasticTipDTO result = service.getElasticTips();
        Assert.assertEquals(result.getLinkTip(), elasticTipBO.getLinkTip());
    }

    @Test
    public void testGetElasticGrayScale() {
        when(appkeyResource.getElasticGrayScale("meituan.avatar")).thenReturn(Boolean.TRUE);
        Boolean result = service.getElasticGrayScale("meituan.avatar");
        Assert.assertTrue(result);
    }

    @Test
    public void batchGetAppkeyRunningAndHoldingFlowList() {
        PageResponse<AppkeyFlowBO> appkeyFlowBOPageResponse = new PageResponse<>();
        AppkeyFlowBO appkeyFlowBO = new AppkeyFlowBO();
        appkeyFlowBO.setAppkey("appkey");
        appkeyFlowBOPageResponse.setItems(Collections.singletonList(appkeyFlowBO));

        when(appkeyResource.batchGetAppkeyRunningAndHoldingFlowList(Collections.singletonList("appkey"))).thenReturn(appkeyFlowBOPageResponse);
        PageResponse<AppkeyFlowDTO> result = service.batchGetAppkeyRunningAndHoldingFlowList(Collections.singletonList("appkey"));
        Assert.assertTrue(result.getItems().size() > 0);

        when(appkeyResource.batchGetAppkeyRunningAndHoldingFlowList(Collections.singletonList("appkeyError"))).thenReturn(new PageResponse<>());
        PageResponse<AppkeyFlowDTO> resultError = service.batchGetAppkeyRunningAndHoldingFlowList(Collections.singletonList("appkeyError"));
        Assert.assertTrue(CollectionUtils.isEmpty(resultError.getItems()));
    }

    @Test
    public void getUserTopAppkey() {
        when(relationResource.getUserTopAppkey("avatar")).thenReturn(Collections.singletonList("appkey"));
        List<String> result = service.getUserTopAppkey("avatar");
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void getGetAppkeyUnitList() {
        BillingUnitBO billingUnitBO = BillingUnitBO.builder().billingUnitId(31).build();
        when(appkeyResource.getAppkeyUnitList("appkey")).thenReturn(billingUnitBO);
        AppkeyBillingUnitDTO result = service.getAppkeyUnitList("appkey");
        Assert.assertEquals(31, (int) result.getBillingUnitId());
    }
}