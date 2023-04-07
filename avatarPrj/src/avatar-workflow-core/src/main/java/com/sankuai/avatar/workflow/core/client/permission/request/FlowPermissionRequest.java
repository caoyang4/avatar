package com.sankuai.avatar.workflow.core.client.permission.request;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class FlowPermissionRequest {
    /**
     * 流程上下文
     */
    private FlowContext flowContext;
}
