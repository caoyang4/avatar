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
public class FlowAtomContextUpdateRequest {
    /**
     * 主键 id
     */
    private Integer id;
    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 执行序号，从小到大依次执行，序号相同则并行执行
     */
    private Integer seq;

    /**
     * Atom名称
     */
    private String atomName;

    /**
     * 状态
     */
    private String status;
}
