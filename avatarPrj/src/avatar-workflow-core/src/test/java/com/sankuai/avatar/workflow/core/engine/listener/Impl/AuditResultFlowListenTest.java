package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.dao.workflow.repository.FlowAuditNodeRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowAuditRecordRepository;
import com.sankuai.avatar.workflow.core.auditer.AuditResult;
import com.sankuai.avatar.workflow.core.auditer.chain.*;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import static org.mockito.Mockito.*;

import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AuditResultFlowListenTest {

    @Mock
    private FlowAuditNodeRepository flowAuditNodeRepository;

    @Mock
    private FlowAuditRecordRepository flowAuditRecordRepository;

    @InjectMocks
    private AuditResultFlowListen auditResultFlowListen;

    @Mock
    private FlowContext flowContext;

    @Mock
    private Response response;

    @Before
    public void setUp() throws Exception {
        ProcessContext processContext = mock(ProcessContext.class);
        when(processContext.getResponse()).thenReturn(this.response);

        when(flowContext.getId()).thenReturn(1);
        when(flowContext.getCurrentProcessContext()).thenReturn(processContext);
    }

    /**
     * 审核阶段不存在
     */
    @Test
    public void testReceiveEventsUpdateFlowAuditNode() {
        FlowAuditChainNode flowAuditChainNode = FlowAuditChainNode.builder()
                .name("xx")
                .seq(1)
                .auditors(Arrays.asList("xx", "dd"))
                .state(AuditState.AUDITING)
                .approveType(AuditApproveType.OR).build();

        FlowAuditChain mockFlowAuditChain = mock(FlowAuditChain.class);
        when(mockFlowAuditChain.getChainType()).thenReturn(FlowAuditChainType.MCM);
        when(mockFlowAuditChain.getChainNodes()).thenReturn(Arrays.asList(flowAuditChainNode, flowAuditChainNode));

        AuditResult mockAuditResult = mock(AuditResult.class);
        when(mockAuditResult.getAuditChain()).thenReturn(mockFlowAuditChain);

        when(this.response.getResult(AuditResult.class)).thenReturn(mockAuditResult);

        //do event
        auditResultFlowListen.receiveEvents(flowContext, FlowState.AUDITING);


        verify(flowAuditNodeRepository, times(1)).queryAuditNode(anyInt());
        verify(flowAuditNodeRepository, times(2)).addAuditNode(any());
    }


    /**
     * 更新审核记录
     */
    @Test
    public void testReceiveEventsUpdateFlowAuditRecord() {
        FlowAuditorOperation auditorOperation = FlowAuditorOperation.builder()
                .auditNodeId(1)
                .operationType(AuditOperationType.ACCEPT)
                .auditor("xx")
                .comment("xx")
                .build();
        AuditResult mockAuditResult = mock(AuditResult.class);
        when(mockAuditResult.getAuditState()).thenReturn(AuditState.ACCEPTED);
        when(mockAuditResult.getOperation()).thenReturn(auditorOperation);

        when(this.response.getResult(AuditResult.class)).thenReturn(mockAuditResult);
        when(flowAuditNodeRepository.queryAuditNode(anyInt())).thenReturn(mock(List.class));

        //do event
        auditResultFlowListen.receiveEvents(flowContext, FlowState.AUDIT_ACCEPTED);

        verify(flowAuditNodeRepository, times(1)).queryAuditNode(anyInt());
        verify(flowAuditNodeRepository, times(1)).updateAuditNode(any());
        verify(flowAuditRecordRepository, times(1)).addAuditRecord(any());
    }
}