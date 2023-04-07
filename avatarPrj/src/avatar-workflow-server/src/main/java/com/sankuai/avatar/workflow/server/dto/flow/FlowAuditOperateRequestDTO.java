package com.sankuai.avatar.workflow.server.dto.flow;

import lombok.Builder;
import lombok.Data;

/**
 * 流程审核操作请求数据
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class FlowAuditOperateRequestDTO {
    /**
     * 审核节点ID
     */
    private Integer auditNodeId;

    /**
     * 备注
     */
    private String comment;
}
