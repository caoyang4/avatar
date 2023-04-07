package com.sankuai.avatar.workflow.core.engine.listener.Impl;

import com.dianping.cat.Cat;
import com.sankuai.avatar.workflow.core.auditer.AuditResult;
import com.sankuai.avatar.workflow.core.auditer.chain.AuditState;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainType;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditorOperation;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowState;
import com.sankuai.avatar.workflow.core.context.transfer.FlowContextTransfer;
import com.sankuai.avatar.workflow.core.engine.listener.FlowListen;
import com.sankuai.avatar.workflow.core.engine.listener.ListenFlowState;
import com.sankuai.avatar.workflow.core.engine.process.response.Response;
import com.sankuai.avatar.workflow.core.mcm.McmClient;
import com.sankuai.avatar.workflow.core.mcm.McmEventContext;
import com.sankuai.avatar.workflow.core.mcm.request.McmEventRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mcm 审核事件回调监听器，异步更新 Mcm 审核链状态
 *
 * @author zhaozhifan02
 */
@Slf4j
@Component
@ListenFlowState({FlowState.AUDITING, FlowState.AUDIT_ACCEPTED, FlowState.AUDIT_REJECTED, FlowState.AUDIT_CANCELED})
public class McmAuditCallbackFlowListen implements FlowListen {

    @Autowired
    private McmClient mcmClient;

    @Override
    public void receiveEvents(FlowContext flowContext, FlowState flowState) {
        Response response = flowContext.getCurrentProcessContext().getResponse();
        if (response == null || response.getResult(AuditResult.class) == null) {
            return;
        }

        AuditResult auditResult = response.getResult(AuditResult.class);
        if (auditResult == null) {
            return;
        }
        if (FlowAuditChainType.MCM.equals(auditResult.getAuditChain().getChainType())) {
            // 回调更新 Mcm 审核链
            doCallback(flowContext, auditResult);
        }
    }

    /**
     * 回调MCM, 更新审核状态
     *
     * @param flowContext 流上下文
     * @param auditResult 审计结果
     */
    private void doCallback(FlowContext flowContext, AuditResult auditResult) {
        McmEventRequest request = getMcmEventRequest(flowContext, auditResult);
        AuditState auditState = auditResult.getAuditState();
        if (auditState == null) {
            return;
        }
        switch (auditState) {
            case ACCEPTED:
                doAuditAccept(request);
                break;
            case REJECTED:
                doAuditReject(request);
                break;
            case CANCELED:
                doAuditCancel(request);
                break;
            default:
                break;
        }
    }

    /**
     * 获取MCM事件请求
     *
     * @param flowContext {@link FlowContext}
     * @return {@link McmEventRequest}
     */
    private McmEventRequest getMcmEventRequest(FlowContext flowContext, AuditResult auditResult) {
        FlowAuditorOperation auditorOperation = auditResult.getOperation();
        McmEventContext eventContext = FlowContextTransfer.INSTANCE.toMcmEventContext(flowContext);
        if (auditorOperation != null && auditorOperation.getAuditor() != null) {
            eventContext.setOperator(auditorOperation.getAuditor());
        }
        return McmEventRequest.builder()
                .evenName(eventContext.getEventName())
                .mcmEventContext(eventContext)
                .build();
    }

    /**
     * MCM 审核通过
     *
     * @param request {@link McmEventRequest}
     */
    private void doAuditAccept(McmEventRequest request) {
        try {
            mcmClient.auditAccept(request);
        } catch (Exception e) {
            Cat.logError(e);
            log.error("Do Mcm AuditAccept catch error", e);
        }
    }

    /**
     * MCM 审核拒绝
     *
     * @param request {@link McmEventRequest}
     */
    private void doAuditReject(McmEventRequest request) {
        try {
            mcmClient.auditReject(request);
        } catch (Exception e) {
            Cat.logError(e);
            log.error("Do Mcm AuditReject catch error", e);
        }
    }

    /**
     * MCM 审核撤销
     *
     * @param request {@link McmEventRequest}
     */
    private void doAuditCancel(McmEventRequest request) {
        try {
            mcmClient.auditCancel(request);
        } catch (Exception e) {
            Cat.logError(e);
            log.error("Do Mcm AuditCancel catch error", e);
        }
    }
}
