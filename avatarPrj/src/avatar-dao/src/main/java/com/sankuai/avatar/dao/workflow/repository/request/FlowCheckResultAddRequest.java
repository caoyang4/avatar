package com.sankuai.avatar.dao.workflow.repository.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowCheckResultAddRequest {
    /**
     * 主键ID
     */
    private Integer flowId;

    /**
     * 流程预检输出信息
     */
    private String checkerResult;
}
