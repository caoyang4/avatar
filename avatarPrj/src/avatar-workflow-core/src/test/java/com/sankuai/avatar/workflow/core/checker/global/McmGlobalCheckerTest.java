package com.sankuai.avatar.workflow.core.checker.global;

import com.sankuai.avatar.workflow.core.checker.CheckResult;
import com.sankuai.avatar.workflow.core.checker.CheckState;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.mcm.McmClient;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreCheckResponse;
import com.sankuai.mcm.client.sdk.dto.common.PreCheckItem;
import com.sankuai.mcm.client.sdk.enums.Decision;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class McmGlobalCheckerTest {
    @Mock
    private McmClient mcmClient;

    @InjectMocks
    private McmGlobalChecker mcmGlobalChecker;

    private PreCheckItem rejectPreCheck;
    private PreCheckItem warnPreCheck;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(mcmGlobalChecker, "mcmCheckSwitch", true);
        // reject response
        rejectPreCheck = new PreCheckItem();
        rejectPreCheck.setName("test");
        rejectPreCheck.setDecision(Decision.REJECT);
        rejectPreCheck.setPlainDescription("测试一下拦截");
        // warn response
        warnPreCheck = new PreCheckItem();
        warnPreCheck.setName("test");
        warnPreCheck.setDecision(Decision.WARNING);
        warnPreCheck.setPlainDescription("测试一下警告");
    }

    @Test
    public void testCheckReject() {
        // mock FlowContext
        FlowContext flowContext = mock(FlowContext.class);
        when(flowContext.getTemplateName()).thenReturn("any");
        // mock mcm response
        McmPreCheckResponse preCheckResponse = new McmPreCheckResponse();
        preCheckResponse.setDecision(Decision.REJECT);
        preCheckResponse.setItems(Arrays.asList(rejectPreCheck, warnPreCheck));
        when(mcmClient.preCheck(any())).thenReturn(preCheckResponse);
        // do checker
        CheckResult checkResult = mcmGlobalChecker.check(flowContext);
        Assert.assertNotNull(checkResult);
        Assert.assertEquals(checkResult.getCheckState(), CheckState.PRE_CHECK_REJECTED);
    }
    @Test
    public void testCheckWarning() {
        // mock FlowContext
        FlowContext flowContext = mock(FlowContext.class);
        when(flowContext.getTemplateName()).thenReturn("any");
        // mock mcm response
        McmPreCheckResponse preCheckResponse = new McmPreCheckResponse();
        preCheckResponse.setDecision(Decision.WARNING);
        preCheckResponse.setItems(Collections.singletonList(warnPreCheck));
        when(mcmClient.preCheck(any())).thenReturn(preCheckResponse);
        // do checker
        CheckResult checkResult = mcmGlobalChecker.check(flowContext);
        Assert.assertNotNull(checkResult);
        Assert.assertNotNull(checkResult.getResultItems());
        Assert.assertEquals(checkResult.getCheckState(), CheckState.PRE_CHECK_WARNING);
    }

}
