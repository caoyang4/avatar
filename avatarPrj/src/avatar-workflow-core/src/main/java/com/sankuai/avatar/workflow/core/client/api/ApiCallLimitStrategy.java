package com.sankuai.avatar.workflow.core.client.api;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Builder
@Data
public class ApiCallLimitStrategy {
    /**
     * 时间间隔
     */
    private int interval;

    /**
     * 调用频率
     */
    private int frequency;

}
