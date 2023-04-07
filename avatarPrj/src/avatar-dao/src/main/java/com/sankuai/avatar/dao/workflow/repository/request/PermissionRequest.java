package com.sankuai.avatar.dao.workflow.repository.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class PermissionRequest {
    /**
     * 流程模板名称
     */
    private String templateName;

    /**
     * 用户角色
     * rd、sre、rd_admin、group、leader
     */
    private String role;

    /**
     * 是否有流程的发起权限
     * Y-是
     * N-否
     */
    private String isApply;
}
