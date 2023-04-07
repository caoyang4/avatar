package com.sankuai.avatar.workflow.core.auditer.chain.builder;

import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class DefaultAuditChainTest {

    private DefaultAuditChain defaultAuditChain;

    public DefaultAuditChainTest() {
        this.defaultAuditChain = new DefaultAuditChain();
    }

    @Test
    public void testBuild() {
        FlowContext flowContext = FlowContext.builder().createUser("zhaozhifan02").build();
        ReflectionTestUtils.setField(defaultAuditChain, "avatarSuperAdmin", new String[]{"zhaozhifan02"});
        FlowAuditChain flowAuditChain = defaultAuditChain.build(flowContext);
        Assert.assertNotNull(flowAuditChain);
    }
}