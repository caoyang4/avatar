package com.sankuai.avatar.workflow.core.client.lock;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.workflow.core.client.lock.impl.FlowLockClientImpl;
import com.sankuai.avatar.workflow.core.client.lock.request.FlowLockRequest;
import com.sankuai.avatar.workflow.core.client.lock.response.FlowLockResponse;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.context.FlowUserSource;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import com.sankuai.avatar.workflow.core.input.solution.UnlockDeployInput;
import com.sankuai.avatar.dao.workflow.repository.FlowCreateLockRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlowLockClientTest {

    private static final String TEST_APP_KEY = "com.sankuai.avatar.workflow.server";

    /**
     * 重复流程提交提醒
     */
    private static final String REPEAT_FLOW_WARNING_MSG = "重复提交相同任务,请稍后再试";

    @Mock
    private FlowCreateLockRepository flowCreateLockRepository;

    private FlowLockClient flowLockClient;

    @Before
    public void setUp() {
        flowLockClient = new FlowLockClientImpl(flowCreateLockRepository);
    }

    @Test
    public void testValidateSuccess() {
        // Setup
        FlowContext flowContext = buildUnlockDeployFlowContext();
        FlowLockRequest flowLockRequest = FlowLockRequest.builder().flowContext(flowContext).build();

        final FlowLockResponse expectedResult = FlowLockResponse.builder().locked(false).build();
        String cacheKey = generateResourceMd5(flowContext);
        when(flowCreateLockRepository.isExists(cacheKey)).thenReturn(false);

        // Run the test
        final FlowLockResponse result = flowLockClient.validate(flowLockRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testValidateFail() {
        // Setup
        FlowContext flowContext = buildUnlockDeployFlowContext();
        FlowLockRequest flowLockRequest = FlowLockRequest.builder().flowContext(flowContext).build();

        final FlowLockResponse expectedResult = FlowLockResponse.builder()
                .locked(true)
                .message(REPEAT_FLOW_WARNING_MSG)
                .build();
        String cacheKey = generateResourceMd5(flowContext);
        when(flowCreateLockRepository.isExists(cacheKey)).thenReturn(true);

        // Run the test
        final FlowLockResponse result = flowLockClient.validate(flowLockRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testAcquire() {
        // Setup
        FlowContext flowContext = buildUnlockDeployFlowContext();
        FlowLockRequest flowLockRequest = FlowLockRequest.builder().flowContext(flowContext).build();

        String cacheKey = generateResourceMd5(flowContext);
        when(flowCreateLockRepository.save(cacheKey, getCacheResource(flowContext), 180)).thenReturn(true);
        // Run the test
        final boolean result = flowLockClient.acquire(flowLockRequest);
        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testRelease() {
        // Setup
        FlowContext flowContext = buildUnlockDeployFlowContext();
        FlowLockRequest flowLockRequest = FlowLockRequest.builder().flowContext(flowContext).build();

        String cacheKey = generateResourceMd5(flowContext);
        when(flowCreateLockRepository.delete(cacheKey)).thenReturn(true);
        // Run the test
        final boolean result = flowLockClient.release(flowLockRequest);
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
                .createUserSource(FlowUserSource.USER)
                .createUser("zhaozhifan02")
                .build();
    }

    private UnlockDeployInput getFlowInput() {
        UnlockDeployInput unlockDeployInput = new UnlockDeployInput();
        unlockDeployInput.setAppkey(TEST_APP_KEY);
        unlockDeployInput.setReason("紧急Bug修复及服务扩容");
        unlockDeployInput.setComment("测试");
        return unlockDeployInput;
    }

    private String generateResourceMd5(FlowContext flowContext) {
        String cacheResource = getCacheResource(flowContext);
        return DigestUtils.md5Hex(cacheResource).toUpperCase();
    }

    private String getCacheResource(FlowContext flowContext) {
        String createUser = flowContext.getCreateUser();
        FlowInput flowInput = flowContext.getFlowInput();
        String templateName = flowContext.getTemplateName();
        String inputJsonStr = JsonUtil.bean2Json(flowInput);
        return createUser + templateName + inputJsonStr;
    }
}
