package com.sankuai.avatar.workflow.server.dto.es;

import lombok.Builder;
import lombok.Data;

/**
 * ES Flow 数据
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class EsFlowDTO {
    /**
     * 流程Id
     */
    private Integer id;

    /**
     * 流程类型
     */
    private String type;

    /**
     * 流程数据
     * Object 兼容多种 flow 数据格式
     */
    private Object data;
}
