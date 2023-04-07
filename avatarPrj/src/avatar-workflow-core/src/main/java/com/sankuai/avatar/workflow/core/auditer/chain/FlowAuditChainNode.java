package com.sankuai.avatar.workflow.core.auditer.chain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 流程审核节点数据结构
 *
 * @author zhaozhifan02
 */
@Builder
@Data
public class FlowAuditChainNode {
    /**
     * ID
     */
    private Integer id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 序号
     */
    private Integer seq;

    /**
     * 审核状态
     */
    private AuditState state;

    /**
     * 审核类型
     */
    private AuditApproveType approveType;

    /**
     * 审核人
     */
    private List<String> auditors;

    /**
     * 审核操作记录
     */
    private List<FlowAuditorOperation> auditorOperations;
}
