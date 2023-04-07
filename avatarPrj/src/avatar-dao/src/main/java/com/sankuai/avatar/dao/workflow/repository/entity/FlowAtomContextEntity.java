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
public class FlowAtomContextEntity {
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

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
