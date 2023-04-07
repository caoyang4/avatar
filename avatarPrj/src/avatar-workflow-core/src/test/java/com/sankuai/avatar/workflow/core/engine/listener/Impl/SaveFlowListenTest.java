package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SaveFlowListenTest {

    @Mock
    private FlowRepository flowRepository;

    private SaveFlowListen saveFlowListen;

    @Before
    public void setUp() {
        saveFlowListen = new SaveFlowListen();
        saveFlowListen.flowRepository = flowRepository;
    }

    @Test
    public void testReceiveEventsNullFlowContext() {
        saveFlowListen.receiveEvents(null, FlowState.NEW);
        verify(flowRepository, times(0)).updateFlow(any(FlowEntity.class));
    }

    @Test
    public void testReceiveEventsNullFlowState() {
        FlowContext flowContext = mock(FlowContext.class);
        saveFlowListen.receiveEvents(flowContext, null);
        verify(flowRepository, times(0)).updateFlow(any(FlowEntity.class));
    }

    @Test
    public void testReceiveEventsNewFlowState() {
        FlowContext flowContext = mock(FlowContext.class);
        saveFlowListen.receiveEvents(flowContext, FlowState.NEW);
        verify(flowRepository, times(0)).updateFlow(any(FlowEntity.class));
    }

    @Test
    public void testReceiveEvents() {
        FlowContext flowContext = mock(FlowContext.class);
        saveFlowListen.receiveEvents(flowContext, FlowState.EXECUTE_SUCCESS);
        verify(flowRepository, times(1)).updateFlow(any(FlowEntity.class));
    }
}