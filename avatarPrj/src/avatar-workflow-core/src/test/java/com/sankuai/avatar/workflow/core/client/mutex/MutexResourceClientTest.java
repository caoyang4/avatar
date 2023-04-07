package com.sankuai.avatar.workflow.core.client.mutex;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.AppkeyBO;
import com.sankuai.avatar.resource.host.HostResource;
import com.sankuai.avatar.resource.host.bo.HostBO;
import com.sankuai.avatar.workflow.core.client.mutex.impl.MutexResourceClientImpl;
import com.sankuai.avatar.workflow.core.client.mutex.request.MutexResourceRequest;
import com.sankuai.avatar.workflow.core.client.mutex.response.MutexResourceResponse;
import com.sankuai.avatar.workflow.core.context.FlowConfig;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.context.FlowUserSource;
import com.sankuai.avatar.workflow.core.context.config.FlowMutexResource;
import com.sankuai.avatar.workflow.core.context.config.FlowSwitch;
import com.sankuai.avatar.workflow.core.context.resource.Host;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import com.sankuai.avatar.workflow.core.input.host.HostsRebootInput;
import com.sankuai.avatar.workflow.core.input.mgw.MgwRsAddInput;
import com.sankuai.avatar.workflow.core.input.mgw.Vs;
import com.sankuai.avatar.dao.workflow.repository.FlowMutexResourceRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MutexResourceClientTest {

    private static final String TEST_APP_KEY = "com.sankuai.avatar.workflow.server";

    /**
     * 资源缓存字段拼接符
     */
    private static final String RESOURCE_CACHE_FIELD_DELIMITER = "_";

    /**
     * 资源缓存值拼接符
     */
    private static final String RESOURCE_CACHE_DATA_DELIMITER = "@";

    /**
     * 互斥资源 jsonPath 分隔符
     */
    private static final String MUTEX_FIELD_DELIMITER = ";";

    @Mock
    private FlowMutexResourceRepository flowMutexResourceRepository;

    @Mock
    private AppkeyResource appKeyResource;

    @Mock
    private HostResource hostResource;

    private MutexResourceClient mutexResourceClient;

    @Before
    public void setUp() {
        mutexResourceClient = new MutexResourceClientImpl(flowMutexResourceRepository, appKeyResource, hostResource);
    }

    @Test
    public void testValidateIPMutexResourceIsUnLock() {
        // Setup
        FlowContext flowContext = buildHostsRebootFlowContext();
        MutexResourceRequest request = MutexResourceRequest.builder().flowContext(flowContext).build();

        final MutexResourceResponse expectedResult = MutexResourceResponse.builder().locked(false).build();
        String appKey = flowContext.getResource().getAppkey();
        List<HostBO> hostBOList = Collections.singletonList(HostBO.builder()
                .ipLan("10.43.84.164")
                .env("test")
                .build());
        List<Host> hosts = flowContext.getResource().getHostList();
        for (Host host : hosts) {
            when(flowMutexResourceRepository.isExist(host.getIpLan())).thenReturn(false);
            when(hostResource.getHostsByAppKey(appKey)).thenReturn(hostBOList);
        }

        // Run the test
        MutexResourceResponse result = mutexResourceClient.validate(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

    }

    @Test
    public void testValidateIPMutexResourceIsLocked() {
        // Setup
        FlowContext flowContext = buildHostsRebootFlowContext();
        MutexResourceRequest request = MutexResourceRequest.builder().flowContext(flowContext).build();

        final MutexResourceResponse expectedResult = MutexResourceResponse.builder()
                .locked(true)
                .message("10.43.84.164正在重启中")
                .build();

        String appKey = flowContext.getResource().getAppkey();
        List<HostBO> hostBOList = Collections.singletonList(HostBO.builder()
                .ipLan("10.43.84.164")
                .env("test")
                .build());
        List<Host> hosts = flowContext.getResource().getHostList();
        for (Host host : hosts) {
            when(flowMutexResourceRepository.isExist(host.getIpLan())).thenReturn(true);
            when(flowMutexResourceRepository.getResourceByField(host.getIpLan()))
                    .thenReturn("hosts_reboot@8d1a3c0-dbb3-43b3-929b-e5d663f1e694");
            when(hostResource.getHostsByAppKey(appKey)).thenReturn(hostBOList);

        }

        // Run the test
        MutexResourceResponse result = mutexResourceClient.validate(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

    }

    @Test
    public void testValidateCommonMutexResourceIsUnLock() {
        // Setup
        FlowContext flowContext = buildMgwRsAddFlowContext();
        MutexResourceRequest request = MutexResourceRequest.builder().flowContext(flowContext).build();

        final MutexResourceResponse expectedResult = MutexResourceResponse.builder().locked(false).build();
        List<String> mutexResourceCacheKeyList = getMutexResourceCacheKey(flowContext);
        for (String cacheKey : mutexResourceCacheKeyList) {
            when(flowMutexResourceRepository.isExist(cacheKey)).thenReturn(false);
        }
        ReflectionTestUtils.setField(mutexResourceClient, "avatarUrl", "https://mbop.oversea.test.sankuai.com");

        // Run the test
        MutexResourceResponse result = mutexResourceClient.validate(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testValidateCommonMutexResourceLocked() {
        // Setup
        FlowContext flowContext = buildMgwRsAddFlowContext();
        MutexResourceRequest request = MutexResourceRequest.builder().flowContext(flowContext).build();

        final MutexResourceResponse expectedResult = MutexResourceResponse.builder()
                .locked(true)
                .message("资源10.48.27.169_8081_TCP被流程https://mbop.oversea.test.sankuai.com/#/home?flow_id=8d1a3c0-dbb3-43b3-929b-e5d663f1e694锁定中,请查看流程链接并联系流程发起人处理。")
                .build();
        List<String> mutexResourceCacheKeyList = getMutexResourceCacheKey(flowContext);
        for (String cacheKey : mutexResourceCacheKeyList) {
            when(flowMutexResourceRepository.isExist(cacheKey)).thenReturn(true);
            when(flowMutexResourceRepository.getResourceByField(cacheKey))
                    .thenReturn("mgw_rs_add@8d1a3c0-dbb3-43b3-929b-e5d663f1e694");
        }
        ReflectionTestUtils.setField(mutexResourceClient, "avatarUrl", "https://mbop.oversea.test.sankuai.com");

        // Run the test
        MutexResourceResponse result = mutexResourceClient.validate(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testLockIPResource() {
        // Setup
        FlowContext flowContext = buildHostsRebootFlowContext();
        MutexResourceRequest request = MutexResourceRequest.builder().flowContext(flowContext).build();

        List<Host> hosts = flowContext.getResource().getHostList();
        for (Host host : hosts) {
            when(flowMutexResourceRepository.save(host.getIpLan(), getCacheData(flowContext))).thenReturn(true);
        }
        // Run the test
        boolean result = mutexResourceClient.lock(request);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testLockCommonResource() {
        // Setupd
        FlowContext flowContext = buildMgwRsAddFlowContext();
        MutexResourceRequest request = MutexResourceRequest.builder().flowContext(flowContext).build();

        List<String> mutexResourceCacheKeyList = getMutexResourceCacheKey(flowContext);
        for (String cacheKey : mutexResourceCacheKeyList) {
            when(flowMutexResourceRepository.save(cacheKey, getCacheData(flowContext))).thenReturn(true);
        }

        // Run the test
        boolean result = mutexResourceClient.lock(request);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testUnLockIPResource() {
        // Setup
        FlowContext flowContext = buildHostsRebootFlowContext();
        MutexResourceRequest request = MutexResourceRequest.builder().flowContext(flowContext).build();

        List<Host> hosts = flowContext.getResource().getHostList();
        for (Host host : hosts) {
            when(flowMutexResourceRepository.delete(host.getIpLan())).thenReturn(true);
        }
        // Run the test
        boolean result = mutexResourceClient.unLock(request);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testUnLockCommonResource() {
        // Setup
        FlowContext flowContext = buildMgwRsAddFlowContext();
        MutexResourceRequest request = MutexResourceRequest.builder().flowContext(flowContext).build();

        List<String> mutexResourceCacheKeyList = getMutexResourceCacheKey(flowContext);
        for (String cacheKey : mutexResourceCacheKeyList) {
            when(flowMutexResourceRepository.delete(cacheKey)).thenReturn(true);
        }

        // Run the test
        boolean result = mutexResourceClient.unLock(request);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testCanSkip() {
        // Setup
        FlowContext flowContext = buildMgwRsAddFlowContext();
        flowContext.setEnv("prod");
        MutexResourceRequest request = MutexResourceRequest.builder().flowContext(flowContext).build();
        AppkeyBO appkeyBO = new AppkeyBO();
        appkeyBO.setApplicationName("Buffer");
        when(appKeyResource.getByAppkey(flowContext.getResource().getAppkey()))
                .thenReturn(appkeyBO);
        // Run the test
        boolean result = mutexResourceClient.canSkip(request);

        // Verify the results
        assertThat(result).isTrue();
    }

    private FlowContext buildHostsRebootFlowContext() {
        /*
         * 机器重启
         * {
         *     "input":{
         *         "env":"prod",
         *         "reason":"test",
         *         "appkey":"com.sankuai.avatar.workflow.server",
         *         "hosts":[
         *             {
         *                 "name":"set-xr-avatar-workflow-server01",
         *                 "ip_lan":"10.43.84.164",
         *                 "uuid":"72b1e707-6181-422b-ae81-6ce7977f5fd1"
         *             }
         *         ]
         *     }
         * }
         */
        HostsRebootInput hostsRebootInput = getHostsRebootInput();
        return FlowContext.builder()
                .templateName("hosts_reboot")
                .uuid("eac32463-de89-4013-a44e-be83a473d5a2")
                .env("test")
                .flowInput(hostsRebootInput)
                .resource(FlowResource.builder().appkey(TEST_APP_KEY).hostList(getHosts()).build())
                .createUserSource(FlowUserSource.USER)
                .createUser("zhaozhifan02")
                .build();
    }


    private FlowContext buildMgwRsAddFlowContext() {
        /*
         *  新增RS
         * {
         *     "input":{
         *         "appkey":"com.sankuai.avatar.develop",
         *         "env":"test",
         *         "rport":8899,
         *         "weight":100,
         *         "delay_before_retry":3,
         *         "nb_get_retry":5,
         *         "ht_get_retry":3,
         *         "connect_timeout":3,
         *         "is_sorry_rs":false,
         *         "protocol":"TCP",
         *         "check_type":"TCP",
         *         "rips":[
         *             "10.48.185.164"
         *         ],
         *         "vs_list":[
         *             {
         *                 "vip":"10.48.27.169",
         *                 "vport":8081,
         *                 "protocol":"TCP"
         *             }
         *         ],
         *         "connect_port":8899
         *     }
         * }
         */
        MgwRsAddInput mgwRsAddInput = getMgwRsAddInput();

        FlowMutexResource mutexResource = FlowMutexResource.builder()
                .mutexFields(Collections.singletonList("$.input.vsList..vip;$.input.vsList..vport;$.input.vsList..protocol"))
                .build();
        FlowSwitch flowSwitch = FlowSwitch.builder().mutexResource(true).build();
        return FlowContext.builder()
                .templateName("mgw_rs_add")
                .uuid("eac32463-de89-4013-a44e-be83a473d5a3")
                .env("test")
                .flowInput(mgwRsAddInput)
                .resource(FlowResource.builder().appkey(TEST_APP_KEY).srv("meituan.avatar.test.avatar-develop").build())
                .createUserSource(FlowUserSource.USER)
                .createUser("zhaozhifan02")
                .flowConfig(FlowConfig.builder().mutexResource(mutexResource).switches(flowSwitch).build())
                .build();
    }

    private HostsRebootInput getHostsRebootInput() {
        HostsRebootInput hostsRebootInput = new HostsRebootInput();
        hostsRebootInput.setAppkey(TEST_APP_KEY);
        hostsRebootInput.setReason("测试");
        hostsRebootInput.setEnv("test");
        hostsRebootInput.setHosts(getHosts());
        return hostsRebootInput;
    }

    private List<Host> getHosts() {
        Host host = Host.builder()
                .name("set-xr-avatar-workflow-server01")
                .ipLan("10.43.84.164")
                .uuid("72b1e707-6181-422b-ae81-6ce7977f5fd1")
                .build();
        return Collections.singletonList(host);
    }

    private MgwRsAddInput getMgwRsAddInput() {
        Vs vs = new Vs();
        vs.setVip("10.48.27.169");
        vs.setVport(8081);
        vs.setProtocol("TCP");
        MgwRsAddInput mgwRsAddInput = new MgwRsAddInput();
        mgwRsAddInput.setAppKey(TEST_APP_KEY);
        mgwRsAddInput.setEnv("test");
        mgwRsAddInput.setRport(8899);
        mgwRsAddInput.setWeight(100);
        mgwRsAddInput.setDelayBeforeRetry(3);
        mgwRsAddInput.setNbGetRetry(5);
        mgwRsAddInput.setHtGetRetry(3);
        mgwRsAddInput.setConnectTimeout(3);
        mgwRsAddInput.setIsSorryRs(false);
        mgwRsAddInput.setProtocol("TCP");
        mgwRsAddInput.setCheckType("TCP");
        mgwRsAddInput.setRips(Collections.singletonList("10.48.185.164"));
        mgwRsAddInput.setVsList(Collections.singletonList(vs));
        return mgwRsAddInput;
    }

    /**
     * 获取互斥资源缓存 key 列表
     *
     * @param flowContext 流程上下文
     * @return 缓存 key 列表
     */
    private List<String> getMutexResourceCacheKey(FlowContext flowContext) {
        List<String> cacheKeys = new ArrayList<>();
        List<List<String>> mutexResourceList = getMutexResource(flowContext);
        if (CollectionUtils.isEmpty(mutexResourceList)) {
            return cacheKeys;
        }
        for (List<String> resources : mutexResourceList) {
            cacheKeys.add(StringUtils.join(resources, RESOURCE_CACHE_FIELD_DELIMITER));
        }
        return cacheKeys;
    }

    private List<List<String>> getMutexResource(FlowContext flowContext) {
        List<List<String>> result = new ArrayList<>(Collections.emptyList());
        FlowConfig flowConfig = flowContext.getFlowConfig();
        if (flowConfig == null || flowConfig.getMutexResource() == null) {
            return result;
        }
        List<String> mutexFields = flowConfig.getMutexResource().getMutexFields();
        if (CollectionUtils.isEmpty(mutexFields)) {
            return result;
        }
        String inputJson = getInputJsonStr(flowContext);
        for (String field : mutexFields) {
            String[] jsonPathKey = field.split(MUTEX_FIELD_DELIMITER);
            List<String> values = getJsonPathValue(inputJson, jsonPathKey);
            result.add(values);
        }
        return result;
    }


    private String getInputJsonStr(FlowContext flowContext) {
        FlowInput flowInput = flowContext.getFlowInput();
        MutexResourceInfo resourceInfo = MutexResourceInfo.builder()
                .input(flowInput)
                .appKey(flowContext.getResource().getAppkey())
                .env(flowContext.getEnv())
                .templateName(flowContext.getTemplateName())
                .build();
        return JsonUtil.bean2Json(resourceInfo);
    }

    private List<String> getJsonPathValue(String inputJson, String[] jsonPathKey) {
        List<String> jsonPathValueList = new ArrayList<>(Collections.emptyList());
        Configuration configuration = Configuration
                .builder()
                .options(Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS)
                .build();
        for (String key : jsonPathKey) {
            List<String> jsonPathValue = JsonPath.using(configuration).parse(inputJson).read(key);
            if (jsonPathValue == null) {
                continue;
            }
            jsonPathValueList.addAll(jsonPathValue);
        }
        return jsonPathValueList;
    }

    private String getCacheData(FlowContext flowContext) {
        String templateName = flowContext.getTemplateName();
        String uuid = flowContext.getUuid();
        return String.join(RESOURCE_CACHE_DATA_DELIMITER, Arrays.asList(templateName, uuid));
    }

}
