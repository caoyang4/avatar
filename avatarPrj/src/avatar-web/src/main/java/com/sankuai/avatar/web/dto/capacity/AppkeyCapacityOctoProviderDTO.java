package com.sankuai.avatar.web.dto.capacity;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-04 17:54
 */
@Data
public class AppkeyCapacityOctoProviderDTO {
    /**
     * 机器名称
     */
    private String hostName;

    /**
     * 机器 ip
     */
    private String ip;

    /**
     * 机器所属 set
     */
    private String cell;

    /**
     * 所属机房
     */
    private String idc;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 节点状态
     */
    private Integer status;
}
