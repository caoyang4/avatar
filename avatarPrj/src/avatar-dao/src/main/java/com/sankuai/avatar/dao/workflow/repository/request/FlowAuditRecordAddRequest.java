package com.sankuai.avatar.dao.workflow.repository.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 流程审核操作记录创建请求对象
 *
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowAuditRecordAddRequest {
    /**
     * 主键 id
     */
    private Integer id;

    /**
     * 审核节点ID
     */
    private Integer auditNodeId;

    /**
     * 审核操作
     * 1-通过
     * 2-驳回
     */
    private Integer auditOperation;

    /**
     * 审核操作类型名称
     */
    private String auditOperationName;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 备注
     */
    private String comment;

    /**
     * 审核操作时间
     */
    private Date operateTime;
}
