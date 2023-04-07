package com.sankuai.avatar.resource.activity.bo;

import com.sankuai.avatar.resource.activity.constant.ResourceStatusType;
import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-03-08 13:49
 */
@Data
public class ActivityResourceBO {

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
    private ActivityHostBO hostConfig;

    /**
     * 状态
     */
    private ResourceStatusType status;

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
