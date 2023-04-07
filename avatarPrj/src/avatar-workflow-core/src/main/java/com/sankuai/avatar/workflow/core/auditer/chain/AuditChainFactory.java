package com.sankuai.avatar.workflow.core.auditer.chain;

import com.sankuai.avatar.workflow.core.auditer.chain.builder.DefaultAuditChain;
import com.sankuai.avatar.workflow.core.auditer.chain.builder.McmAuditChain;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 审核链工厂类，支持本地审核链和MCM审核链
 *
 * @author zhaozhifan02
 */
@Component
public class AuditChainFactory {

    @Autowired
    private DefaultAuditChain defaultAuditChain;

    @Autowired
    private McmAuditChain mcmAuditChain;

    /**
     * 获取审核链
     *
     * @param auditChainType {@link FlowAuditChainType}
     * @return {@link FlowAuditChain}
     */
    public FlowAuditChain getFlowAuditChain(FlowContext flowContext, FlowAuditChainType auditChainType) {
        if (FlowAuditChainType.MCM.equals(auditChainType)) {
            return mcmAuditChain.build(flowContext);
        }
        return defaultAuditChain.build(flowContext);
    }
}
