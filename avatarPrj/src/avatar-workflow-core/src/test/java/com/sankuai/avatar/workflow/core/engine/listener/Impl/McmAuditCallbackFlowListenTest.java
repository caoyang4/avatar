package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.sankuai.avatar.workflow.core.auditer.AuditResult;
import com.sankuai.avatar.workflow.core.auditer.chain.AuditState;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainType;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.engine.process.ProcessContext;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.mcm.McmClient;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class McmAuditCallbackFlowListenTest {

    @Mock
    private McmClient mcmClient;

    @InjectMocks
    private McmAuditCallbackFlowListen mcmAuditCallbackFlowListen;

    @Test
    public void testReceiveEventsNotAuditFlowProcess() {
        FlowContext flowContext = FlowContext.builder()
                .templateName("userUnlock")
                .flowState(FlowState.AUDIT_IGNORE)
                .currentProcessContext(ProcessContext.builder().build())
                .build();

        //do event
        mcmAuditCallbackFlowListen.receiveEvents(flowContext, null);
        verify(mcmClient, never()).auditAccept(any(McmEventRequest.class));
    }

    @Test
    public void testReceiveEventsAuditIgnore() {
        AuditResult auditResult = AuditResult.builder()
                .auditChain(FlowAuditChain.builder().chainType(FlowAuditChainType.MCM).build())
                .build();
        FlowContext flowContext = FlowContext.builder()
                .templateName("userUnlock")
                .flowState(FlowState.AUDIT_IGNORE)
                .currentProcessContext(ProcessContext.builder().response(Response.of(FlowState.AUDIT_IGNORE, auditResult)).build())
                .build();

        //do event
        mcmAuditCallbackFlowListen.receiveEvents(flowContext, FlowState.AUDIT_IGNORE);

        verify(mcmClient, never()).auditAccept(any(McmEventRequest.class));
    }

    @Test
    public void testReceiveEventsAuditAccept() {
        AuditResult auditResult = AuditResult.builder()
                .auditState(AuditState.ACCEPTED)
                .auditChain(FlowAuditChain.builder().chainType(FlowAuditChainType.MCM).build())
                .build();
        FlowContext flowContext = FlowContext.builder()
                .templateName("userUnlock")
                .flowState(FlowState.AUDIT_ACCEPTED)
                .currentProcessContext(ProcessContext.builder().response(Response.of(FlowState.AUDIT_ACCEPTED, auditResult)).build())
                .build();

        //do event
        mcmAuditCallbackFlowListen.receiveEvents(flowContext, FlowState.AUDIT_ACCEPTED);
        verify(mcmClient).auditAccept(any(McmEventRequest.class));
        verify(mcmClient, never()).auditReject(any(McmEventRequest.class));
        verify(mcmClient, never()).auditCancel(any(McmEventRequest.class));

    }

    @Test
    public void testReceiveEventsAuditReject() {
        AuditResult auditResult = AuditResult.builder()
                .auditState(AuditState.REJECTED)
                .auditChain(FlowAuditChain.builder().chainType(FlowAuditChainType.MCM).build())
                .build();
        FlowContext flowContext = FlowContext.builder()
                .flowState(FlowState.AUDIT_REJECTED)
                .currentProcessContext(ProcessContext.builder().response(Response.of(FlowState.AUDIT_REJECTED, auditResult)).build())
                .templateName("userUnlock").build();

        //do event
        mcmAuditCallbackFlowListen.receiveEvents(flowContext, FlowState.AUDIT_REJECTED);
        verify(mcmClient).auditReject(any(McmEventRequest.class));
        verify(mcmClient, never()).auditAccept(any(McmEventRequest.class));
        verify(mcmClient, never()).auditCancel(any(McmEventRequest.class));
    }

    @Test
    public void testReceiveEventsAuditCancel() {
        AuditResult auditResult = AuditResult.builder()
                .auditState(AuditState.CANCELED)
                .auditChain(FlowAuditChain.builder().chainType(FlowAuditChainType.MCM).build())
                .build();
        FlowContext flowContext = FlowContext.builder()
                .flowState(FlowState.AUDIT_CANCELED)
                .currentProcessContext(ProcessContext.builder().response(Response.of(FlowState.AUDIT_CANCELED, auditResult)).build())
                .templateName("userUnlock").build();

        //do event
        mcmAuditCallbackFlowListen.receiveEvents(flowContext, FlowState.AUDIT_CANCELED);
        verify(mcmClient).auditCancel(any(McmEventRequest.class));
        verify(mcmClient, never()).auditAccept(any(McmEventRequest.class));
        verify(mcmClient, never()).auditReject(any(McmEventRequest.class));
    }
}