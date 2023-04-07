package com.sankuai.avatar.dao.workflow.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Atom 执行记录
 *
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow_atom_record")
public class FlowAtomRecordDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 原子化步骤名称
     */
    private String atomName;

    /**
     * 状态
     */
    private String status;

    /**
     * 任务输入
     */
    private String input;

    /**
     * 任务输出
     */
    private String output;

    /**
     * 异常
     */
    private String exception;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 耗时
     */
    private Integer duration;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}
