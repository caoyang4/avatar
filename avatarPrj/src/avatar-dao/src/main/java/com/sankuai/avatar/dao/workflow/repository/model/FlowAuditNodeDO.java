package com.sankuai.avatar.dao.workflow.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 流程审核节点表
 *
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow_audit_node")
public class FlowAuditNodeDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
