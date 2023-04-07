package com.sankuai.avatar.workflow.core.auditer.chain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 审核人操作信息
 *
 * @author zhaozhifan02
 */
@Builder
@Data
public class FlowAuditorOperation {
    /**
     * 审核节点ID
     */
    private Integer auditNodeId;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 备注
     */
    private String comment;

    /**
     * 审核动作
     */
    private AuditOperationType operationType;

    /**
     * 操作时间
     */
    private Date operateTime;
}
