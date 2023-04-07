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
 * 流程 Atom 执行上下文，记录 Atom 任务执行情况
 *
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow_atom_context")
public class FlowAtomContextDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 执行序号，从小到大依次执行，序号相同则并行执行
     */
    private Integer seq;

    /**
     * Atom名称
     */
    private String atomName;

    /**
     * 状态
     */
    private String status;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
