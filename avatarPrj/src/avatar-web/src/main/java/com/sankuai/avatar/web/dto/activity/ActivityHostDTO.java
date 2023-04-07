package com.sankuai.avatar.web.dto.activity;

import lombok.Data;

/**
 * @author caoyang
 * @create 2023-03-08 15:43
 */
@Data
public class ActivityHostDTO {

    /**
     * 环境
     */
    private String env;

    /**
     * 类型
     */
    private String channel;

    /**
     * 类型名称
     */
    private String channelCn;

    /**
     * 集群
     */
    private String cluster;

    /**
     * 集群名称
     */
    private String clusterCn;

    /**
     * 城市
     */
    private String city;

    /**
     * 地区
     */
    private String region;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 交付数量
     */
    private Integer deliverCount;

    /**
     * CPU
     */
    private Integer cpu;

    /**
     * 内存
     */
    private Integer memory;

    /**
     * 磁盘
     */
    private Integer disk;

    /**
     * 磁盘类型
     */
    private String diskType;

    /**
     * 磁盘类型名称
     */
    private String diskTypeCn;

    /**
     * 机房
     */
    private String idcs;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 网卡
     */
    private String nic;

    /**
     * 网卡类型
     */
    private String nicType;

    /**
     * 链路
     */
    private String set;

    /**
     * 并行度
     */
    private Integer parallel;

    /**
     * 是否部署
     */
    private Boolean deploy;

    /**
     * 机器配置的其他描述信息
     */
    private String configExtraInfo;

}
