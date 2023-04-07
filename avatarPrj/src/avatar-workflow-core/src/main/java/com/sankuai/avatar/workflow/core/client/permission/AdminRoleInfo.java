package com.sankuai.avatar.workflow.core.client.permission;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Data
@Builder
public class AdminRoleInfo {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 用户列表
     */
    private List<String> users;

    /**
     * 默认权限
     */
    private boolean defaultPermission;
}
