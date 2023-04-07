package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.dao.es.FlowSearchEsRepository;
import com.sankuai.avatar.dao.es.request.FlowSearchUpdateRequest;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EsStorageListenTest {
    @Mock
    private FlowSearchEsRepository flowSearchEsRepository;

    @InjectMocks
    private EsStorageListen esStorageListen;

    @Test
    public void receiveEventsNullFlowContext() {
        esStorageListen.receiveEvents(null, FlowState.NEW);
        verify(flowSearchEsRepository, times(0)).update(any(FlowSearchUpdateRequest.class));
    }

    @Test
    public void receiveEventsNullFlowState() {
        esStorageListen.receiveEvents(mock(FlowContext.class), null);
        verify(flowSearchEsRepository, times(0)).update(any(FlowSearchUpdateRequest.class));
    }

    @Test
    public void receiveEvents() {
        FlowContext flowContext = FlowContext.builder().flowState(FlowState.NEW).build();
        esStorageListen.receiveEvents(flowContext, FlowState.NEW);
        verify(flowSearchEsRepository, times(1)).update(any(FlowSearchUpdateRequest.class));
    }
}