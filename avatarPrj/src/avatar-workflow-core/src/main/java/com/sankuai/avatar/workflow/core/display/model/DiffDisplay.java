package com.sankuai.avatar.workflow.core.display.model;

import lombok.Builder;
import lombok.Data;

/**
 * 变更差异对比
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class DiffDisplay {
    /**
     * 旧值
     */
    private String oldValue;

    /**
     * 新值
     */
    private String newValue;

}
