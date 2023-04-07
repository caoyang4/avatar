package com.sankuai.avatar.dao.workflow.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 流程展示信息
 *
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow_display")
public class FlowDisplayDO {
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
     * 流程申请信息
     */
    private String input;

    /**
     * 流程输出信息:比如申请机器列表、域名列表等
     */
    private String output;

    /**
     * 变更差异信息
     */
    private String diff;

    /**
     * 风险提示信息
     */
    private String text;
}
