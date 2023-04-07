package com.sankuai.avatar.workflow.core.auditer;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.workflow.core.auditer.chain.AuditChainFactory;
import com.sankuai.avatar.workflow.core.auditer.chain.AuditState;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChain;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainType;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowUserSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 审核处理器：发起MCM审核、获取审核链、审核通过、审核驳回、审核撤销
 *
 * @author zhaozhifan02
 */
@Component
public class AuditHandler {

    @Autowired
    private AuditChainFactory auditChainFactory;

    @MdpConfig("AUDIT_APPKEY_USER:[]")
    private static String[] auditAppKey;

    /**
     * 发起审核，获取审核链
     *
     * @param flowContext {@link FlowContext}
     * @return {@link AuditResult}
     */
    public AuditResult audit(FlowContext flowContext) {
        AuditResult auditResult = AuditResult.builder().shouldIgnore(false).build();
        // 是否可跳过审核
        if (isAuditShouldIgnore(flowContext)) {
            auditResult.setShouldIgnore(true);
            return auditResult;
        }

        // 先构建MCM审核链
        FlowAuditChain flowAuditChain = auditChainFactory.getFlowAuditChain(flowContext, FlowAuditChainType.MCM);
        if (flowAuditChain == null) {
            // 构建本地默认审核链
            flowAuditChain = auditChainFactory.getFlowAuditChain(flowContext, FlowAuditChainType.DEFAULT);
        }
        auditResult.setShouldIgnore(flowAuditChain.getShouldIgnore());
        auditResult.setAuditChain(flowAuditChain);
        auditResult.setAuditState(AuditState.AUDITING);
        return auditResult;
    }


    /**
     * 是否可忽略审核: api 用户、加白逻辑等
     *
     * @param flowContext {@link FlowContext}
     * @return boolean
     */
    private boolean isAuditShouldIgnore(FlowContext flowContext) {
        // 需要审核的调用方
        if (Arrays.asList(auditAppKey).contains(flowContext.getCreateUser())) {
            return false;
        }
        // API 用户免审
        return FlowUserSource.APPKEY.equals(flowContext.getCreateUserSource());
    }
}
