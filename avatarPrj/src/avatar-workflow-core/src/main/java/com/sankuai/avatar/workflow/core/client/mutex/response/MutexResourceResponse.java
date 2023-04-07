package com.sankuai.avatar.workflow.core.client.mutex.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class MutexResourceResponse {
    /**
     * 是否已被锁定
     */
    boolean locked;

    /**
     * 提示信息
     */
    String message;
}
