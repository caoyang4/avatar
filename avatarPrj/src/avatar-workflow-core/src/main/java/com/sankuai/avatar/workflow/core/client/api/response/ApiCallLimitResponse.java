package com.sankuai.avatar.workflow.core.client.api.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class ApiCallLimitResponse {
    /**
     * 是否已被锁定
     */
    boolean locked;

    /**
     * 提示信息
     */
    String message;
}
