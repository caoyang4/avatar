package com.sankuai.avatar.workflow.core.client.lock.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class FlowLockResponse {
    /**
     * 是否已被锁定
     */
    boolean locked;

    /**
     * 提示信息
     */
    String message;
}
