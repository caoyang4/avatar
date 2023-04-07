package com.sankuai.avatar.workflow.core.client.permission;

import com.sankuai.avatar.workflow.core.client.permission.request.FlowPermissionRequest;
import com.sankuai.avatar.workflow.core.client.permission.response.FlowPermissionResponse;

/**
 * 流程发起权限客户端
 *
 * @author zhaozhifan02
 */
public interface FlowPermissionClient {

    /**
     * 是否可跳过检查
     *
     * @param request 请求参数
     * @return boolean
     */
    boolean canSkip(FlowPermissionRequest request);

    /**
     * 权限校验
     *
     * @param request 请求参数
     * @return 校验结果
     */
    FlowPermissionResponse validate(FlowPermissionRequest request);

}
