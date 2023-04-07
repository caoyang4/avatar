package com.sankuai.avatar.resource.host;

import com.sankuai.avatar.client.dayu.DayuHttpClient;
import com.sankuai.avatar.client.dayu.model.DayuGroupTag;
import com.sankuai.avatar.client.dayu.model.GroupTagQueryRequest;
import com.sankuai.avatar.client.ecs.EcsHttpClient;
import com.sankuai.avatar.client.ecs.model.EcsIdc;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.kapiserver.KApiServerHttpClient;
import com.sankuai.avatar.client.kapiserver.model.HostFeature;
import com.sankuai.avatar.client.kapiserver.model.VmHostDiskFeature;
import com.sankuai.avatar.client.kapiserver.request.VmHostDiskQueryRequest;
import com.sankuai.avatar.client.nodemanager.NodeManagerHttpClient;
import com.sankuai.avatar.client.ops.OpsHttpClient;
import com.sankuai.avatar.client.ops.model.OpsHost;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.host.bo.*;
import com.sankuai.avatar.resource.host.impl.HostResourceImpl;
import com.sankuai.avatar.resource.host.request.GroupTagQueryRequestBO;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import com.sankuai.avatar.resource.host.request.VmHostDiskQueryRequestBO;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class HostResourceImplTest extends TestBase {

    @Mock
    private OpsHttpClient mockOpsHttpClient;
    @Mock
    private EcsHttpClient mockEcsHttpClient;
    @Mock
    private CacheClient mockCacheClient;
    @Mock
    private NodeManagerHttpClient mockNodeManagerHttpClient;
    @Mock
    private KApiServerHttpClient mockKApiServerHttpClient;
    @Mock
    private DayuHttpClient mockDayuHttpClient;

    private HostResourceImpl hostResource;

    @Before
    public void setUp() {
        hostResource = new HostResourceImpl(mockOpsHttpClient, mockEcsHttpClient, mockCacheClient,
                mockNodeManagerHttpClient, mockKApiServerHttpClient, mockDayuHttpClient);
        ReflectionTestUtils.setField(hostResource, "hostKindMap", "{\n" +
                "  \"server\": {\n" +
                "    \"cloud\": \"私有云物理机\",\n" +
                "    \"tx\": \"腾讯云黑石物理机\",\n" +
                "    \"maoyan_tx\": \"猫眼腾讯云物理机\"\n" +
                "  },\n" +
                "  \"vserver\": {\n" +
                "    \"cloud\": \"私有云虚拟机\",\n" +
                "    \"tx\": \"腾讯云虚拟机\",\n" +
                "    \"mos\": \"公有云虚拟机\",\n" +
                "    \"maoyan_tx\": \"猫眼腾讯云虚拟机\",\n" +
                "    \"hulk-kvm\": \"HULK虚拟机\"\n" +
                "  },\n" +
                "  \"hulk\": {\n" +
                "    \"hulk1.0\": \"HULK1.0\",\n" +
                "    \"hulk2.0\": \"HULK\",\n" +
                "    \"kata\": \"HULK安全容器\",\n" +
                "    \"tx\": \"HULK\"\n" +
                "  }\n" +
                "}");
        ReflectionTestUtils.setField(hostResource, "hostSourceMap", "{\"policy\":\"弹性伸缩\"}");
        ReflectionTestUtils.setField(hostResource, "originReturnResourcePool", "{\"通用资源池\": \"结算单元池\"}");
        ReflectionTestUtils.setField(hostResource, "winTag", new String[]{"win", "yf-pay-fundstransfer-gw-fe-cmb-clone_20160603"});
    }

    private static final String ecsIdc = "[{\n" +
            "    \"city\": \"上海\",\n" +
            "    \"idc_name\": \"桂桥\",\n" +
            "    \"idc\": \"gq\",\n" +
            "    \"region\": \"shanghai\",\n" +
            "    \"desc\": \"\"\n" +
            "}]";

    private static final OpsHost opsHost = new OpsHost();

    private static final HostAttributesBO hostAttributesBO;

    private final static HostQueryRequestBO hostQueryRequestBO = new HostQueryRequestBO(
            "env", "appkey", "srv", "query",
            "batchQueryType", "set", "cell", "swimlane",
            "grouptags", "netType", "managePlat", "idcName",
            "kindName", "originType", "rsNetType", "sortBy", "sort",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            false, false, false, false, false);

    private final static HostQueryRequestBO simpleHostQueryRequestBO = HostQueryRequestBO.builder()
            .appkey("appkey")
            .srv("srv")
            .env("env")
            .build();

    static {
        opsHost.setComment("comment");
        opsHost.setIpLan("ipLan");
        opsHost.setKernel("kernel");
        opsHost.setEnv("env");
        opsHost.setSshPort(0);
        opsHost.setCpuCount(0);
        opsHost.setCluster("cluster");
        opsHost.setBandwidth(0);
        opsHost.setHyperThreading(false);
        opsHost.setMtime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        opsHost.setCtime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        opsHost.setQcloudId("qcloudId");
        opsHost.setGrouptags("grouptags");
        opsHost.setId(0);
        opsHost.setGpuCount(0);
        opsHost.setUptime(0);
        opsHost.setUuid("eeef23b1-a3b0-4f86-91c5-be1fa2e68b3d");

        hostAttributesBO = new HostAttributesBO(
                "kindName", "idcName", "cityName",
                "nicSpeedName", "managePlatName", false,
                Collections.singletonList("value"), Collections.singletonList("value"),
                "netType", "originType", "returnType",
                "originGroup", "comment", "ipLan", "kernel",
                0, 0, "cluster", 0, false,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                "qcloudId", "grouptags", 0, 0, 0,
                "8f22fd12-1d75-4692-949a-d7bd584af7b2", 0, "swimlane",
                "ipLanV6", false, "cell", "env",
                "rackInfo", "ipWan", "type", "sn", "status",
                "maintainEnd", false, "vendor", "iloMac", "parent",
                "brand", "cpuModel", 0, "corp", "managePlat",
                "name", "kind", 0,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                "maintainStart", "buyTime",
                "mirrorId", "fqdn", "idc", "iloIp", "flavorName",
                "model", 0, "os", "region",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    }

    @Test
    public void testGetHostsBySrv() {
        final List<HostBO> expectedResult = Collections.singletonList(HostBO.builder().ipLan("ipLan").build());
        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);
        final List<HostBO> result = hostResource.getHostsBySrv("srv");
        Assertions.assertThat(result.get(0).getIpLan()).isEqualTo(expectedResult.get(0).getIpLan());
    }

    @Test
    public void testGetHostInfo() {
        HostBO hostBO = HostBO.builder().ipLan("ipLan").build();
        when(mockOpsHttpClient.getHostInfo("10.10.10.10")).thenReturn(opsHost);
        final HostBO result = hostResource.getHostInfo("10.10.10.10");
        Assertions.assertThat(result.getIpLan()).isEqualTo(hostBO.getIpLan());
    }

    @Test
    public void testGetHostsBySrv_OpsHttpClientReturnsNoItems() {
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(Collections.emptyList());
        final List<HostBO> result = hostResource.getHostsBySrv("srv");
        Assertions.assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetHostsBySrv_OpsHttpClientThrowsSdkCallException() {
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenThrow(SdkCallException.class);
        Assertions.assertThatThrownBy(() -> hostResource.getHostsBySrv("srv")).isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetHostsBySrv_OpsHttpClientThrowsSdkBusinessErrorException() {
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenThrow(SdkBusinessErrorException.class);
        Assertions.assertThatThrownBy(() -> hostResource.getHostsBySrv("srv"))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetDayuGrouptags() {
        GroupTagQueryRequest groupTagQueryRequest = GroupTagQueryRequest.builder().owt("owt").build();
        DayuGroupTag dayuGroupTag = new DayuGroupTag();
        dayuGroupTag.setGroupTagsName("GroupTag");

        when(mockDayuHttpClient.getGrouptags(groupTagQueryRequest)).thenReturn(Collections.singletonList(dayuGroupTag));

        GroupTagQueryRequestBO request = GroupTagQueryRequestBO.builder().owt("owt").build();
        final List<DayuGroupTagBO> result = hostResource.getGrouptags(request);
        Assert.assertEquals("GroupTag", result.get(0).getGroupTagsName());
    }

    @Test
    public void testGetHostsByAppKey() {
        final List<HostBO> expectedResult = Collections.singletonList(HostBO.builder().ipLan("ipLan").build());
        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsByAppkey("appKey")).thenReturn(opsHosts);
        final List<HostBO> result = hostResource.getHostsByAppKey("appKey");
        assertThat(result.get(0).getIpLan()).isEqualTo(expectedResult.get(0).getIpLan());
    }

    @Test
    public void testGetHostsByAppKey_OpsHttpClientReturnsNoItems() {
        when(mockOpsHttpClient.getHostsByAppkey("appKey")).thenReturn(Collections.emptyList());
        final List<HostBO> result = hostResource.getHostsByAppKey("appKey");
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetHostsByAppKey_OpsHttpClientThrowsSdkCallException() {
        when(mockOpsHttpClient.getHostsByAppkey("appKey")).thenThrow(SdkCallException.class);
        assertThatThrownBy(() -> hostResource.getHostsByAppKey("appKey"))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetHostsByAppKey_OpsHttpClientThrowsSdkBusinessErrorException() {
        when(mockOpsHttpClient.getHostsByAppkey("appKey")).thenThrow(SdkBusinessErrorException.class);
        assertThatThrownBy(() -> hostResource.getHostsByAppKey("appKey"))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetHostInfo_OpsHttpClientThrowsSdkCallException() {
        when(mockOpsHttpClient.getHostInfo("name")).thenThrow(SdkCallException.class);
        assertThatThrownBy(() -> hostResource.getHostInfo("name")).isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetHostInfo_OpsHttpClientThrowsSdkBusinessErrorException() {
        when(mockOpsHttpClient.getHostInfo("name")).thenThrow(SdkBusinessErrorException.class);
        assertThatThrownBy(() -> hostResource.getHostInfo("name"))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetSrvHostsByQueryRequest() {
        // Setup

        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn(ecsIdc);
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(
                        new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200))
                .thenReturn(false);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());

        // Run the test
        final List<HostAttributesBO> resultWithFilter = hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO);

        // Verify the results
        assertThat(resultWithFilter).isNotNull();
    }

    @Test
    public void testGetSrvHostsByQueryRequest_OpsHttpClientReturnsNoItems() {
        // Setup
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(Collections.emptyList());

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn(ecsIdc);
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(
                        new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200))
                .thenReturn(false);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());

        // Run the test
        final List<HostAttributesBO> result = hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetSrvHostsByQueryRequest_OpsHttpClientThrowsSdkCallException() {
        // Setup
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getSrvHostsByQueryRequest(hostQueryRequestBO))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetSrvHostsByQueryRequest_OpsHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getSrvHostsByQueryRequest(hostQueryRequestBO))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    /**
     * 测试: 获取ECS机房缓存信息抛出缓存异常
     * 不影响主机查询
     */
    @Test
    public void testGetSrvHostsByQueryRequest_CacheClientGetThrowsCacheException() {

        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);
        // Throw CacheException
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenThrow(CacheException.class);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());

        // Run the test
        final List<HostAttributesBO> result = hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testGetSrvHostsByQueryRequest_EcsHttpClientReturnsNoItems() {
        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);
        // ECS机房缓存为空
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        // ECS机房为空
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.emptyList());
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200)).thenReturn(false);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());

        // Run the test
        final List<HostAttributesBO> result = hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testGetSrvHostsByQueryRequest_EcsHttpClientThrowsSdkCallException() {
        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        // ECS 抛出SdkCallException
        when(mockEcsHttpClient.getIdcList()).thenThrow(SdkCallException.class);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());

        // Run the test
        final List<HostAttributesBO> result = hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testGetSrvHostsByQueryRequest_EcsHttpClientThrowsSdkBusinessErrorException() {
        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");

        // ECS 抛出SdkBusinessErrorException
        when(mockEcsHttpClient.getIdcList()).thenThrow(SdkBusinessErrorException.class);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());

        // Run the test
        final List<HostAttributesBO> result = hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testGetSrvHostsByQueryRequest_CacheClientSetThrowsCacheException() {
        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        // 缓存异常
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200))
                .thenThrow(CacheException.class);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());

        // Run the test
        final List<HostAttributesBO> result = hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testGetSrvHostsByQueryRequest_KApiServerHttpClientThrowsSdkCallException() {
        opsHost.setKind("hulk");
        simpleHostQueryRequestBO.setOriginType("originType");
        simpleHostQueryRequestBO.setEnv("prod");

        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn(ecsIdc);
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200))
                .thenReturn(false);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(any(List.class), any(EnvEnum.class)))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetSrvHostsByQueryRequest_KApiServerHttpClientThrowsSdkBusinessErrorException() {
        opsHost.setKind("hulk");
        simpleHostQueryRequestBO.setOriginType("originType");
        simpleHostQueryRequestBO.setEnv("prod");

        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn(ecsIdc);
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200))
                .thenReturn(false);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(any(List.class), any(EnvEnum.class)))
                .thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetSrvHostsByQueryRequest_NodeManagerHttpClientThrowsSdkCallException() {
        opsHost.setKind("hulk");
        simpleHostQueryRequestBO.setOriginType("originType");
        simpleHostQueryRequestBO.setEnv("prod");

        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn(ecsIdc);
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200))
                .thenReturn(false);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Arrays.asList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(any(List.class), any(EnvEnum.class)))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetSrvHostsByQueryRequest_NodeManagerHttpClientThrowsSdkBusinessErrorException() {
        opsHost.setKind("hulk");
        simpleHostQueryRequestBO.setOriginType("originType");
        simpleHostQueryRequestBO.setEnv("prod");

        final List<OpsHost> opsHosts = Collections.singletonList(opsHost);
        when(mockOpsHttpClient.getHostsBySrv("srv")).thenReturn(opsHosts);

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn(ecsIdc);
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200))
                .thenReturn(false);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Arrays.asList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(any(List.class), any(EnvEnum.class)))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getSrvHostsByQueryRequest(simpleHostQueryRequestBO))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testPatchHostTagsAndFeatures() {
        hostAttributesBO.setKind("hulk");
        final List<HostAttributesBO> opsHosts = Collections.singletonList(hostAttributesBO);
        HostFeature hostFeature = new HostFeature();
        hostFeature.setFeatures(Collections.singletonList("value"));
        hostFeature.setOriginType("OriginType");
        hostFeature.setOriginGroup("OriginGroup");
        Map<String, HostFeature> hostFeatureMap = new HashMap();
        hostFeatureMap.put("name", hostFeature);

        Map<String, List<String>> hostsParentFeatureMap = new HashMap<>();
        hostsParentFeatureMap.put("parent", Collections.singletonList("ParentFeature"));


        when(mockKApiServerHttpClient.getHulkHostsFeatures(any(List.class), any(EnvEnum.class)))
                .thenReturn(hostFeatureMap);
        when(mockNodeManagerHttpClient.getHostsParentFeatures(any(List.class), any(EnvEnum.class)))
                .thenReturn(hostsParentFeatureMap);

        // Run the test
        final List<HostAttributesBO> result = hostResource.patchHostTagsAndFeatures(opsHosts, "prod");

        // Verify the results
        assertThat(result).hasSize(1);
        for (HostAttributesBO host: result) {
            assertThat(host.getHostTags()).contains("value");
            assertThat(host.getOriginType()).isEqualTo("OriginType");
            assertThat(host.getOriginGroup()).isEqualTo("-");
            assertThat(host.getParentTags()).isEqualTo(Collections.singletonList("ParentFeature"));
        }
    }

    @Test
    public void testPatchHostTagsAndFeatures_KApiServerHttpClientThrowsSdkCallException() {
        // Setup
        hostAttributesBO.setKind("hulk");
        final List<HostAttributesBO> opsHosts = Collections.singletonList(hostAttributesBO);

        when(mockKApiServerHttpClient.getHulkHostsFeatures(any(List.class), any(EnvEnum.class)))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.patchHostTagsAndFeatures(opsHosts, "prod"))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testPatchHostTagsAndFeatures_KApiServerHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        hostAttributesBO.setKind("hulk");
        final List<HostAttributesBO> opsHosts = Collections.singletonList(hostAttributesBO);

        when(mockKApiServerHttpClient.getHulkHostsFeatures(any(List.class), any(EnvEnum.class)))
                .thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.patchHostTagsAndFeatures(opsHosts, "prod"))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testPatchHostTagsAndFeatures_NodeManagerHttpClientThrowsSdkCallException() {
        // Setup
        hostAttributesBO.setKind("hulk");
        final List<HostAttributesBO> opsHosts = Collections.singletonList(hostAttributesBO);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(any(List.class), any(EnvEnum.class)))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(any(List.class), any(EnvEnum.class)))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.patchHostTagsAndFeatures(opsHosts, "prod"))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testPatchHostTagsAndFeatures_NodeManagerHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        hostAttributesBO.setKind("hulk");
        final List<HostAttributesBO> opsHosts = Collections.singletonList(hostAttributesBO);
        when(mockKApiServerHttpClient.getHulkHostsFeatures(any(List.class), any(EnvEnum.class)))
                .thenReturn(new HashMap<>());
        when(mockNodeManagerHttpClient.getHostsParentFeatures(any(List.class), any(EnvEnum.class)))
                .thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.patchHostTagsAndFeatures(opsHosts, "prod"))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testPatchVmHostDiskType() {
        // Setup
        hostAttributesBO.setKind("hulk");
        hostAttributesBO.setKindName("HULK虚拟机");

        final List<HostAttributesBO> opsHosts = Collections.singletonList(hostAttributesBO);
        final List<VmHostDiskFeature> vmHostDiskFeatures = Collections.singletonList(
                VmHostDiskFeature.builder().ip("ipLan").storageType("lvm").build()
        );
        when(mockKApiServerHttpClient.getVmHostsDiskFeatures(
                VmHostDiskQueryRequest.builder().appkey("appkey").env("env").ips(Collections.singletonList("ipLan"))
                        .build()))
                .thenReturn(vmHostDiskFeatures);

        // Run the test
        final List<HostAttributesBO> result = hostResource.patchVmHostDiskType(opsHosts, "appkey", "env");

        // Verify the results
        assertThat(result.get(0).getHostTags()).contains("lvm磁盘");
    }

    @Test
    public void testPatchVmHostDiskType_KApiServerHttpClientReturnsNoItems() {
        hostAttributesBO.setKindName("HULK虚拟机");

        final List<HostAttributesBO> opsHosts = Collections.singletonList(hostAttributesBO);
        when(mockKApiServerHttpClient.getVmHostsDiskFeatures(
                VmHostDiskQueryRequest.builder().appkey("appkey").env("env").ips(Collections.singletonList("ipLan"))
                        .build()))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<HostAttributesBO> result = hostResource.patchVmHostDiskType(opsHosts, "appkey", "env");

        // Verify the results
        assertThat(result.get(0).getHostTags()).isNotNull();
    }

    @Test
    public void testPatchVmHostDiskType_KApiServerHttpClientThrowsSdkCallException() {
        // Setup
        hostAttributesBO.setKindName("HULK虚拟机");

        final List<HostAttributesBO> opsHosts = Collections.singletonList(hostAttributesBO);
        when(mockKApiServerHttpClient.getVmHostsDiskFeatures(any(VmHostDiskQueryRequest.class)))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.patchVmHostDiskType(opsHosts, "appkey", "env"))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testPatchVmHostDiskType_KApiServerHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        hostAttributesBO.setKindName("HULK虚拟机");

        final List<HostAttributesBO> opsHosts = Collections.singletonList(hostAttributesBO);
        when(mockKApiServerHttpClient.getVmHostsDiskFeatures(any(VmHostDiskQueryRequest.class)))
                .thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.patchVmHostDiskType(opsHosts, "appkey", "env"))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetIdcList() {
        // Setup

        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn(ecsIdc);
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200)).thenReturn(false);

        // Run the test
        final List<IdcMetaDataBO> result = hostResource.getIdcList();

        // Verify the results
        assertThat(result).isNotEmpty();
    }

    @Test
    public void testGetIdcList_CacheClientGetThrowsCacheException() {
        // Setup
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenThrow(CacheException.class);

        // Run the test
        final List<IdcMetaDataBO> result = hostResource.getIdcList();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetIdcList_EcsHttpClientReturnsNoItems() {
        // Setup
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.emptyList());
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200)).thenReturn(false);

        // Run the test
        final List<IdcMetaDataBO> result = hostResource.getIdcList();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetIdcList_EcsHttpClientThrowsSdkCallException() {
        // Setup
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenThrow(SdkCallException.class);

        // Run the test
        final List<IdcMetaDataBO> result = hostResource.getIdcList();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetIdcList_EcsHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        final List<IdcMetaDataBO> result = hostResource.getIdcList();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetIdcList_CacheClientSetThrowsCacheException() {
        // Setup
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        when(mockCacheClient.set(any(String.class), any(String.class),
                any(List.class), any(Integer.class)))
                .thenThrow(CacheException.class);

        // Run the test
        final List<IdcMetaDataBO> result = hostResource.getIdcList();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetIdcMap() {
        // Setup
        EcsIdc idc = EcsIdc.builder().idc("idc").idcName("idcName").city("city").build();
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(idc));
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200)).thenReturn(false);

        // Run the test
        final Map<String, String> result = hostResource.getIdcMap();

        // Verify the results
        assertThat(result).isNotEmpty();

    }

    @Test
    public void testGetIdcMap_CacheClientGetThrowsCacheException() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenThrow(CacheException.class);

        // Run the test
        final Map<String, String> result = hostResource.getIdcMap();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetIdcMap_EcsHttpClientReturnsNoItems() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.emptyList());

        // Run the test
        final Map<String, String> result = hostResource.getIdcMap();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetIdcMap_EcsHttpClientThrowsSdkCallException() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenThrow(SdkCallException.class);

        // Run the test
        final Map<String, String> result = hostResource.getIdcMap();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetIdcMap_EcsHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        final Map<String, String> result = hostResource.getIdcMap();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetIdcMap_CacheClientSetThrowsCacheException() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();
        when(mockCacheClient.get("avatar-web", "ecsIdcMetaData")).thenReturn("");
        when(mockEcsHttpClient.getIdcList()).thenReturn(Collections.singletonList(EcsIdc.builder().build()));
        when(mockCacheClient.set("avatar-web", "ecsIdcMetaData",
                Collections.singletonList(new IdcMetaDataBO("city", "idcName", "region", "idc", "desc")), 43200))
                .thenThrow(CacheException.class);

        // Run the test
        final Map<String, String> result = hostResource.getIdcMap();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetHulkHostsFeatures() {
        // Setup
        final Map<String, HostFeatureBO> expectedResult = new HashMap<>();
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());

        // Run the test
        final Map<String, HostFeatureBO> result = hostResource.getHulkHostsFeatures(Collections.singletonList("value"),
                EnvEnum.PROD);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetHulkHostsFeatures_KApiServerHttpClientThrowsSdkCallException() {
        // Setup
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getHulkHostsFeatures(Collections.singletonList("value"),
                EnvEnum.PROD)).isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetHulkHostsFeatures_KApiServerHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        when(mockKApiServerHttpClient.getHulkHostsFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getHulkHostsFeatures(Collections.singletonList("value"),
                EnvEnum.PROD)).isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetVmHostsDiskFeatures() {
        // Setup
        final VmHostDiskQueryRequestBO request = VmHostDiskQueryRequestBO.builder()
                .appkey("appkey")
                .env("env")
                .ips(Collections.singletonList("value"))
                .build();
        final VmHostDiskQueryRequest vmHostDiskQueryRequest = VmHostDiskQueryRequest.builder()
                .appkey("appkey")
                .env("env")
                .ips(Collections.singletonList("value"))
                .build();
        final List<VmHostFeatureBO> expectedResult = Collections.singletonList(VmHostFeatureBO.builder()
                .ip("ip")
                .storageType("storageType")
                .build());

        // Configure KApiServerHttpClient.getVmHostsDiskFeatures(...).
        final List<VmHostDiskFeature> vmHostDiskFeatures = Collections.singletonList(VmHostDiskFeature.builder().ip("ip")
                .storageType("storageType").build());
        when(mockKApiServerHttpClient.getVmHostsDiskFeatures(vmHostDiskQueryRequest))
                .thenReturn(vmHostDiskFeatures);

        // Run the test
        final List<VmHostFeatureBO> result = hostResource.getVmHostsDiskFeatures(request);

        // Verify the results
        assertThat(result.get(0).getStorageType()).isEqualTo(expectedResult.get(0).getStorageType());
    }

    @Test
    public void testGetVmHostsDiskFeatures_KApiServerHttpClientReturnsNoItems() {
        // Setup
        final VmHostDiskQueryRequestBO request = VmHostDiskQueryRequestBO.builder()
                .appkey("appkey")
                .env("env")
                .ips(Collections.singletonList("value"))
                .build();
        when(mockKApiServerHttpClient.getVmHostsDiskFeatures(any(VmHostDiskQueryRequest.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<VmHostFeatureBO> result = hostResource.getVmHostsDiskFeatures(request);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetVmHostsDiskFeatures_KApiServerHttpClientThrowsSdkCallException() {
        // Setup
        final VmHostDiskQueryRequestBO request = VmHostDiskQueryRequestBO.builder()
                .appkey("appkey")
                .env("env")
                .ips(Collections.singletonList("value"))
                .build();
        when(mockKApiServerHttpClient.getVmHostsDiskFeatures(any(VmHostDiskQueryRequest.class)))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getVmHostsDiskFeatures(request))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetVmHostsDiskFeatures_KApiServerHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        final VmHostDiskQueryRequestBO request = VmHostDiskQueryRequestBO.builder()
                .appkey("appkey")
                .env("env")
                .ips(Collections.singletonList("value"))
                .build();
        when(mockKApiServerHttpClient.getVmHostsDiskFeatures(any(VmHostDiskQueryRequest.class)))
                .thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getVmHostsDiskFeatures(request))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetHostsParentFeatures() {
        // Setup
        final Map<String, List<String>> expectedResult = new HashMap<>();
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenReturn(new HashMap<>());

        // Run the test
        final Map<String, List<String>> result = hostResource.getHostsParentFeatures(
                Collections.singletonList("value"), EnvEnum.PROD);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetHostsParentFeatures_NodeManagerHttpClientThrowsSdkCallException() {
        // Setup
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getHostsParentFeatures(Collections.singletonList("value"),
                EnvEnum.PROD)).isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetHostsParentFeatures_NodeManagerHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        when(mockNodeManagerHttpClient.getHostsParentFeatures(Collections.singletonList("value"), EnvEnum.PROD))
                .thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getHostsParentFeatures(Collections.singletonList("value"),
                EnvEnum.PROD)).isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetGrouptags() {
        // Setup
        final GroupTagQueryRequestBO request = new GroupTagQueryRequestBO(0, 0, "keyword", "owt", false);
        final DayuGroupTagBO dayuGroupTagBO = new DayuGroupTagBO();
        dayuGroupTagBO.setId(0);
        dayuGroupTagBO.setGroupTagsName("groupTagsName");
        dayuGroupTagBO.setGroupTagsAlias("groupTagsAlias");
        dayuGroupTagBO.setGroupTags("groupTags");
        dayuGroupTagBO.setOwt("owt");
        dayuGroupTagBO.setHidden(false);
        dayuGroupTagBO.setDescription("description");
        dayuGroupTagBO.setCreator("creator");
        dayuGroupTagBO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        dayuGroupTagBO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<DayuGroupTagBO> expectedResult = Collections.singletonList(dayuGroupTagBO);

        // Configure DayuHttpClient.getGrouptags(...).
        final DayuGroupTag dayuGroupTag = new DayuGroupTag();
        dayuGroupTag.setId(0);
        dayuGroupTag.setGroupTagsName("groupTagsName");
        dayuGroupTag.setGroupTagsAlias("groupTagsAlias");
        dayuGroupTag.setGroupTags("groupTags");
        dayuGroupTag.setOwt("owt");
        dayuGroupTag.setHidden(false);
        dayuGroupTag.setDescription("description");
        dayuGroupTag.setCreator("creator");
        dayuGroupTag.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        dayuGroupTag.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<DayuGroupTag> dayuGroupTags = Collections.singletonList(dayuGroupTag);
        when(mockDayuHttpClient.getGrouptags(new GroupTagQueryRequest(0, 0, "keyword", "owt", false)))
                .thenReturn(dayuGroupTags);

        // Run the test
        final List<DayuGroupTagBO> result = hostResource.getGrouptags(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetGrouptags_DayuHttpClientReturnsNoItems() {
        // Setup
        final GroupTagQueryRequestBO request = new GroupTagQueryRequestBO(0, 0, "keyword", "owt", false);
        when(mockDayuHttpClient.getGrouptags(new GroupTagQueryRequest(0, 0, "keyword", "owt", false)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<DayuGroupTagBO> result = hostResource.getGrouptags(request);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testGetGrouptags_DayuHttpClientThrowsSdkCallException() {
        // Setup
        final GroupTagQueryRequestBO request = new GroupTagQueryRequestBO(0, 0, "keyword", "owt", false);
        when(mockDayuHttpClient.getGrouptags(new GroupTagQueryRequest(0, 0, "keyword", "owt", false)))
                .thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getGrouptags(request)).isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetGrouptags_DayuHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        final GroupTagQueryRequestBO request = new GroupTagQueryRequestBO(0, 0, "keyword", "owt", false);
        when(mockDayuHttpClient.getGrouptags(new GroupTagQueryRequest(0, 0, "keyword", "owt", false)))
                .thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> hostResource.getGrouptags(request))
                .isInstanceOf(SdkBusinessErrorException.class);
    }
}
