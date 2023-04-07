package com.sankuai.avatar.workflow.core.auditer;

import com.sankuai.avatar.workflow.core.auditer.chain.AuditChainFactory;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainType;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowUserSource;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class AuditHandlerTest {

    @Mock
    private AuditChainFactory auditChainFactory;

    @InjectMocks
    private AuditHandler auditHandler;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(auditHandler, "auditAppKey", new String[]{});
    }


    @Test
    public void testAuditShouldIgnore() {
        FlowContext flowContext = FlowContext.builder().createUserSource(FlowUserSource.APPKEY).build();
        AuditResult auditResult = auditHandler.audit(flowContext);
        Assert.assertTrue(auditResult.isShouldIgnore());
        verify(auditChainFactory, never()).getFlowAuditChain(flowContext, FlowAuditChainType.MCM);
    }

    @Test
    public void testAuditMcmChain() {
        FlowAuditChain flowAuditChain = FlowAuditChain.builder().shouldIgnore(true).build();
        when(auditChainFactory.getFlowAuditChain(any(FlowContext.class),
                any())).thenReturn(flowAuditChain);

        AuditResult auditResult = auditHandler.audit(mock(FlowContext.class));
        Assert.assertTrue(auditResult.isShouldIgnore());
    }

    @Test
    public void testAuditDefaultChain() {
        when(auditChainFactory.getFlowAuditChain(any(FlowContext.class),
                any())).thenReturn(null, FlowAuditChain.builder().shouldIgnore(true).build());
        AuditResult auditResult = auditHandler.audit(mock(FlowContext.class));
        Assert.assertTrue(auditResult.isShouldIgnore());
        verify(auditChainFactory, times(2)).getFlowAuditChain(any(FlowContext.class),
                any(FlowAuditChainType.class));
    }
}