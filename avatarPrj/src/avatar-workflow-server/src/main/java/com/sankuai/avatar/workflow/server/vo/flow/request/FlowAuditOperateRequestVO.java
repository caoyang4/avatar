package com.sankuai.avatar.workflow.server.vo.flow.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 流程审核操作请求对象
 *
 * @author zhaozhifan02
 */
@Data
public class FlowAuditOperateRequestVO {

    /**
     * 审核节点ID
     */
    @NotNull(message = "审核节点ID不能为空")
    private Integer auditNodeId;

    /**
     * 备注
     */
    private String comment;
}
