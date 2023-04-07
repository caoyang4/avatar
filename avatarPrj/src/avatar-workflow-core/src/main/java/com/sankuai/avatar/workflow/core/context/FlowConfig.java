package com.sankuai.avatar.workflow.core.context;

import com.sankuai.avatar.workflow.core.context.config.FlowMutexResource;
import com.sankuai.avatar.workflow.core.context.config.FlowSwitch;
import lombok.Builder;
import lombok.Data;

/**
 * 流程配置信息：资源锁开关、资源锁字段
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class FlowConfig {
    /**
     * 流程互斥锁资源
     */
    FlowMutexResource mutexResource;

    /**
     * 流程开关
     */
    FlowSwitch switches;
}
