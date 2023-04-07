package com.sankuai.avatar.workflow.core.input.host;

import com.sankuai.avatar.workflow.core.annotation.FlowMeta;
import com.sankuai.avatar.workflow.core.context.resource.Host;
import com.sankuai.avatar.workflow.core.input.AbstractFlowInput;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FlowMeta(flowTemplateName = "deliver_activity_resource")
public class HostsRebootInput extends AbstractFlowInput {
    @NotBlank
    String env;

    @NotBlank
    String reason;

    @NotBlank
    String appkey;

    @NotEmpty(message = "变更机器不能为空")
    List<Host> hosts;
}
