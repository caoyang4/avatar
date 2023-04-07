package com.sankuai.avatar.dao.workflow.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhaozhifan02
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecuteResult {
    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 持续时间
     */
    private Integer duration;

    /**
     * 输入
     */
    private String input;

    /**
     * 输出
     */
    private String output;

    /**
     * 异常信息
     */
    private String exception;

}
