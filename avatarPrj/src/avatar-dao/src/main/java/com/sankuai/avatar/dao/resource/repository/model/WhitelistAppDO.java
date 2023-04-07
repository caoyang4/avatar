package com.sankuai.avatar.dao.resource.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 白名单类型数据对象
 * @author caoyang
 * @create 2022-11-02 21:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "whitelist_type")
public class WhitelistAppDO {
    /**
     *  主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     *  白名单类型
     */
    private String app;

    /**
     *  白名单中文名称
     */
    private String cname;

    /**
     *  白名单说明
     */
    private String description;
}
