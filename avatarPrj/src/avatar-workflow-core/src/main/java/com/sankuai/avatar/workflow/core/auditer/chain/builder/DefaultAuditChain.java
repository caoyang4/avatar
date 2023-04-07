package com.sankuai.avatar.workflow.core.auditer.chain.builder;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.workflow.core.auditer.chain.*;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 默认审核链实现类，当MCM异常时使用默认审核链
 *
 * @author zhaozhifan02
 */
@Component
public class DefaultAuditChain implements AuditChain {

    /**
     * 默认兜底审核标题
     */
    private static final String DEFAULT_AUDIT_TITLE = "默认兜底审核";

    /**
     * 超级管理员
     */
    @MdpConfig("AVATAR_ADMIN:[]")
    private String[] avatarSuperAdmin;


    @Override
    public FlowAuditChain build(FlowContext flowContext) {
        return FlowAuditChain.builder()
                .shouldIgnore(false)
                .state(AuditState.AUDITING)
                .auditors(getDefaultAuditor(flowContext))
                .chainType(FlowAuditChainType.DEFAULT)
                .chainNodes(buildDefaultAuditChainNode(flowContext))
                .build();
    }

    /**
     * 构建默认审核节点列表
     *
     * @param flowContext {@link FlowContext}
     * @return {@link FlowAuditChainNode}
     */
    private List<FlowAuditChainNode> buildDefaultAuditChainNode(FlowContext flowContext) {
        FlowAuditChainNode flowAuditChainNode = FlowAuditChainNode.builder()
                .name(DEFAULT_AUDIT_TITLE)
                .state(AuditState.AUDITING)
                .auditors(getDefaultAuditor(flowContext))
                .approveType(AuditApproveType.OR)
                .build();
        return Collections.singletonList(flowAuditChainNode);
    }

    /**
     * 获取默认兜底审核人
     *
     * @param flowContext {@link FlowContext}
     * @return List<String>
     */
    private List<String> getDefaultAuditor(FlowContext flowContext) {
        List<String> auditors = new ArrayList<>(Arrays.asList(avatarSuperAdmin));
        if (!auditors.contains(flowContext.getCreateUser())) {
            auditors.add(flowContext.getCreateUser());
        }
        return auditors;
    }
}
