package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.dao.workflow.repository.FlowDataRepository;
import com.sankuai.avatar.dao.workflow.repository.request.FlowCheckResultAddRequest;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.response.PreCheckResult;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import junit.framework.TestCase;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PreCheckResultFlowListenTest extends TestCase {
    @Mock
    private FlowDataRepository flowDataRepository;

    @InjectMocks
    private PreCheckResultFlowListen preCheckResultFlowListen;


    @Test
    public void receivePreCheckEventsTest() {
        FlowContext flowContext = FlowContext.builder()
                .id(1)
                .currentProcessContext(ProcessContext.builder().response(Response.of(FlowState.PRE_CHECK_ACCEPTED, PreCheckResult.ofAccept())).build())
                .build();

        this.preCheckResultFlowListen.receiveEvents(flowContext, FlowState.PRE_CHECK_ACCEPTED);

        verify(flowDataRepository, times(1)).addFlowCheckResult(any(FlowCheckResultAddRequest.class));
    }

    @Test
    public void receiveNotPreCheckEventsTest() {
        FlowContext flowContext = FlowContext.builder()
                .currentProcessContext(ProcessContext.builder().response(Response.of(FlowState.PRE_CHECK_ACCEPTED)).build())
                .build();

        this.preCheckResultFlowListen.receiveEvents(flowContext, FlowState.PRE_CHECK_ACCEPTED);

        verify(flowDataRepository, never()).addFlowCheckResult(any(FlowCheckResultAddRequest.class));
    }

}
