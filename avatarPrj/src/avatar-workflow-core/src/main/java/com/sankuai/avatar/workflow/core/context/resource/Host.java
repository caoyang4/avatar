package com.sankuai.avatar.workflow.core.context.resource;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Jie.li.sh
 * @create 2022-11-03
 **/
@Builder
@Data
public class Host {
    /**
     * ipv4地址
     */
    @NotBlank
    String ipLan;
    /**
     * 主机名
     */
    @NotBlank
    String name;

    /**
     * uuid
     */
    @NotBlank
    String uuid;
}
