package com.sankuai.avatar.dao.workflow.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowAtomTemplateEntity {
    /**
     * 主键 id
     */
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
