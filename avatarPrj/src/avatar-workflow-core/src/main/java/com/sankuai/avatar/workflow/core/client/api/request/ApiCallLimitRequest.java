package com.sankuai.avatar.workflow.core.client.api.request;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class ApiCallLimitRequest {
    /**
     * 流程上下文
     */
    private FlowContext flowContext;
}
