package com.sankuai.avatar.web.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2022-11-01 16:19
 */
@Data
public class UserVO {
    private Integer id;

    /**
     * 人员编号
     */
    private Integer number;

    /**
     * dx登录 登录名称
     */
    private String loginName;

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
     * 在职 or 离职
     */
    private String jobStatus;

    /**
     * 注册时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    /**
     * 登录时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

}
