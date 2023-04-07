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
public class PermissionEntity {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 流程模板名称
     */
    private String templateName;

    /**
     * 用户角色
     * rd、sre、rd_admin、group、leader、any
     */
    private String role;

    /**
     * 是否有流程的发起权限
     * Y-是
     * N-否
     */
    private String isApply;

    /**
     * 是否有流程的审核权限
     * Y-是
     * N-否
     */
    private String isChecker;

    /**
     * 成员组
     */
    private String groupList;
}
