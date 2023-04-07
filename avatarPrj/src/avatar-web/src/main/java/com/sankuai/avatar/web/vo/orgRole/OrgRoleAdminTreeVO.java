package com.sankuai.avatar.web.vo.orgRole;

import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-11 21:29
 */
@Data
public class OrgRoleAdminTreeVO {

    /**
     * orgId
     */
    private String orgId;

    /**
     * org名称
     */
    private String orgName;

    /**
     * 角色类型：运维负责人、测试负责人
     */
    private String role;

    /**
     * 角色 mis 号
     */
    private String roleUsers;

    /**
     * 子组织节点
     */
    private List<OrgRoleAdminVO> children;

    /**
     * 父组织节点
     */
    private OrgRoleAdminVO ancestor;

    /**
     * 当前自身节点
     */
    private OrgRoleAdminVO current;

    /**
     * org大象群信息
     */
    private List<DxGroupVO> groupList;

    /**
     * 信息来源说明
     */
    private String desc;
}
