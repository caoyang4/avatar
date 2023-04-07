package com.sankuai.avatar.workflow.core.engine.scheduler;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.loader.FlowContextLoader;
import com.sankuai.avatar.workflow.core.context.request.FlowCreateRequest;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContextLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoaderImplTest {

    @Mock
    private ProcessContextLoader processContextLoader;
    @Mock
    private FlowContextLoader flowContextLoader;

    @InjectMocks
    private LoaderImpl loaderImplUnderTest;


    @Test
    public void testFlowId() {
        // mock FlowContext
        FlowContext flowContext = mock(FlowContext.class);
        when(flowContextLoader.id(anyInt())).thenReturn(flowContext);
        // mock processContextLoader
        ProcessContext processContext = mock(ProcessContext.class);
        when(processContextLoader.buildByFlowContext(flowContext)).thenReturn(Arrays.asList(processContext, processContext));

        // Run this test
        loaderImplUnderTest.flowId(10);

        // Verify this Result
        verify(flowContextLoader, times(1)).id(10);
        verify(processContextLoader, times(1)).buildByFlowContext(flowContext);
    }

    @Test
    public void testFlowTemplateName() {
        // mock FlowContext
        FlowContext flowContext = mock(FlowContext.class);
        when(flowContextLoader.buildByTemplateName(any(), any())).thenReturn(flowContext);
        // mock processContextLoader
        ProcessContext processContext = mock(ProcessContext.class);
        when(processContextLoader.buildByFlowContext(flowContext)).thenReturn(Arrays.asList(processContext, processContext));

        // Run this test
        loaderImplUnderTest.flowTemplateName("name", mock(FlowCreateRequest.class));

        // Verify this Result
        verify(flowContextLoader, times(1)).buildByTemplateName(any(), any());
        verify(processContextLoader, times(1)).buildByFlowContext(any());
    }


}
