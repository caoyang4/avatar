package com.sankuai.avatar.dao.es.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Oceanus es更新数据请求
 *
 * @author zhaozhifan02
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowOceanusUpdateRequest implements Serializable {
    /**
     * 流程id
     */
    @SerializedName("flow_id")
    private Integer flowId;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 状态
     */
    private String status;

    /**
     * 模板名称
     */
    @SerializedName("template_name")
    private String templateName;

    /**
     * SRE人员
     */
    private String sre;

    /**
     * 模板中文名
     */
    @SerializedName("cn_name")
    private String cnName;

    /**
     * 站点名称
     */
    @SerializedName("site_name")
    private String siteName;

    /**
     * 天网异常
     */
    @SerializedName("skynet_abnormal")
    private String skynetAbnormal;

    /**
     * 天网超时
     */
    @SerializedName("skynet_timeout")
    private String skynetTimeout;

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
     * 忽略原因
     */
    @SerializedName("ignore_reason")
    private String ignoreReason;

    /**
     * 错误原因
     */
    @SerializedName("err_reason")
    private String errReason;

    /**
     * 创建人
     */
    @SerializedName("create_user")
    private String createUser;

    /**
     * 审批间隔
     */
    @SerializedName("approve_interval")
    private Integer approveInterval;

    /**
     * 检查异常原因
     */
    @SerializedName("check_abnormal_reason")
    private String checkAbnormalReason;


    /**
     * 发布失败
     */
    @SerializedName("publish_fail")
    private String publishFail;

    /**
     * 是否灰度发布
     */
    @SerializedName("is_gray_publish")
    private String isGrayPublish;

    /**
     * 发布间隔
     */
    @SerializedName("publish_interval")
    private Integer publishInterval;

    /**
     * 健康检查异常
     */
    @SerializedName("health_check_abnormal")
    private String healthCheckAbnormal;

    /**
     *
     */
    @SerializedName("flow_interval")
    private Integer flowInterval;


    /**
     * 创建人部门路径
     */
    @SerializedName("create_user_org_path")
    private String createUserOrgPath;

    /**
     * 创建人部门id
     */
    @SerializedName("create_user_org_id")
    private String createUserOrgId;

    /**
     * 检查异常
     */
    @SerializedName("check_abnormal")
    private String checkAbnormal;

    /**
     * 周
     */
    @SerializedName("week_of_year")
    private String weekOfYear;


    /**
     * 发布模式
     */
    @SerializedName("publish_mode")
    private Long publishMode;

    /**
     * 配置超时
     */
    @SerializedName("config_effect_timeout")
    private String configEffectTimeout;

    /**
     * 任务间隔
     */
    @SerializedName("tasks_interval")
    private Integer tasksInterval;

    /**
     * 是否失败
     */
    @SerializedName("is_failed")
    private Integer isFailed;
}
