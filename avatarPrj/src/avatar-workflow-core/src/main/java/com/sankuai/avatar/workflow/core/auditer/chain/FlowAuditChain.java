package com.sankuai.avatar.workflow.core.auditer.chain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 流程审核链数据结构
 *
 * @author zhaozhifan02
 */
@Builder
@Data
public class FlowAuditChain {
    /**
     * 是否可忽略
     */
    private Boolean shouldIgnore;

    /**
     * 审核状态
     */
    private AuditState state;

    /**
     * 当前审核人
     */
    private List<String> auditors;

    /**
     * 审核链节点
     */
    private List<FlowAuditChainNode> chainNodes;

    /**
     * 审核链类型
     */
    private FlowAuditChainType chainType;

    /**
     * 创建人
     */
    private String createUser;

}
