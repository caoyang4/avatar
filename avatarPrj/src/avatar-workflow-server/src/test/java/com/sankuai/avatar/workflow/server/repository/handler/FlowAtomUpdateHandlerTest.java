package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.dao.es.FlowAtomEsRepository;
import com.sankuai.avatar.dao.es.request.FlowAtomUpdateRequest;
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
public class FlowAtomUpdateHandlerTest {

    @Mock
    private FlowAtomEsRepository flowAtomEsRepository;

    @InjectMocks
    private FlowAtomUpdateHandler flowAtomUpdateHandler;

    @Test
    public void testGetType() {
        Assert.assertEquals(EsFlowType.ATOM, flowAtomUpdateHandler.getType());
    }

    @Test
    public void testUpdate() {
        FlowUpdateRequest flowUpdateRequest = FlowUpdateRequest.builder()
                .data(FlowAtomUpdateRequest.builder().build()).build();
        when(flowAtomEsRepository.update(any(FlowAtomUpdateRequest.class))).thenReturn(true);
        boolean status = flowAtomUpdateHandler.update(flowUpdateRequest);
        Assert.assertTrue(status);
        verify(flowAtomEsRepository).update(any(FlowAtomUpdateRequest.class));
    }
}