package com.sankuai.avatar.workflow.core.client.mutex;

import com.sankuai.avatar.workflow.core.input.FlowInput;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
@Builder
public class MutexResourceInfo {
    /**
     * 流程入参
     */
    FlowInput input;

    /**
     * 变更 appKey
     */
    String appKey;

    /**
     * 环境
     */
    String env;

    /**
     * 流程模板
     */
    String templateName;
}
