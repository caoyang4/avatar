package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.dao.workflow.repository.FlowEventRepository;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventUpdateRequest;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CloudTrailEventFlowListenTest {

    @Mock
    private FlowEventRepository flowEventRepository;

    private CloudTrailEventFlowListen cloudTrailEventFlowListen;

    @Before
    public void setUp() {
        cloudTrailEventFlowListen = new CloudTrailEventFlowListen(flowEventRepository);
    }

    @Test
    public void testReceiveEventsNullFlowContext() {
        cloudTrailEventFlowListen.receiveEvents(null, FlowState.NEW);
        verify(flowEventRepository, times(0)).addFlowEvent(any(FlowEventAddRequest.class));
    }

    @Test
    public void testReceiveEventsNullFlowState() {
        cloudTrailEventFlowListen.receiveEvents(mock(FlowContext.class), null);
        verify(flowEventRepository, times(0)).addFlowEvent(any(FlowEventAddRequest.class));
    }

    @Test
    public void testReceiveEventsNewFlowState() {
        cloudTrailEventFlowListen.receiveEvents(mock(FlowContext.class), FlowState.NEW);
        verify(flowEventRepository, times(1)).addFlowEvent(any(FlowEventAddRequest.class));
    }

    @Test
    public void testReceiveEventsCompleteFlowState() {
        cloudTrailEventFlowListen.receiveEvents(mock(FlowContext.class), FlowState.EXECUTE_SUCCESS);
        verify(flowEventRepository, times(0)).addFlowEvent(any(FlowEventAddRequest.class));
        verify(flowEventRepository, times(1)).updateFlowEvent(any(FlowEventUpdateRequest.class));
    }

    @Test
    public void testReceiveEventsNoMatchFlowState() {
        cloudTrailEventFlowListen.receiveEvents(mock(FlowContext.class), FlowState.PRE_CHECK_REJECTED);
        verify(flowEventRepository, never()).addFlowEvent(any(FlowEventAddRequest.class));
        verify(flowEventRepository, times(1)).updateFlowEvent(any(FlowEventUpdateRequest.class));
    }
}