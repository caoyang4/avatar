package com.sankuai.avatar.dao.es.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 流程数据更新请求对象
 *
 * @author zhaozhifan02
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowDataUpdateRequest implements Serializable {
    /**
     * 流程Id
     */
    private Integer id;

    /**
     * 流程 UUID
     */
    private String uuid;

    /**
     * 流程类型
     */
    @SerializedName("flow_type")
    private Integer flowType;

    /**
     * 模板Id
     */
    @SerializedName("template_id")
    private Integer templateId;

    /**
     * 模板名称
     */
    @SerializedName("template_name")
    private String templateName;

    /**
     * 服务 appKey
     */
    @SerializedName("appkey")
    private String appKey;

    /**
     * 服务 service
     */
    private String srv;

    /**
     * 流程状态
     */
    private String status;

    /**
     * 流程审批状态
     */
    @SerializedName("approve_status")
    private String approveStatus;

    /**
     * 审批类型
     * 或签：0
     * 会签：1
     */
    @SerializedName("approve_type")
    private Integer approveType;

    /**
     * 审批人
     */
    @SerializedName("approve_users")
    private String approveUsers;

    /**
     * 审批角色
     */
    @SerializedName("approved_role")
    private String approvedRole;

    /**
     * 创建人
     */
    @SerializedName("create_user")
    private String createUser;

    /**
     * 创建人中文名
     */
    @SerializedName("create_user_name")
    private String createUserName;

    /**
     * 环境
     */
    private String env;

    /**
     * 别名
     */
    @SerializedName("cn_name")
    private String cname;

    /**
     * 模板中文名
     */
    @SerializedName("template_cn_name")
    private String templateCnName;

    /**
     * 开始时间
     */
    @SerializedName("start_time")
    private String startTime;

    /**
     * 结束时间
     */
    @SerializedName("end_time")
    private String endTime;

    /**
     * 原因
     */
    private String reason;

    /**
     * 对象类型
     */
    @SerializedName("object_type")
    private String objectType;

    /**
     * 对象名称
     */
    @SerializedName("object_name")
    private String objectName;

    /**
     * 是否失败
     */
    @SerializedName("is_failed")
    private Integer isFailed;


    /**
     * 索引
     */
    private Integer index;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 周
     */
    @SerializedName("week_of_year")
    private String weekOfYear;

    /**
     * 间隔
     */
    private Integer interval;
}
