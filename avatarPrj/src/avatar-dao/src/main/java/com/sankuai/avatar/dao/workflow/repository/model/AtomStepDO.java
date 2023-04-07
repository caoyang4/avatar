package com.sankuai.avatar.dao.workflow.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 老版本(Python)的 Atom 数据对象
 *
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow_atom_step")
public class AtomStepDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * UUID
     */
    private String uuid;

    /**
     * Atom 名称
     */
    private String name;

    /**
     * 中文名
     */
    private String cnName;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 超时时间
     */
    private Integer timeout;

}
