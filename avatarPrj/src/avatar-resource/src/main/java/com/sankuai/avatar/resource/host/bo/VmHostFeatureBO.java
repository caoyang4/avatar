package com.sankuai.avatar.resource.host.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * vm主机功能
 * Vm主机特性
 *
 * @author qinwei05
 * @date 2023/02/26
 */
@Data
@Builder
public class VmHostFeatureBO {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * ip
     */
    private String ip;

    /**
     * ipv6
     */
    private String ipv6;

    /**
     * 机房
     */
    private String idc;

    /**
     * 主机名
     */
    private String hostName;

    /**
     * cpu
     */
    private Integer cpu;

    /**
     * mem
     */
    private Integer mem;

    /**
     * 磁盘
     */
    private Integer disk;

    /**
     * 起源
     */
    private String origin;

    /**
     * SET
     */
    private String cell;

    /**
     * 业务分组
     */
    private String grouptags;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 集群
     */
    private String cluster;

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 存储类型
     */
    private String storageType;
}
