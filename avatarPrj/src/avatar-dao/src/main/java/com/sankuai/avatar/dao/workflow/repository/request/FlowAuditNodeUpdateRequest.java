package com.sankuai.avatar.dao.workflow.repository.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 审核节点更新请求
 *
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowAuditNodeUpdateRequest {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 执行序号，从小到大依次执行
     */
    private Integer seq;

    /**
     * 审核类型，0-或签 1-会签
     */
    private Integer auditType;

    /**
     * 审核链类型
     * 0-本地生成兜底审核链
     * 1-MCM审核链
     */
    private Integer auditChainType;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态名称
     */
    private String stateName;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * process执行阶段ID
     */
    private Integer processId;

    /**
     * 流程ID
     */
    private Integer flowId;
}
