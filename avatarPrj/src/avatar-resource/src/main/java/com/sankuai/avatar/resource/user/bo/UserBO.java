package com.sankuai.avatar.resource.user.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User 人员信息 BO对象
 * @author caoyang
 * @create 2022-10-20 14:32
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBO {

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

}
