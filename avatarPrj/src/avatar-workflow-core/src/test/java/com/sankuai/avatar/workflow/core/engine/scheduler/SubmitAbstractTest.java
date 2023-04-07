package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessTemplate;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventContext;
import com.sankuai.avatar.workflow.core.engine.scheduler.event.SchedulerEventEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.Future;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubmitAbstractTest {

    @Mock
    private Heartbeat heartbeat;
    @Mock
    private Loader mockLoader;
    @Mock
    private Scheduler scheduler;

    private SubmitAbstract submitAbstractUnderTest;

    @Before
    public void setUp() {
        submitAbstractUnderTest = new SubmitAbstract() {
            @Override
            boolean submitDb(Integer flowId) {
                return false;
            }

            @Override
            boolean submitMq(Integer flowId) {
                return false;
            }

            @Override
            Future<Response> submitScheduler(ProcessTemplate processTemplate, SchedulerEventContext schedulerEventContext) {
                return scheduler.dispatch(processTemplate, schedulerEventContext);
            }
        };

        ReflectionTestUtils.setField(submitAbstractUnderTest, "loader", mockLoader);
        ReflectionTestUtils.setField(submitAbstractUnderTest, "heartbeat", heartbeat);
    }


    @Test
    public void testSubmit() {
        // Setup
        SchedulerEventContext schedulerEventContext = mock(SchedulerEventContext.class);
        when(schedulerEventContext.getSchedulerEventEnum()).thenReturn(SchedulerEventEnum.AUDIT_ACCEPTED);
        // mock flowContext
        FlowContext flowContext = mock(FlowContext.class);
        when(flowContext.getFlowState()).thenReturn(FlowState.NEW);
        //mock processContext
        ProcessContext processContext = mock(ProcessContext.class);
        when(processContext.getName()).thenReturn("PreCheckFlowProcess");
        when(processContext.getFlowContext()).thenReturn(flowContext);
        // mock processTemplate
        ProcessTemplate processTemplate = mock(ProcessTemplate.class);
        when(processTemplate.getCurrentProcesses()).thenReturn(processContext);

        // Configure Loader.flowId(...).
        when(mockLoader.flowId(anyInt())).thenReturn(processTemplate);

        // Run the test
        // 提交id场景
        submitAbstractUnderTest.submit(1);
        // 提交Context场景
        submitAbstractUnderTest.submit(processTemplate, schedulerEventContext);

        // Verify the results
        verify(mockLoader, times(1)).flowId(anyInt());
        verify(scheduler, times(2)).dispatch(any(), any());
        //verify(heartbeat, times(2)).cleanHeartbeat(flowContext);
    }
}
