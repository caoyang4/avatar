package com.sankuai.avatar.workflow.core.input.jumper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sankuai.avatar.workflow.core.annotation.Display;
import com.sankuai.avatar.workflow.core.annotation.FlowMeta;
import com.sankuai.avatar.workflow.core.input.AbstractFlowInput;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 跳板机密码重置执行入参
 * @author caoyang
 * @create 2023-03-14 15:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FlowMeta(flowTemplateName = "password_reset")
public class PasswordResetInput extends AbstractFlowInput {

    /**
     * 登录名
     */
    @JsonProperty("login_name")
    @NotBlank(message = "登录名不能为空")
    @Display(name = "登录名")
    String loginName;

    /**
     * 操作类型
     */
    @NotBlank(message = "类型不能为空")
    @Display(name = "类型")
    String type;

}
