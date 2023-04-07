package com.sankuai.avatar.workflow.core.engine.process.impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.listener.PushEvent;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.Submit;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AbstractFlowProcessTest {

    @Mock
    private PushEvent mockPushEvent;
    @Mock
    private Submit mockSubmit;
    @Mock
    private ProcessContext mockProcessContext;
    @Mock
    private FlowContext mockFlowContext;

    private AbstractFlowProcess abstractFlowProcess;

    @Before
    public void setUp() {
        abstractFlowProcess = new AbstractFlowProcess() {
            @Override
            protected Response doProcess(ProcessContext processContext) {
                return Response.of(FlowState.PRE_CHECK_ACCEPTED);
            }
        };

        ReflectionTestUtils.setField(abstractFlowProcess, "pushEvent", mockPushEvent);
        ReflectionTestUtils.setField(abstractFlowProcess, "submit", mockSubmit);
    }

    @Test
    public void testSetFlowState() {
        // Run the test
        abstractFlowProcess.setFlowState(mockFlowContext, FlowState.NEW);

        // Verify the results
        verify(mockPushEvent, times(1)).pushEvent(mockFlowContext, FlowState.NEW);
        verify(mockFlowContext, times(1)).setFlowState(FlowState.NEW);
    }

    @Test
    public void testProcess() {
        // Setup
        when(mockProcessContext.getFlowContext()).thenReturn(this.mockFlowContext);
        when(mockProcessContext.getProcessTemplate()).thenReturn(mock(ProcessTemplate.class));
        when(mockFlowContext.getFlowState()).thenReturn(FlowState.NEW);

        // Run the test
        abstractFlowProcess.process(mockProcessContext, null);

        // Verify the results
        verify(mockFlowContext, times(1)).setCurrentProcessContext(any());
        verify(mockSubmit, times(1)).submit(any(ProcessTemplate.class));
        verify(mockPushEvent, times(1)).pushEvent(mockFlowContext, FlowState.PRE_CHECK_ACCEPTED);
        verify(mockProcessContext, times(1)).getProcessTemplate();
        verify(mockFlowContext, times(1)).setFlowState(FlowState.PRE_CHECK_ACCEPTED);
    }

    @Test
    public void testEventProcess() {
        // Setup
        when(mockProcessContext.getFlowContext()).thenReturn(this.mockFlowContext);
        SchedulerEventContext schedulerEventContext = mock(SchedulerEventContext.class);

        // Run the test
        abstractFlowProcess.process(mockProcessContext, schedulerEventContext);

        // verify the result
        verify(mockFlowContext, times(1)).setCurrentProcessContext(any());
        verify(mockSubmit, never()).submit(any(ProcessTemplate.class));
        verify(mockPushEvent, never()).pushEvent(any(), any());
    }
}
