package com.sankuai.avatar.workflow.core.client.mutex.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class ResourceInfo {
    /**
     * 资源当前操作
     */
    private ResourceOperation operation;

    /**
     * 关联流程UUID
     */
    private String uuid;

}
