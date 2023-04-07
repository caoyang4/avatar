package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.dao.es.FlowSearchEsRepository;
import com.sankuai.avatar.dao.es.request.FlowDataUpdateRequest;
import com.sankuai.avatar.dao.es.request.FlowSearchUpdateRequest;
import com.sankuai.avatar.workflow.server.dto.es.EsFlowType;
import com.sankuai.avatar.workflow.server.dto.request.flow.FlowUpdateRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FlowSearchUpdateHandlerTest {

    @Mock
    private FlowSearchEsRepository flowSearchEsRepository;

    @InjectMocks
    private FlowSearchUpdateHandler flowSearchUpdateHandler;

    @Test
    public void testGetType() {
        Assert.assertEquals(EsFlowType.SEARCH, flowSearchUpdateHandler.getType());
    }

    @Test
    public void testUpdate() {
        FlowUpdateRequest flowUpdateRequest = FlowUpdateRequest.builder()
                .data(FlowDataUpdateRequest.builder().build()).build();
        when(flowSearchEsRepository.update(any(FlowSearchUpdateRequest.class))).thenReturn(true);
        boolean status = flowSearchUpdateHandler.update(flowUpdateRequest);
        Assert.assertTrue(status);
        verify(flowSearchEsRepository).update(any(FlowSearchUpdateRequest.class));
    }
}