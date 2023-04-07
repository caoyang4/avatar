package com.sankuai.avatar.workflow.core.input.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sankuai.avatar.workflow.core.annotation.Display;
import com.sankuai.avatar.workflow.core.annotation.FlowMeta;
import com.sankuai.avatar.workflow.core.input.AbstractFlowInput;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 更新测试负责人的输入
 * @author Jie.li.sh
 * @create 2022-11-11
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@FlowMeta(flowTemplateName = "update_service_test_principal")
public class UpdateServiceTestPrincipalInput extends AbstractFlowInput {
    /**
     * 测试负责人
     */
    @JsonProperty("ep_admin")
    @NotBlank
    @Display(name = "测试负责人")
    String epAdmin;

    /**
     * 服务名称
     */
    @JsonProperty("service_name")
    @NotBlank
    @Display(name = "服务名称")
    String serviceName;
}
