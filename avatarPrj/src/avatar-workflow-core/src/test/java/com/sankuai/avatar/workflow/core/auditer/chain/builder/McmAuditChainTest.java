package com.sankuai.avatar.workflow.core.auditer.chain.builder;

import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.mcm.McmClient;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreAuditResponse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class McmAuditChainTest {

    @Mock
    private McmClient mcmClient;

    @InjectMocks
    private McmAuditChain mcmAuditChain;

    private FlowContext flowContext;

    @Before
    public void setUp() {
        flowContext = FlowContext.builder().templateName("userUnlock").build();
    }

    @Test
    public void testBuildShouldIgnoreTrue() {
        McmPreAuditResponse mcmPreAuditResponse = new McmPreAuditResponse();
        mcmPreAuditResponse.setShouldIgnore(true);
        when(mcmClient.audit(any(McmEventRequest.class))).thenReturn(mcmPreAuditResponse);
        FlowAuditChain flowAuditChain = mcmAuditChain.build(flowContext);
        Assert.assertTrue(flowAuditChain.getShouldIgnore());
    }

    @Test
    public void testBuildShouldIgnoreFalse() {
        McmPreAuditResponse mcmPreAuditResponse = new McmPreAuditResponse();
        mcmPreAuditResponse.setShouldIgnore(false);
        when(mcmClient.audit(any(McmEventRequest.class))).thenReturn(mcmPreAuditResponse);
        FlowAuditChain flowAuditChain = mcmAuditChain.build(flowContext);
        Assert.assertFalse(flowAuditChain.getShouldIgnore());
    }
}