package com.sankuai.avatar.dao.workflow.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow")
public class FlowDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
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
     * 索引
     */
    @Column(name = "`index`")
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
     * 模板版本号
     */
    private Integer version;

    /*
      以下字段为V1保留，兼容V1数据类型，仅为接口层适配使用
     */

    /**
     * 入参
     */
    private byte[] input;

    /**
     * 公共数据
     */
    private byte[] publicData;

    /**
     * 任务列表
     */
    private byte[] tasks;
    /**
     * 队列任务类型
     * squirrelQueue、dbQueue
     */
    private String type;

    /**
     * 配置
     */
    private byte[] config;

    /**
     * 日志
     */
    private byte[] logs;

    /**
     * 流程类型 1-mbop 2-avatar
     */
    private Integer flowType;
}
