package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SchedulerImplTest {

    @Mock
    private Execute mockExecute;
    @Mock
    private Heartbeat mockHeartbeat;
    @Mock
    private FlowContext mockFlowContext;
    @Mock
    private ProcessContext mockProcessContext;

    @InjectMocks
    private SchedulerImpl schedulerTest;

    /**
     * NEW 场景
     */
    @Test
    public void testDispatchNew() {
        // processContext
        when(mockFlowContext.getFlowState()).thenReturn(FlowState.NEW);
        when(mockProcessContext.getFlowContext()).thenReturn(this.mockFlowContext);
        // ProcessTemplate
        ProcessTemplate processTemplate = ProcessTemplate.builder().build();
        processTemplate.setIndex(0);
        processTemplate.setProcesses(Arrays.asList(mockProcessContext, mockProcessContext));
        // mockHeartbeat
        when(mockHeartbeat.checkHeartbeat(this.mockFlowContext)).thenReturn(false);
        when(mockHeartbeat.setHeartbeat(this.mockFlowContext)).thenReturn(true);

        // Run the test
        schedulerTest.dispatch(processTemplate);

        // Verify the results
        Assert.assertEquals(0, (int)processTemplate.getIndex());
        verify(mockHeartbeat, times(1)).checkHeartbeat(this.mockFlowContext);
        verify(mockHeartbeat, times(1)).setHeartbeat(this.mockFlowContext);
        verify(mockExecute, times(1)).execute(mockProcessContext, null);
    }

    /**
     * SUCCESS,  后续没有ProcessContext的场景
     */
    @Test
    public void testDispatchSuccess() {
        // processContext
        when(mockFlowContext.getFlowState()).thenReturn(FlowState.PRE_CHECK_ACCEPTED);
        when(mockProcessContext.getFlowContext()).thenReturn(this.mockFlowContext);
        // ProcessTemplate
        ProcessTemplate processTemplate = ProcessTemplate.builder().build();
        processTemplate.setIndex(0);
        processTemplate.setProcesses(Collections.singletonList(mockProcessContext));
        // mockHeartbeat
        when(mockHeartbeat.checkHeartbeat(this.mockFlowContext)).thenReturn(true);

        // Run the test
        schedulerTest.dispatch(processTemplate);

        // Verify the results
        Assert.assertEquals(0, (int)processTemplate.getIndex());
        verify(mockHeartbeat, times(1)).checkHeartbeat(this.mockFlowContext);
        verify(mockHeartbeat, never()).setHeartbeat(this.mockFlowContext);
        verify(mockHeartbeat, times(1)).cleanHeartbeat(this.mockFlowContext);
        verify(mockExecute, never()).execute(mockProcessContext, null);
    }

    /**
     * ProcessState.SUCCESS,  后续还有ProcessContext的场景
     */
    @Test
    public void testDispatchSuccess2() {
        // processContext
        when(mockFlowContext.getFlowState()).thenReturn(FlowState.PRE_CHECK_ACCEPTED);
        when(mockProcessContext.getFlowContext()).thenReturn(this.mockFlowContext);
        // ProcessTemplate
        ProcessTemplate processTemplate = ProcessTemplate.builder().build();
        processTemplate.setIndex(0);
        processTemplate.setProcesses(Arrays.asList(mockProcessContext, mockProcessContext));
        // mockHeartbeat
        when(mockHeartbeat.checkHeartbeat(this.mockFlowContext)).thenReturn(false);
        when(mockHeartbeat.setHeartbeat(this.mockFlowContext)).thenReturn(true);

        // Run the test
        schedulerTest.dispatch(processTemplate);

        // Verify the results
        Assert.assertEquals(1, (int)processTemplate.getIndex());
        verify(mockHeartbeat, times(1)).checkHeartbeat(this.mockFlowContext);
        verify(mockHeartbeat, times(1)).setHeartbeat(this.mockFlowContext);
        verify(mockHeartbeat, never()).cleanHeartbeat(this.mockFlowContext);
        verify(mockExecute, times(1)).execute(mockProcessContext, null);
    }

    /**
     * ProcessState.PENDING 状态调度
     */
    @Test
    public void testDispatchPending() {
        // processContext
        when(mockFlowContext.getFlowState()).thenReturn(FlowState.PRE_CHECK_REJECTED);
        when(mockProcessContext.getFlowContext()).thenReturn(this.mockFlowContext);
        // ProcessTemplate
        ProcessTemplate processTemplate = ProcessTemplate.builder().build();
        processTemplate.setIndex(0);
        processTemplate.setProcesses(Arrays.asList(mockProcessContext, mockProcessContext));
        // mockHeartbeat
        when(mockHeartbeat.checkHeartbeat(this.mockFlowContext)).thenReturn(true);

        // Run the test
        schedulerTest.dispatch(processTemplate);

        // Verify the results
        Assert.assertEquals(0, (int)processTemplate.getIndex());
        verify(mockHeartbeat, times(1)).checkHeartbeat(this.mockFlowContext);
        verify(mockHeartbeat, times(1)).cleanHeartbeat(this.mockFlowContext);
        verify(mockHeartbeat, never()).setHeartbeat(this.mockFlowContext);
        verify(mockExecute, never()).execute(mockProcessContext, null);
    }

}
