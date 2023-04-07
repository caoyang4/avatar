package com.sankuai.avatar.workflow.server.dto.request.flow;

import lombok.Builder;
import lombok.Data;

/**
 * 流程更新请求对象
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class FlowUpdateRequest {
    /**
     * 流程Id
     */
    private Integer id;

    /**
     * 流程类型
     */
    private String type;

    /**
     * 流程内容
     */
    private Object data;
}
