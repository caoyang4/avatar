package com.sankuai.avatar.dao.es.request.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

/**
 * 审核结果
 *
 * @author zhaozhifan02
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    /**
     * 审核状态
     */
    private String status;

    /**
     * 是否结束
     */
    private Boolean finish;

    /**
     * 标题
     */
    private String title;

    /**
     * 审核人
     */
    private String user;

    /**
     * 执行时间
     */
    private String time;

    /**
     * 描述
     */
    private String desc;

    /**
     * 操作类型
     */
    private String op;
}
