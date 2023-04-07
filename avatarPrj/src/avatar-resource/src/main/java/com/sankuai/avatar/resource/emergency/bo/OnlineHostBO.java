package com.sankuai.avatar.resource.emergency.bo;

import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-02 21:12
 */
@Data
public class OnlineHostBO {

    /**
     * 环境
     */
    private String env;

    /**
     * 资源池
     */
    private String channel;

    /**
     * 资源池中文名称
     */
    private String channelCn;

    /**
     * 集群
     */
    private String cluster;

    /**
     * 集群中文名
     */
    private String clusterCn;

    /**
     * 城市
     */
    private String city;

    /**
     * 地域
     */
    private String region;

    /**
     * 机器总数量
     */
    private Integer count;

    /**
     * 交付数量
     */
    private Integer deliverCount;

    /**
     * CPU大小
     */
    private Integer cpu;

    /**
     * 内存大小
     */
    private Integer memory;

    /**
     * 磁盘大小
     */
    private Integer disk;

    /**
     * 磁盘类型
     */
    private String diskType;

    /**
     * 磁盘类型中文名称
     */
    private String diskTypeCn;

    /**
     * 机器分布的机房
     */
    private List<HostIdcBO> idcs;

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
     * set
     */
    private String set;

    /**
     * 并行度
     */
    private Integer parallel;

    /**
     * 是否部署代码
     */
    private Boolean deploy;

    /**
     * 机器配置的其他描述信息
     */
    private String configExtraInfo;

}
