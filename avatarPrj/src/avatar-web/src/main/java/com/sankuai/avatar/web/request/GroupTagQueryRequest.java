package com.sankuai.avatar.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 主机业务分组查询对象
 *
 * @author qinwei05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupTagQueryRequest {

    /**
     * 环境
     */
    @NotBlank(message = "env不能为空")
    @Pattern(regexp = "(^prod$|^staging$|^test$|^dev$|^ppe$)", message = "env环境参数错误！！！仅可为prod、staging、test、dev、ppe中的一个")
    private String env;

    /**
     * 服务
     */
    private String appkey;

    /**
     * 检索关键字
     */
    private String keyword;

    /**
     * 服务树owt
     */
    private String owt;
}
