package com.sankuai.avatar.workflow.core.input.solution;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sankuai.avatar.workflow.core.annotation.Display;
import com.sankuai.avatar.workflow.core.annotation.FlowMeta;
import com.sankuai.avatar.workflow.core.input.AbstractFlowInput;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工作委托输入参数
 *
 * @author zhaozhifan02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FlowMeta(flowTemplateName = "delegate_work")
public class DelegateWorkInput extends AbstractFlowInput {

    /**
     * 委托人
     */
    @NotEmpty(message = "委托人不能为空")
    @Display(name = "委托人")
    List<String> agent;

    /**
     * 备注
     */
    String comment;

    /**
     * 委托时间
     */
    @JsonProperty("end_time")
    @NotBlank(message = "结束时间不能为空")
    @Display(name = "结束时间")
    String endTime;

    @Display(name = "发起人")
    String user;
}
