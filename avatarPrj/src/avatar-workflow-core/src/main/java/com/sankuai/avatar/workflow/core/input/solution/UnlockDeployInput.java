package com.sankuai.avatar.workflow.core.input.solution;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sankuai.avatar.workflow.core.annotation.Display;
import com.sankuai.avatar.workflow.core.annotation.FlowMeta;
import com.sankuai.avatar.workflow.core.input.AbstractFlowInput;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 高峰期临时解禁输入参数
 *
 * @author zhaozhifan02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FlowMeta(flowTemplateName = "unlock_deploy")
public class UnlockDeployInput extends AbstractFlowInput {

    /**
     * 变更服务 appKey
     */
    @JsonProperty("appkey")
    @NotBlank(message = "appKey不能为空")
    @Display(name = "来源")
    String appkey;

    /**
     * 申请原因
     */
    @NotBlank(message = "申请原因不能为空")
    @Display(name = "说明")
    String reason;

    /**
     * 申请备注
     */
    String comment;

    /**
     * 解禁类型
     * 默认为空 临时解禁
     * alteration 长期解禁
     */
    String app;

    /**
     * 解禁维度
     * APPKEY、APPKEYLITESET
     */
    String configType;

    /**
     * 高峰期状态
     * ONLINE、OFFLINE
     */
    String status;

    /**
     * 结束时间(单位天)
     */
    Integer endTime;
}
