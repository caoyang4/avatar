package com.sankuai.avatar.workflow.server.dto.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建流程参数
 *
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateRequestDTO {
    /**
     * 模版名
     */
    String templateName;

    /**
     * 输入
     */
    String input;
}
