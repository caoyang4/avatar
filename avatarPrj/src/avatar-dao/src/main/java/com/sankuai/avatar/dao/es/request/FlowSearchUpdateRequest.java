package com.sankuai.avatar.dao.es.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 流程ES查询数据更新对象
 *
 * @author zhaozhifan02
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowSearchUpdateRequest implements Serializable {
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
    private String appkey;

    /**
     * 服务 service
     */
    private String srv;

    /**
     * 流程状态
     */
    private String status;

    /**
     * 域名
     */
    private String domain;

    /**
     * 主机
     */
    private String host;

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
     * 环境
     */
    private String env;

    /**
     * 别名
     */
    @SerializedName("cn_name")
    private String cnName;

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
}
