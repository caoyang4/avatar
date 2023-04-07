package com.sankuai.avatar.dao.es.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * atom 数据更新对象
 *
 * @author zhaozhifan02
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowAtomUpdateRequest implements Serializable {
    /**
     * 流程Id
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
     * atom 名称
     */
    @SerializedName("atom_name")
    private String atomName;

    /**
     * atom 中文名称
     */
    @SerializedName("atom_cn_name")
    private String atomCnName;

    /**
     * 执行耗时
     */
    @SerializedName("exec_time")
    private Integer execTime;

    /**
     * 模板名称
     */
    @SerializedName("template_name")
    private String templateName;

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
     * 错误
     */
    private String errs;
}
