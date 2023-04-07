package com.sankuai.avatar.workflow.server.repository.handler;

import com.sankuai.avatar.dao.es.FlowAuditEsRepository;
import com.sankuai.avatar.dao.es.request.FlowAuditUpdateRequest;
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
public class FlowAuditUpdateHandlerTest {

    @Mock
    private FlowAuditEsRepository flowAuditEsRepository;

    @InjectMocks
    private FlowAuditUpdateHandler flowAuditUpdateHandler;

    @Test
    public void testGetType() {
        Assert.assertEquals(EsFlowType.AUDIT, flowAuditUpdateHandler.getType());
    }

    @Test
    public void testUpdate() {
        FlowUpdateRequest flowUpdateRequest = FlowUpdateRequest.builder()
                .data(FlowAuditUpdateRequest.builder().build()).build();
        when(flowAuditEsRepository.update(any(FlowAuditUpdateRequest.class))).thenReturn(true);
        boolean status = flowAuditUpdateHandler.update(flowUpdateRequest);
        Assert.assertTrue(status);
        verify(flowAuditEsRepository).update(any(FlowAuditUpdateRequest.class));
    }
}