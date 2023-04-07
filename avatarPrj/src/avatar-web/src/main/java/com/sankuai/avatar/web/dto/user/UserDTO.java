package com.sankuai.avatar.web.dto.user;

import lombok.Data;

import java.util.Date;

/**
 * 用户 DTO 对象
 * @author caoyang
 * @create 2022-11-01 15:21
 */
@Data
public class UserDTO {

    private Integer id;

    /**
     * 人员编号
     */
    private Integer number;

    /**
     * dx登录 mis
     */
    private String mis;

    /**
     * 人员中文姓名
     */
    private String name;

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
     * 在职 或 离职
     */
    private String jobStatus;

    /**
     * 用户图像
     */
    private String avatarUrl;

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
}
