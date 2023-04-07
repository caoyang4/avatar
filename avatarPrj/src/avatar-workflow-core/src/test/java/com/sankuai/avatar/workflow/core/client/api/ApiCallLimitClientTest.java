package com.sankuai.avatar.workflow.core.client.api;

import com.sankuai.avatar.dao.workflow.repository.FlowCreateLockRepository;
import com.sankuai.avatar.workflow.core.client.api.impl.ApiCallLimitClientImpl;
import com.sankuai.avatar.workflow.core.client.api.request.ApiCallLimitRequest;
import com.sankuai.avatar.workflow.core.client.api.response.ApiCallLimitResponse;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.context.FlowUserSource;
import com.sankuai.avatar.workflow.core.input.solution.UnlockDeployInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApiCallLimitClientTest {

    private static final String TEST_APP_KEY = "com.sankuai.avatar.workflow.server";

    /**
     * API 限流提醒
     */
    private static final String API_LIMIT_WARNING_MSG = "API调用过于频繁，请稍后再试";

    /**
     * API 调用默认递增值
     */
    private static final Long DEFAULT_INCREASE_VALUE = 1L;

    @Mock
    private FlowCreateLockRepository flowCreateLockRepository;

    private ApiCallLimitClient apiCallLimitClient;

    @Before
    public void setUp() {
        apiCallLimitClient = new ApiCallLimitClientImpl(flowCreateLockRepository);
    }

    @Test
    public void testValidateSuccess() {
        // Setup
        FlowContext flowContext = buildUnlockDeployFlowContext();
        ApiCallLimitRequest request = ApiCallLimitRequest.builder().flowContext(flowContext).build();

        final ApiCallLimitResponse expectedResult = ApiCallLimitResponse.builder().locked(false).build();
        String cacheKey = generateApiUserCacheKey(flowContext);
        when(flowCreateLockRepository.get(cacheKey, Integer.class)).thenReturn(1);
        ReflectionTestUtils.setField(apiCallLimitClient, "apiUserLimitStrategy",
                "{\"__system\":{\"interval\": 60,\"frequency\": 1}}");
        // Run the test
        final ApiCallLimitResponse result = apiCallLimitClient.validate(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testValidateFail() {
        // Setup
        FlowContext flowContext = buildUnlockDeployFlowContext();
        ApiCallLimitRequest request = ApiCallLimitRequest.builder().flowContext(flowContext).build();

        final ApiCallLimitResponse expectedResult = ApiCallLimitResponse.builder()
                .locked(true)
                .message(API_LIMIT_WARNING_MSG)
                .build();
        String cacheKey = generateApiUserCacheKey(flowContext);
        when(flowCreateLockRepository.get(cacheKey, Integer.class)).thenReturn(10);
        ReflectionTestUtils.setField(apiCallLimitClient, "apiUserLimitStrategy",
                "{\"__system\":{\"interval\": 60,\"frequency\": 1}}");
        // Run the test
        final ApiCallLimitResponse result = apiCallLimitClient.validate(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testAcquire() {
        // Setup
        FlowContext flowContext = buildUnlockDeployFlowContext();
        ApiCallLimitRequest request = ApiCallLimitRequest.builder().flowContext(flowContext).build();
        String cacheKey = generateApiUserCacheKey(flowContext);
        when(flowCreateLockRepository.save(cacheKey, DEFAULT_INCREASE_VALUE, 60)).thenReturn(true);
        ReflectionTestUtils.setField(apiCallLimitClient, "apiUserLimitStrategy",
                "{\"__system\":{\"interval\": 60,\"frequency\": 1}}");

        // Run the test
        final boolean result = apiCallLimitClient.acquire(request);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testRelease() {
        // Setup
        FlowContext flowContext = buildUnlockDeployFlowContext();
        ApiCallLimitRequest request = ApiCallLimitRequest.builder().flowContext(flowContext).build();
        String cacheKey = generateApiUserCacheKey(flowContext);
        when(flowCreateLockRepository.delete(cacheKey)).thenReturn(true);

        // Run the test
        final boolean result = apiCallLimitClient.release(request);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testIncreaseApiHit() {
        // Setup
        FlowContext flowContext = buildUnlockDeployFlowContext();
        ApiCallLimitRequest request = ApiCallLimitRequest.builder().flowContext(flowContext).build();
        String cacheKey = generateApiUserCacheKey(flowContext);
        Long value = 1L;
        when(flowCreateLockRepository.increment(cacheKey, value)).thenReturn(true);

        // Run the test
        final boolean result = apiCallLimitClient.increaseApiHit(request);

        // Verify the results
        assertThat(result).isTrue();
    }

    private FlowContext buildUnlockDeployFlowContext() {
        /*
         * 高峰期解禁
         * {
         *     "input":{
         *         "reason":"紧急Bug修复及服务扩容",
         *         "comment":"测试",
         *         "appkey":"com.sankuai.avatar.workflow.server"
         *     }
         * }
         */
        UnlockDeployInput unlockDeployInput = getFlowInput();
        return FlowContext.builder()
                .templateName("unlock_deploy")
                .env("test")
                .flowInput(unlockDeployInput)
                .resource(FlowResource.builder().appkey(TEST_APP_KEY).build())
                .createUserSource(FlowUserSource.APPKEY)
                .createUser("__system")
                .build();
    }

    private UnlockDeployInput getFlowInput() {
        UnlockDeployInput unlockDeployInput = new UnlockDeployInput();
        unlockDeployInput.setAppkey(TEST_APP_KEY);
        unlockDeployInput.setReason("紧急Bug修复及服务扩容");
        unlockDeployInput.setComment("测试");
        return unlockDeployInput;
    }

    private String generateApiUserCacheKey(FlowContext flowContext) {
        FlowResource flowResource = flowContext.getResource();
        String appKey = flowResource.getAppkey();
        String templateName = flowContext.getTemplateName();
        return String.format("flow_api_call_%s_%s_%s", flowContext.getCreateUser(), appKey, templateName);
    }
}
