package com.sankuai.avatar.workflow.core.engine.process.impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.listener.PushEvent;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventEnum;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.eventInput.ExecuteCallbackEvent;
import com.sankuai.avatar.workflow.core.execute.AtomLoader;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ExecuteFlowProcessTest {

    @Mock
    private AtomLoader mockAtomLoader;
    @Mock
    private ProcessContext mockProcessContext;
    @Mock
    private PushEvent mockPushEvent;

    private ExecuteFlowProcess executeFlowProcessTest;

    @Before
    public void setUp() throws Exception {
        executeFlowProcessTest = new ExecuteFlowProcess();
        ReflectionTestUtils.setField(executeFlowProcessTest, "atomLoader", mockAtomLoader);
        ReflectionTestUtils.setField(executeFlowProcessTest, "pushEvent", mockPushEvent);
    }

    @Test
    public void testDoProcess() {
        // Setup
        FlowContext flowContext = mock(FlowContext.class);
        when(mockProcessContext.getFlowContext()).thenReturn(flowContext);

        // Run the test
        executeFlowProcessTest.doProcess(mockProcessContext);

        // Verify the results
        verify(flowContext, times(1)).setFlowState(FlowState.EXECUTING);
        verify(mockPushEvent, times(1)).pushEvent(flowContext, FlowState.EXECUTING);
        verify(mockAtomLoader, times(1)).loadAtomTemplate(mockProcessContext, flowContext);
    }

    /**
     * atom执行结束，回调场景
     */
    @Test
    public void testDoEventProcessCallback() {
        // mock ExecuteCallbackEvent
        ExecuteCallbackEvent executeCallbackEvent = mock(ExecuteCallbackEvent.class);
        when(executeCallbackEvent.getAtomState()).thenReturn(AtomStatus.SUCCESS, AtomStatus.PENDING);

        // mock SchedulerEventContext
        SchedulerEventContext schedulerEventContext = mock(SchedulerEventContext.class);
        when(schedulerEventContext.getEventInput(ExecuteCallbackEvent.class)).thenReturn(executeCallbackEvent);
        when(schedulerEventContext.getSchedulerEventEnum()).thenReturn(SchedulerEventEnum.EXECUTE_CALLBACK);

        // 执行2次，分别模拟执行成功、执行失败、执行挂起
        //executeFlowProcessTest.doEventProcess(this.mockProcessContext, schedulerEventContext);
        Response success = executeFlowProcessTest.doEventProcess(this.mockProcessContext, schedulerEventContext);
        Response pending = executeFlowProcessTest.doEventProcess(this.mockProcessContext, schedulerEventContext);

        // Verify the results
        verify(executeCallbackEvent, times(2)).getAtomState();
        Assert.assertEquals(FlowState.EXECUTE_SUCCESS, success.getFlowState());
        Assert.assertEquals(FlowState.EXECUTE_PENDING, pending.getFlowState());
    }

    /**
     * atom执行失败, 触发重试场景
     */
    @Test
    public void testDoEventProcessRetry() {
        // setup
        FlowContext flowContext = mock(FlowContext.class);
        when(mockProcessContext.getFlowContext()).thenReturn(flowContext);

        // mock SchedulerEventContext
        SchedulerEventContext schedulerEventContext = mock(SchedulerEventContext.class);
        when(schedulerEventContext.getSchedulerEventEnum()).thenReturn(SchedulerEventEnum.EXECUTE_RETRY);

        // Run the test
        executeFlowProcessTest.doEventProcess(this.mockProcessContext, schedulerEventContext);

        // verify result
        verify(flowContext, times(1)).setFlowState(FlowState.EXECUTING);
        verify(mockPushEvent, times(1)).pushEvent(flowContext, FlowState.EXECUTING);
        verify(mockAtomLoader, times(1)).loadAtomTemplate(mockProcessContext, flowContext);

    }

}
