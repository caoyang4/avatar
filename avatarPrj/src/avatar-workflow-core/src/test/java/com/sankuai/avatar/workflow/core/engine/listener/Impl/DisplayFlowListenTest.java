package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.display.DisplayGenerator;
import com.sankuai.avatar.workflow.core.display.impl.JumperUserUnlockGenerator;
import com.sankuai.avatar.dao.workflow.repository.FlowDisplayRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowRepository;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayAddRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class DisplayFlowListenTest {

    @Mock
    private FlowRepository flowRepository;

    @Mock
    private FlowDisplayRepository flowDisplayRepository;

    @Mock
    private Map<String, DisplayGenerator> displayGenerators;

    @Mock
    private JumperUserUnlockGenerator jumperUserUnlockGenerator;

    private DisplayFlowListen displayFlowListen;

    @Before
    public void setUp() {
        displayGenerators.put(jumperUserUnlockGenerator.getClass().getSimpleName(), jumperUserUnlockGenerator);
        displayFlowListen = new DisplayFlowListen(displayGenerators, flowDisplayRepository, flowRepository);
    }


    @Test
    public void receiveEventsNullFlowContext() {
        displayFlowListen.receiveEvents(null, FlowState.NEW);
        verify(displayGenerators, times(0)).get(any(String.class));
        verify(flowDisplayRepository, times(0)).addFlowDisplay(any(FlowDisplayAddRequest.class));
    }

    @Test
    public void receiveEventsNullFlowState() {
        displayFlowListen.receiveEvents(mock(FlowContext.class), null);
        verify(displayGenerators, times(0)).get(any(String.class));
        verify(flowDisplayRepository, times(0)).addFlowDisplay(any(FlowDisplayAddRequest.class));
    }

    @Test
    public void receiveEventsNewFlowState() {
        displayFlowListen.receiveEvents(mock(FlowContext.class), FlowState.NEW);
        verify(displayGenerators, times(1)).get(any());
        verify(flowDisplayRepository, times(0)).addFlowDisplay(any(FlowDisplayAddRequest.class));
    }

    @Test
    public void receiveEventsFlowState() {
        displayFlowListen.receiveEvents(mock(FlowContext.class), FlowState.PRE_CHECK_ACCEPTED);
        verify(displayGenerators, times(1)).get(any());
    }
}