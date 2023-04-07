package com.sankuai.avatar.dao.es.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import com.sankuai.avatar.dao.es.request.audit.ApproveUser;
import com.sankuai.avatar.dao.es.request.audit.Decision;
import com.sankuai.avatar.dao.es.request.audit.Result;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 审核 es 数据更新请求对象
 *
 * @author zhaozhifan02
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowAuditUpdateRequest implements Serializable {
    /**
     * 流程id
     */
    @SerializedName("flow_id")
    private Integer flowId;

    /**
     * 审核id
     */
    private Integer id;

    /**
     * 索引
     */
    private Integer index;

    /**
     * 审核状态
     */
    @SerializedName("approve_status")
    private String approveStatus;

    /**
     * 模板名称
     */
    @SerializedName("template_name")
    private String templateName;

    /**
     * 模板中文名
     */
    @SerializedName("template_cn_name")
    private String templateCnName;

    /**
     * 是否超级用户
     */
    @SerializedName("is_super_role")
    private String isSuperRole;

    /**
     * 动态刷新
     */
    @SerializedName("dynamic_refresh")
    private Integer dynamicRefresh;

    /**
     * 结果
     */
    private Result result;

    /**
     * 决策条件
     */
    private Decision decision;

    /**
     * 审核人信息
     */
    @SerializedName("approve_user")
    private ApproveUser approveUser;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型
     */
    private String types;

    /**
     * 描述
     */
    private String desc;

    /**
     * 最终审核人
     */
    @SerializedName("final_approve_user")
    private String finalApproveUser;

    /**
     * 最终审核人部门Id
     */
    @SerializedName("final_approve_user_org_id")
    private String finalApproveUserOrgId;

    /**
     * 最终审核人部门路径
     */
    @SerializedName("final_approve_user_org_path")
    private String finalApproveUserOrgPath;

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
     * 周
     */
    @SerializedName("week_of_year")
    private String weekOfYear;

    /**
     * 间隔
     */
    private Integer interval;

    /**
     * 是否生效
     */
    private Integer effective;

    /**
     * 决策类
     */
    private String klass;
}
