package com.sankuai.avatar.web.vo.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.resource.activity.constant.ResourceStatusType;
import com.sankuai.avatar.web.dto.activity.ActivityHostDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author caoyang
 * @create 2023-03-08 17:21
 */
@Data
public class ActivityResourceVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 流程 id
     */
    private Integer flowId;

    /**
     * 流程 uuid
     */
    private String flowUuid;

    /**
     * 窗口期ID
     */
    @NotNull(message = "窗口期id不能为空")
    private Integer windowPeriodId;

    /**
     * appkey
     */
    @NotBlank(message = "appkey不能为空")
    private String appkey;

    /**
     * 部门Id
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
     *  机器的配置数据
     */
    private ActivityHostDTO hostConfig;

    /**
     * 机器配置
     */
    private String config;

    /**
     * 机房信息
     */
    private String idcs;

    /**
     * 数量
     */
    private Integer count;

    /**
     *   状态
     */
    private ResourceStatusType status;

    /**
     *   状态中文名
     */
    private String statusCn;

    /**
     *  创建人
     */
    private String createUser;

    /**
     *  创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     *  更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     *   开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     *  结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     *  交付时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deliverTime;

    /**
     *  归还时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date returnTime;

}
