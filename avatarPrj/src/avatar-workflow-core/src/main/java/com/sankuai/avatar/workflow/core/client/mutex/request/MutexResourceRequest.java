package com.sankuai.avatar.workflow.core.client.mutex.request;

import com.sankuai.avatar.workflow.core.context.FlowContext;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class MutexResourceRequest {
    /**
     * 流程上下文
     */
    private FlowContext flowContext;
}
