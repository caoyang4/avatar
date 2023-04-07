package com.sankuai.avatar.dao.workflow.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowAtomRecordEntity {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * Atom名称
     */
    private String name;

    /**
     * 状态
     */
    private String status;

    /**
     * 输入
     */
    private String input;

    /**
     * 输出
     */
    private String output;

    /**
     * 执行耗时
     */
    private Integer duration;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 异常
     */
    private String exception;
}
