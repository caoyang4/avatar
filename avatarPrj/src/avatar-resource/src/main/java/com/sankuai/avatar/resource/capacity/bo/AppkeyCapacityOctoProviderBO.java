package com.sankuai.avatar.resource.capacity.bo;

import lombok.Data;

/**
 * octo节点信息
 * @author caoyang
 * @create 2022-11-03 16:14
 */
@Data
public class AppkeyCapacityOctoProviderBO {
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
