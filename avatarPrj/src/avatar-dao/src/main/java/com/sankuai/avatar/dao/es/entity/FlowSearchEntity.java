package com.sankuai.avatar.dao.es.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 流程查询数据对象
 *
 * @author zhaozhifan02
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowSearchEntity {
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
     * 流程类型
     */
    private String type;

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
     * 待操作人员
     */
    @SerializedName("operating_users")
    private String operatingUsers;

    /**
     * 已操作人员
     */
    @SerializedName("operated_users")
    private String operatedUsers;

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
     * 开始时间
     */
    @SerializedName("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @SerializedName("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 原因
     */
    private String reason;

    /**
     * 备注
     */
    private String comment;

    /**
     * 关键字
     */
    private List<String> keyword;

    /**
     * 模糊匹配
     */
    private List<String> fuzzy;
}
