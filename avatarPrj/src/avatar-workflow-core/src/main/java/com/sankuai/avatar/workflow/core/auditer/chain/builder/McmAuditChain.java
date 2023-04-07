package com.sankuai.avatar.workflow.core.auditer.chain.builder;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainType;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.transfer.FlowContextTransfer;
import com.sankuai.avatar.workflow.core.mcm.McmClient;
import com.sankuai.avatar.workflow.core.mcm.McmEventContext;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import com.sankuai.avatar.workflow.core.mcm.response.McmPreAuditResponse;
import com.sankuai.avatar.workflow.core.mcm.transfer.McmTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MCM 审核链实现类
 *
 * @author zhaozhifan02
 */
@Slf4j
@Component
public class McmAuditChain implements AuditChain {

    private McmClient mcmClient;

    @Autowired
    public McmAuditChain(McmClient mcmClient) {
        this.mcmClient = mcmClient;
    }

    @Override
    public FlowAuditChain build(FlowContext flowContext) {
        McmPreAuditResponse auditResponse = null;
        Transaction transaction = Cat.newTransaction("McmAudit", flowContext.getUuid());
        try {
            auditResponse = mcmClient.audit(getMcmEventRequest(flowContext));
            transaction.setSuccessStatus();
        } catch (Exception e) {
            transaction.setStatus(e);
            log.error("Mcm build flowAuditChain catch error", e);
        } finally {
            transaction.complete();
        }
        if (auditResponse == null) {
            return null;
        }
        if (auditResponse.getAuditChain() == null) {
            // 未配置MCM审核链
            return FlowAuditChain.builder().shouldIgnore(auditResponse.getShouldIgnore()).build();
        }
        FlowAuditChain flowAuditChain = McmTransfer.INSTANCE.toFlowAuditChain(auditResponse.getAuditChain());
        flowAuditChain.setChainType(FlowAuditChainType.MCM);
        flowAuditChain.setShouldIgnore(auditResponse.getShouldIgnore());
        return flowAuditChain;
    }

    /**
     * 获取MCM事件请求
     *
     * @param flowContext {@link FlowContext}
     * @return {@link McmEventRequest}
     */
    private McmEventRequest getMcmEventRequest(FlowContext flowContext) {
        McmEventContext eventContext = FlowContextTransfer.INSTANCE.toMcmEventContext(flowContext);
        return McmEventRequest.builder()
                .evenName(eventContext.getEventName())
                .mcmEventContext(eventContext)
                .build();
    }
}
