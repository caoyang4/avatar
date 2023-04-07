package com.sankuai.avatar.dao.workflow.repository.entity;

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
public class FlowEventEntity {
    /**
     * 主键 id
     */
    private Integer id;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 发起人
     */
    private String loginName;

    /**
     * 源站域名
     */
    private String sourceDomain;

    /**
     * 源IP
     */
    private String sourceIp;

    /**
     * 额外信息
     */
    private String extraInfo;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;
}
