package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.mcm.McmClient;
import com.sankuai.avatar.workflow.core.mcm.McmEventStatus;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import com.sankuai.avatar.workflow.core.mcm.response.McmEventChangeDetailResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class McmPreCheckCallbackFlowListenTest {

    @Mock
    private McmClient mcmClient;

    @InjectMocks
    private McmPreCheckCallbackFlowListen mcmPreCheckCallbackFlowListen;


    @Test
    public void testReceiveEventsNotAllowAccept() {
        FlowContext flowContext = FlowContext.builder()
                .templateName("userUnlock")
                .uuid("88fedb4b-8c44-4c02-ae1f-d22eef072450")
                .flowState(FlowState.PRE_CHECK_ACCEPTED).build();

        McmEventChangeDetailResponse response = new McmEventChangeDetailResponse();
        response.setStatus(McmEventStatus.PRECHECKED);
        when(mcmClient.getEventChangeDetail(anyString())).thenReturn(response);

        //do event
        mcmPreCheckCallbackFlowListen.receiveEvents(flowContext, FlowState.PRE_CHECK_ACCEPTED);
        verify(mcmClient, never()).preCheckConfirm(any(McmEventRequest.class));
    }

    @Test
    public void testReceiveEventsPreCheckAccept() {
        FlowContext flowContext = FlowContext.builder()
                .templateName("userUnlock")
                .uuid("88fedb4b-8c44-4c02-ae1f-d22eef072450")
                .flowState(FlowState.PRE_CHECK_ACCEPTED)
                .flowState(FlowState.PRE_CHECK_ACCEPTED).build();

        McmEventChangeDetailResponse response = new McmEventChangeDetailResponse();
        response.setStatus(McmEventStatus.WARNING);
        when(mcmClient.getEventChangeDetail(anyString())).thenReturn(response);

        //do event
        mcmPreCheckCallbackFlowListen.receiveEvents(flowContext, FlowState.PRE_CHECK_ACCEPTED);
        verify(mcmClient, times(1)).preCheckConfirm(any(McmEventRequest.class));
    }

    @Test
    public void testReceiveEventsNotAllowCancel() {
        FlowContext flowContext = FlowContext.builder()
                .templateName("userUnlock")
                .uuid("88fedb4b-8c44-4c02-ae1f-d22eef072450")
                .flowState(FlowState.SHUTDOWN).build();


        McmEventChangeDetailResponse response = new McmEventChangeDetailResponse();
        response.setStatus(McmEventStatus.PRECHECKED);
        when(mcmClient.getEventChangeDetail(anyString())).thenReturn(response);

        //do event
        mcmPreCheckCallbackFlowListen.receiveEvents(flowContext, FlowState.SHUTDOWN);
        verify(mcmClient, never()).preCheckCancel(any(McmEventRequest.class));
    }

}