package com.sankuai.avatar.workflow.core.checker.global;

import com.sankuai.avatar.workflow.core.checker.CheckResult;
import com.sankuai.avatar.workflow.core.checker.CheckState;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.input.jumper.JumperUserUnlockInput;
import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ValidateInputGlobalCheckerTest {

    @InjectMocks
    private ValidateInputGlobalChecker validateInputGlobalChecker;

    @Test
    public void testCheckNullInput() {
        CheckResult checkResult = validateInputGlobalChecker.check(mock(FlowContext.class));
        Assert.assertNotNull(checkResult);
        Assert.assertEquals(checkResult.getCheckState(), CheckState.PRE_CHECK_ACCEPTED);

    }

    @Test
    public void testCheck() {
        JumperUserUnlockInput input = new JumperUserUnlockInput();
        FlowContext flowContext = FlowContext.builder().flowInput(input).build();
        CheckResult checkResult = validateInputGlobalChecker.check(flowContext);
        Assert.assertNotNull(checkResult);
        Assert.assertEquals(checkResult.getCheckState(), CheckState.PRE_CHECK_REJECTED);
    }
}