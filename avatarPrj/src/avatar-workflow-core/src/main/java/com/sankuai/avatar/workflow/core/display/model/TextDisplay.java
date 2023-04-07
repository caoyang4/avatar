package com.sankuai.avatar.workflow.core.display.model;

import lombok.Builder;
import lombok.Data;

/**
 * 审核风险提示表单展示
 *
 * @author zhaozhifan02
 */
@Data
@Builder
public class TextDisplay {
    /**
     * 风险内容
     */
    String content;

    /**
     * 风险提示等级
     */
    String level;
}
