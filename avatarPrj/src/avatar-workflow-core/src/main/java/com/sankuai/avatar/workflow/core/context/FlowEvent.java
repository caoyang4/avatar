package com.sankuai.avatar.workflow.core.context;

import lombok.Builder;
import lombok.Data;

/**
 * 上报CT事件信息
 *
 * @author zhaozhifan02
 */
@Builder
@Data
public class FlowEvent {
    /**
     * 源站域名
     */
    private String sourceDomain;

    /**
     * 源IP
     */
    private String sourceIp;
}
