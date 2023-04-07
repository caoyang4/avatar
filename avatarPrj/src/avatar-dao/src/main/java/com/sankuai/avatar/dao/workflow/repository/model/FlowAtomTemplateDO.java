package com.sankuai.avatar.dao.workflow.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 流程Atom任务模板，定义编排结构
 *
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow_atom_template")
public class FlowAtomTemplateDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 执行序号，从小到大依次执行，序号相同则并行执行
     */
    private Integer seq;

    /**
     * Atom名称
     */
    private String atomName;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 描述信息
     */
    private String description;
}
