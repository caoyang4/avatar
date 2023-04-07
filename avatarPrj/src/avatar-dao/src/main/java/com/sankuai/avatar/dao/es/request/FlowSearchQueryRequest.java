package com.sankuai.avatar.dao.es.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author zhaozhifan02
 */
@Data
@Builder
public class FlowSearchQueryRequest {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 服务 appKey
     */
    private String appKey;

    /**
     * 模板名称
     */
    private String template;

    /**
     * 环境
     */
    private String env;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 审核人
     */
    private String approveUser;

    /**
     * 原因
     * 模糊查询
     */
    private String reason;

    /**
     * 创建时间-开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeBegin;

    /**
     * 创建时间-结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeEnd;

    /**
     * 操作对象
     */
    private String fuzzy;

    /**
     * 是否状态排序
     */
    private Boolean stateSort;
}
