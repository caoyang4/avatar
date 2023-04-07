package com.sankuai.avatar.dao.resource.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户与appkey关系的数据对象
 * @author caoyang
 * @create 2022-11-02 18:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "resource_appkey_favor")
public class UserRelationDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC", strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * dx登录 mis
     */
    private String loginName;

    /**
     * 标签
     */
    private String tag;

    /**
     * appkey服务名称
     */
    private String appkey;

    /**
     * 创建时间
     */
    @Column(name = "createtime")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "updatetime")
    private Date updateTime;
}
