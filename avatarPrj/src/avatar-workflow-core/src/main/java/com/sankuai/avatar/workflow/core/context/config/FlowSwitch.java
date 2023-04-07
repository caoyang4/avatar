package com.sankuai.avatar.workflow.core.context.config;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
@Builder
public class FlowSwitch {

    /**
     * 资源锁开关
     */
    private boolean mutexResource;
}
