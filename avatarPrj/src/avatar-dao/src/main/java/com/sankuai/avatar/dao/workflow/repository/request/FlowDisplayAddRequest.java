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
public class FlowDisplayAddRequest {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 流程申请信息
     */
    private String input;

    /**
     * 流程输出信息:比如申请机器列表、域名列表等
     */
    private String output;

    /**
     * 变更差异信息
     */
    private String diff;

    /**
     * 风险提示信息
     */
    private String text;
}
