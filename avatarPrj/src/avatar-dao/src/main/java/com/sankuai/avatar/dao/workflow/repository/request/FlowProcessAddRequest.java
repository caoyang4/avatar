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
public class FlowProcessAddRequest {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 流程UUID
     */
    private String flowUuid;

    /**
     * 序号
     */
    private Integer seq;

    /**
     * process名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer state;
}
