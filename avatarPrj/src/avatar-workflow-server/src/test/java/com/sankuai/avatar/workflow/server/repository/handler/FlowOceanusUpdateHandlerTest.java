package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.dao.es.FlowOceanusEsRepository;
import com.sankuai.avatar.dao.es.request.FlowDataUpdateRequest;
import com.sankuai.avatar.dao.es.request.FlowOceanusUpdateRequest;
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
public class FlowOceanusUpdateHandlerTest {

    @Mock
    private FlowOceanusEsRepository flowOceanusEsRepository;

    @InjectMocks
    private FlowOceanusUpdateHandler flowOceanusUpdateHandler;

    @Test
    public void testGetType() {
        Assert.assertEquals(EsFlowType.OCEANUS, flowOceanusUpdateHandler.getType());
    }

    @Test
    public void testUpdate() {
        FlowUpdateRequest flowUpdateRequest = FlowUpdateRequest.builder()
                .data(FlowDataUpdateRequest.builder().build()).build();
        when(flowOceanusEsRepository.update(any(FlowOceanusUpdateRequest.class))).thenReturn(true);
        boolean status = flowOceanusUpdateHandler.update(flowUpdateRequest);
        Assert.assertTrue(status);
        verify(flowOceanusEsRepository).update(any(FlowOceanusUpdateRequest.class));
    }
}