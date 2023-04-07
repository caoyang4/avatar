package com.sankuai.avatar.workflow.core.engine.process.impl;

import com.sankuai.avatar.workflow.core.checker.CheckHandler;
import com.sankuai.avatar.workflow.core.checker.CheckResult;
import com.sankuai.avatar.workflow.core.checker.CheckState;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.listener.PushEvent;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import com.sankuai.avatar.workflow.core.engine.process.response.PreCheckResult;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PreCheckFlowProcessTest {
    @Mock
    private PushEvent mockPushEvent;
    @Mock
    private CheckHandler mockCheckHandler;
    @Mock
    private ProcessContext processContext;
    @Mock
    private FlowContext flowContext;
    @InjectMocks
    private PreCheckFlowProcess preCheckFlowProcessUnderTest;

    /**
     * 预检通过场景
     */
    @Test
    public void testDoProcessAccept() {
        // Setup
        CheckResult checkAccept = CheckResult.of("", CheckState.PRE_CHECK_ACCEPTED);

        when(processContext.getFlowContext()).thenReturn(flowContext);
        when(mockCheckHandler.checker(flowContext)).thenReturn(Arrays.asList(checkAccept, checkAccept));

        // Run the test
        final Response result = preCheckFlowProcessUnderTest.doProcess(processContext);

        // Verify the results
        PreCheckResult preCheckResult = result.getResult(PreCheckResult.class);
        Assert.assertEquals(preCheckResult.getCheckState(), CheckState.PRE_CHECK_ACCEPTED);
        verify(mockCheckHandler, times(1)).checker(flowContext);
        verify(mockPushEvent, times(1)).pushEvent(flowContext, FlowState.PRE_CHECK_LAUNCHED);
    }

    /**
     * 预检拒绝场景
     */
    @Test
    public void testDoProcessReject() {
        // Setup
        CheckResult checkReject = CheckResult.of("", CheckState.PRE_CHECK_REJECTED);
        CheckResult checkWarn = CheckResult.of("", CheckState.PRE_CHECK_WARNING);

        when(processContext.getFlowContext()).thenReturn(flowContext);
        when(mockCheckHandler.checker(flowContext)).thenReturn(Arrays.asList(checkWarn, checkReject));

        // Run the test
        final Response result = preCheckFlowProcessUnderTest.doProcess(processContext);

        // Verify the results
        PreCheckResult preCheckResponse = result.getResult(PreCheckResult.class);
        Assert.assertEquals(preCheckResponse.getCheckState(), CheckState.PRE_CHECK_REJECTED);
        verify(mockCheckHandler, times(1)).checker(flowContext);
        verify(mockPushEvent, times(1)).pushEvent(flowContext, FlowState.PRE_CHECK_LAUNCHED);
    }

    /**
     * 预检告警场景
     */
    @Test
    public void testDoProcessWarn() {
        // Setup
        CheckResult checkWarn = CheckResult.of("", CheckState.PRE_CHECK_WARNING);

        when(processContext.getFlowContext()).thenReturn(flowContext);
        when(mockCheckHandler.checker(flowContext)).thenReturn(Arrays.asList(checkWarn, checkWarn));

        // Run the test
        final Response result = preCheckFlowProcessUnderTest.doProcess(processContext);

        // Verify the results
        PreCheckResult preCheckResponse = result.getResult(PreCheckResult.class);
        Assert.assertEquals(preCheckResponse.getCheckState(), CheckState.PRE_CHECK_WARNING);
        verify(mockPushEvent, times(1)).pushEvent(flowContext, FlowState.PRE_CHECK_LAUNCHED);
    }

    @Test
    public void testDoEventProcess() {
        // Setup
        final ProcessContext processContext = ProcessContext.builder()
                //.processState(ProcessState.NEW)
                .flowContext(FlowContext.builder()
                        .flowInput(null)
                        .flowState(FlowState.NEW)
                        .build())
                .processTemplate(ProcessTemplate.builder().build())
                .build();
        final SchedulerEventContext schedulerEventContext = SchedulerEventContext.builder()
                .schedulerEventEnum(SchedulerEventEnum.PRE_CHECK_CONFIRM)
                .build();

        // Run the test
        final Response result = preCheckFlowProcessUnderTest.doEventProcess(processContext, schedulerEventContext);

        // Verify the results
        Assert.assertEquals(result.getFlowState(), FlowState.PRE_CHECK_ACCEPTED);
    }

}
