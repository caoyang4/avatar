package com.sankuai.avatar.workflow.server.vo.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 流程详情
 *
 * @author zhaozhifan02
 */
@Data
public class FlowDetailVO {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 模板Id
     */
    private Integer templateId;

    /**
     * 中文名
     */
    private String cnName;

    /**
     * 入参
     */
    private Object input;

    /**
     * 任务列表
     */
    private Object task;

    /**
     * 配置
     */
    private Object config;

    /**
     * 索引
     */
    private Integer index;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
