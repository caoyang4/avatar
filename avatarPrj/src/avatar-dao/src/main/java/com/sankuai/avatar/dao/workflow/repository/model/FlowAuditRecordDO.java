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
 * 审核记录
 *
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow_audit_record")
public class FlowAuditRecordDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 审核节点ID
     */
    private Integer auditNodeId;

    /**
     * 审核操作
     * 1-通过
     * 2-驳回
     * 3-撤销
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
