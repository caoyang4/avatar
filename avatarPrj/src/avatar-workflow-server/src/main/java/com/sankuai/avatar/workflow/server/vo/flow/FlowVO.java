package com.sankuai.avatar.workflow.server.vo.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author zhaozhifan02
 */
@Data
public class FlowVO {
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
     * 模板名称
     */
    private String templateName;

    /**
     * 中文名
     */
    private String cnName;

    /**
     * 流程类型
     */
    private String flowType;

    /**
     * atom object name
     */
    private String objectName;

    /**
     * atom object type
     */
    private String objectType;

    /**
     * 任务列表
     */
    private Object tasks;

    /**
     * 入参
     */
    private Object input;

    /**
     * 配置
     */
    private Object config;

    /**
     * 公共数据
     */
    private Object publicData;

    /**
     * 索引
     */
    private Integer index;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 流程状态
     */
    private String status;

    /**
     * 审核状态
     */
    private String approveStatus;

    /**
     * 审核人
     */
    private String approveUsers;

    /**
     * 审核类型
     */
    private String approveType;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建人中文名
     */
    private String createUserName;

    /**
     * 服务 appKey
     */
    private String appkey;

    /**
     * srv 信息
     */
    private String srv;

    /**
     * 环境
     */
    private String env;

    /**
     * 队列任务类型
     * squirrelQueue、dbQueue
     */
    private String type;

    /**
     * 开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
