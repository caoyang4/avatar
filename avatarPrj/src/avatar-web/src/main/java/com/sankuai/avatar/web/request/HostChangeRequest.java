package com.sankuai.avatar.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 主机变更时，获取建议信息
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostChangeRequest {

    /**
     * 环境
     */
    @NotBlank(message = "env不能为空")
    @Pattern(regexp = "(^prod$|^staging$|^test$|^dev$|^ppe$)", message = "env环境参数错误！！！仅可为prod、staging、test、dev、ppe中的一个")
    private String env;

    /**
     * 服务
     */
    @NotBlank(message = "appkey服务名称不能为空")
    private String appkey;

    /**
     * 变更类型
     */
    private String changeType;
}
