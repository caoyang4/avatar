package com.sankuai.avatar.resource.host.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author qinwei05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HostAttributesBO {

    /**
     * 主机类型中文名（自定义处理展示）
     */
    private String kindName;

    /**
     * 机房中文名（自定义处理展示）
     */
    private String idcName;

    /**
     * 城市中文名（自定义处理展示）
     */
    private String cityName;

    /**
     * 网卡速率中文名（自定义处理展示）
     */
    private String nicSpeedName;

    /**
     * 来源中文名（自定义处理展示）
     */
    private String managePlatName;

    /**
     * 是否为win操作系统机器（自定义处理展示）
     */
    private Boolean isWin;

    /**
     * 机器特性（自定义处理展示）
     */
    private List<String> hostTags;

    /**
     * 宿主机特性（自定义处理展示）
     */
    private List<String> parentTags;

    /**
     * 网络类型（自定义处理展示）
     */
    private String netType;

    /**
     * 资源池中文名（自定义处理展示）
     */
    private String originType;

    /**
     * 归还时返回的资源池中文名（自定义处理展示）
     */
    private String returnType;

    /**
     * 来源中文名（自定义处理展示）
     */
    private String originGroup;

    /**
     * 评论
     */
    private String comment;

    /**
     * ip局域网
     */
    private String ipLan;

    /**
     * 内核
     */
    private String kernel;

    /**
     * ssh端口
     */
    private Integer sshPort;

    /**
     * cpu数
     */
    private Integer cpuCount;

    /**
     * 集群
     */
    private String cluster;

    /**
     * 带宽
     */
    private Integer bandwidth;

    /**
     * 超线程
     */
    private Boolean hyperThreading;

    /**
     * mtime
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date mtime;

    /**
     * qcloud id
     */
    private String qcloudId;

    /**
     * 业务分组
     */
    private String grouptags;

    /**
     * id
     */
    private Integer id;

    /**
     * gpu计算
     */
    private Integer gpuCount;

    /**
     * 正常运行时间
     */
    private Integer uptime;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 内存
     */
    private Integer memSize;

    /**
     * 泳道
     */
    private String swimlane;

    /**
     * 局域网ipv6
     */
    private String ipLanV6;

    /**
     * 是否虚拟
     */
    private Boolean isVirtual;

    /**
     * cell（set标签）
     */
    private String cell;

    /**
     * 环境
     */
    private String env;

    /**
     * 机架信息
     */
    private String rackInfo;

    /**
     * ip广域网
     */
    private String ipWan;

    /**
     * 类型
     */
    private String type;

    /**
     * sn
     */
    private String sn;

    /**
     * 状态
     */
    private String status;

    /**
     * 维护结束
     */
    private String maintainEnd;

    /**
     * 是否主机
     */
    private Boolean isHost;

    /**
     * 供应商
     */
    private String vendor;

    /**
     * iloMac
     */
    private String iloMac;

    /**
     * 宿主机
     */
    private String parent;

    /**
     * 品牌
     */
    private String brand;

    /**
     * cpu
     */
    private String cpuModel;

    /**
     * 网卡速度
     */
    private Integer nicSpeed;

    /**
     * 集团
     */
    private String corp;

    /**
     * 来源方
     */
    private String managePlat;

    /**
     * 名字
     */
    private String name;

    /**
     * 种类
     */
    private String kind;

    /**
     * 磁盘大小
     */
    private Integer diskSize;

    /**
     * ctime
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date ctime;

    /**
     * 保持开始
     */
    private String maintainStart;

    /**
     * 繁忙时间
     */
    private String buyTime;

    /**
     * 镜像id
     */
    private String mirrorId;

    /**
     * fqdn
     */
    private String fqdn;

    /**
     * 机房
     */
    private String idc;

    /**
     * iloIp
     */
    private String iloIp;

    /**
     * flavorName
     */
    private String flavorName;

    /**
     * 模型
     */
    private String model;

    /**
     * 费用
     */
    private Integer expense;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 地区
     */
    private String region;

    /**
     * 结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
