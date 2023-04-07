package com.sankuai.avatar.workflow.core.display.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Data
@Builder
public class OutputDisplay {
    /**
     * 输出数据项
     */
    private List<Object> items;

    /**
     * 输出表单表头字段
     */
    private List<String> rows;
}
