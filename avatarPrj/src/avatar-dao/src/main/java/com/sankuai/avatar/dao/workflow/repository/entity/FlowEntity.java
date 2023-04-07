package com.sankuai.avatar.dao.workflow.repository.entity;

import com.sankuai.avatar.dao.workflow.repository.model.FlowTemplateTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 流程元数据
 * @author zhaozhifan02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FlowEntity {
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
     * atom执行索引
     */
    private Integer index;

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
     * 创建人
     */
    private String createUser;

    /**
     * 创建人类型
     */
    private String createUserSource;

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
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * process索引
     */
    private Integer processIndex;

    /**
     * 引用模板的版本号
     */
    private Integer version;

    /*
      以下字段为V1保留，仅为接口层适配使用
     */

    /**
     * 入参
     */
    private Object input;

    /**
     * 任务列表
     */
    private List<FlowTemplateTask> tasks;

    /**
     * 配置
     */
    private Object config;

    /**
     * 日志
     */
    private Object logs;

    /**
     * 公共数据
     */
//    private Object publicData;

    /**
     * 队列任务类型
     * squirrelQueue、dbQueue
     */
//    private String type;
}
