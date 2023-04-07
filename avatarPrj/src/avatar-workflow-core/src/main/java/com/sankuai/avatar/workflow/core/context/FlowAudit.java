package com.sankuai.avatar.workflow.core.context;

import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainNode;
import com.sankuai.avatar.workflow.core.auditer.chain.FlowAuditChainType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 流程审核信息
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class FlowAudit {
    /**
     * 审批人
     */
    private List<String> auditor;

    /**
     * 审核链类型
     */
    private FlowAuditChainType auditChainType;

    /**
     * 审核节点
     */
    private List<FlowAuditChainNode> auditNode;
}
