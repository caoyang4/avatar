package com.sankuai.avatar.dao.resource.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 活动资源
 * @author caoyang
 * @create 2023-03-06 16:50
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "resource_activity_resource")
public class ActivityResourceDO {

    /**
     * 主键id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 流程Id
     */
    private Integer flowId;

    /**
     * 流程Uuid
     */
    private String flowUuid;

    /**
     * 窗口期ID
     */
    private Integer windowPeriodId;

    /**
     *  appkey
     */
    private String appkey;

    /**
     * 部门 id
     */
    private String orgId;

    /**
     * 部门名称
     */
    private String orgName;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 保留数量
     */
    private Integer retainCount;

    /**
     * 机器的配置数据
     */
    private String hostConfig;

    /**
     * 状态
     */
    private String status;

    /**
     *   创建人
     */
    private String createUser;

    /**
     *   起始时间
     */
    private Date startTime;

    /**
     *   结束时间
     */
    private Date endTime;

    /**
     *   交付时间
     */
    private Date deliverTime;

    /**
     *   归还时间
     */
    private Date returnTime;

    /**
     *   创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date updateTime;

}
