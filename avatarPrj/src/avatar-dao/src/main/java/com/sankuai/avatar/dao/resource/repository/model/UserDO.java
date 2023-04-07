package com.sankuai.avatar.dao.resource.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * user 人员数据对象，包括中文姓名，上级 leader，部门，头像，mis，角色等信息
 * @author caoyang
 * @create 2022-10-19 11:16
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "org_user")
public class UserDO {

    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC", strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 人员编号
     */
    private Integer number;

    /**
     * 人员中文姓名
     */
    private String name;

    /**
     * dx登录 mis
     */
    private String loginName;

    /**
     * 人员来源分为 MT(美团)，DP(点评)
     */
    private String source;

    /**
     * 部门组织名称
     */
    private String organization;

    /**
     * 人员角色
     */
    private String role;

    /**
     * 上级 leader
     */
    private String leader;

    /**
     * 用户图像
     */
    private String userImage;

    /**
     * 人员所在部门编号
     */
    private String orgId;

    /**
     * org部门全路径编号
     */
    private String orgPath;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 登录时间
     */
    private Date loginTime;

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

    /**
     * 在职状态：在职/离职
     */
    @Transient
    private String jobStatus;
}