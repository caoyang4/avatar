package com.sankuai.avatar.workflow.server.dto.flow;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@Data
public class FlowDTO {
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
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 关键字
     */
    private List<String> keyword;

    private String reason;
}
