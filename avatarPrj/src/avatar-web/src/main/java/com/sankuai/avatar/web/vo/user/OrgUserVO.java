package com.sankuai.avatar.web.vo.user;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-01 17:38
 */
@Data
public class OrgUserVO {
    /**
     * mis号
     */
    private String mis;
    /**
     * 中文名
     */
    private String name;
    /**
     * 人员角色
     */
    private String role;

    /**
     * 在职 or 离职
     */
    private String jobStatus;

    /**
     * 上级 leader
     */
    private String leader;
    /**
     * dx头像
     */
    private String avatarUrl;
    /**
     * 人员所在部门编号
     */
    private String orgId;
    /**
     * org部门全路径编号
     */
    private String org;
    /**
     * 组织架构全路径名称
     */
    private String orgName;
    /**
     * 来源
     */
    private String source;
}
