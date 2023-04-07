package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * ops 主机对象
 *
 * @author xk
 * @date 2022/10/18
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsHost {
    /**
     * 评论
     */
    private String comment;

    /**
     * ip局域网
     */
    @JsonProperty("ip_lan")
    private String ipLan;

    /**
     * 内核
     */
    private String kernel;

    /**
     * ssh端口
     */
    @JsonProperty("ssh_port")
    private Integer sshPort;

    /**
     * cpu数
     */
    @JsonProperty("cpu_count")
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
    @JsonProperty("hyper_threading")
    private Boolean hyperThreading;

    /**
     * mtime
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date mtime;

    /**
     * qcloud id
     */
    @JsonProperty("qcloud_id")
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
    @JsonProperty("gpu_count")
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
    @JsonProperty("mem_size")
    private Integer memSize;

    /**
     * 泳道
     */
    private String swimlane;

    /**
     * 局域网ipv6
     */
    @JsonProperty("ip_lan_v6")
    private String ipLanV6;

    /**
     * 是否虚拟
     */
    @JsonProperty("is_virtual")
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
    @JsonProperty("rack_info")
    private String rackInfo;

    /**
     * ip广域网
     */
    @JsonProperty("ip_wan")
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
    @JsonProperty("maintain_end")
    private String maintainEnd;

    /**
     * 是否主机
     */
    @JsonProperty("is_host")
    private Boolean isHost;

    /**
     * 供应商
     */
    private String vendor;

    /**
     * iloMac
     */
    @JsonProperty("ilo_mac")
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
    @JsonProperty("cpu_model")
    private String cpuModel;

    /**
     * 网卡速度
     */
    @JsonProperty("nic_speed")
    private Integer nicSpeed;

    /**
     * 集团
     */
    private String corp;

    /**
     * 来源方
     */
    @JsonProperty("manage_plat")
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
    @JsonProperty("disk_size")
    private Integer diskSize;

    /**
     * ctime
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date ctime;

    /**
     * 保持开始
     */
    @JsonProperty("maintain_start")
    private String maintainStart;

    /**
     * 繁忙时间
     */
    @JsonProperty("buy_time")
    private String buyTime;

    /**
     * 镜像id
     */
    @JsonProperty("mirror_id")
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
    @JsonProperty("ilo_ip")
    private String iloIp;

    /**
     * 开关
     */
    @JsonProperty("switch")
    private String switchBak;

    /**
     * flavorName
     */
    @JsonProperty("flavor_name")
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
    @JsonProperty("end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
