package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.dao.es.FlowDataEsRepository;
import com.sankuai.avatar.dao.es.request.FlowDataUpdateRequest;
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
public class FlowDataUpdateHandlerTest {

    @Mock
    private FlowDataEsRepository flowDataEsRepository;

    @InjectMocks
    private FlowDataUpdateHandler flowDataUpdateHandler;

    @Test
    public void testGetType() {
        Assert.assertEquals(EsFlowType.STATISTICS, flowDataUpdateHandler.getType());
    }

    @Test
    public void testUpdate() {
        FlowUpdateRequest flowUpdateRequest = FlowUpdateRequest.builder()
                .data(FlowDataUpdateRequest.builder().build()).build();
        when(flowDataEsRepository.update(any(FlowDataUpdateRequest.class))).thenReturn(true);
        boolean status = flowDataUpdateHandler.update(flowUpdateRequest);
        Assert.assertTrue(status);
        verify(flowDataEsRepository).update(any(FlowDataUpdateRequest.class));
    }
}