package com.sankuai.avatar.workflow.core.client.permission.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class FlowPermissionResponse {

    /**
     * 是否有权限
     */
    boolean hasPermission;

    /**
     * 提示信息
     */
    String message;
}
