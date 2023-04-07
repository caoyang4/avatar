package com.sankuai.avatar.workflow.core.checker;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CheckHandlerTest {

    @Mock
    private Checker checker;

    private final CheckHandler checkHandlerTest =  new CheckHandler();

    @Before
    public void setUp() throws ExecutionException, InterruptedException, TimeoutException {
        List<Checker> checkerList = Arrays.asList(checker, checker);
        ReflectionTestUtils.setField(checkHandlerTest, "checkers", checkerList);
    }

    /**
     * 预检通过
     */
    @Test
    public void testChecker() {
        // mock FlowContext
        FlowContext flowContext = mock(FlowContext.class);
        // mock checker
        //when(checker.getTimeout()).thenReturn(800);
        when(checker.matchCheck(any())).thenReturn(true);
        when(checker.check(any())).thenReturn(CheckResult.of("ok", CheckState.PRE_CHECK_ACCEPTED));

        // Run the test
        final List<CheckResult> result = checkHandlerTest.checker(flowContext);

        // Verify the results
        verify(checker, times(2)).check(any());
        Assert.assertEquals(0, result.size());
    }

    /**
     * 预检拒绝
     */
    @Test
    public void testCheckerReject() {
        // mock FlowContext
        FlowContext flowContext = mock(FlowContext.class);
        // mock checker
        //when(checker.getTimeout()).thenReturn(1000);
        when(checker.matchCheck(any())).thenReturn(true);
        when(checker.check(any())).thenReturn(CheckResult.of("reject", CheckState.PRE_CHECK_REJECTED));

        // Run the test
        final List<CheckResult> result = checkHandlerTest.checker(flowContext);

        // Verify the results
        verify(checker, times(2)).check(any());
        Assert.assertTrue(result.size() >= 1);
    }
}
