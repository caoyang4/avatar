package com.sankuai.avatar.workflow.core.input.plus;

import com.sankuai.avatar.workflow.core.annotation.Display;
import com.sankuai.avatar.workflow.core.annotation.FlowMeta;
import com.sankuai.avatar.workflow.core.input.AbstractFlowInput;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 新增发布项
 * @author Jie.li.sh
 * @create 2022-11-11
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@FlowMeta(flowTemplateName = "add_plus")
@Builder
public class AddPlusFlowInput extends AbstractFlowInput {
    /**
     * 服务名
     */
    @NotBlank
    String appkey;
    /**
     * 仓库地址
     */
    @Display(name = "仓库地址")
    @NotBlank
    String respository;
    /**
     * 发布项名称
     */
    @Display(name = "发布项名称")
    @NotBlank
    String name;
}
