package com.sankuai.avatar.client.rocket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * Rocket主机对象
 *
 * @author qinwei05
 * @date 2022/11/30
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RocketHost {

    /**
     * id
     */
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * fqdn
     */
    private String fqdn;

    /**
     * 机房
     */
    private String idc;

    /**
     * ip局域网
     */
    private String ipLan;

    /**
     * 种类
     */
    private String kind;

    /**
     * 供应商
     */
    private String vendor;

    /**
     * 操作系统类
     */
    private String osFamily;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 内核
     */
    private String kernel;

    /**
     * 网卡速度
     */
    private int nicSpeed;

    /**
     * 宿主机
     */
    private String parent;

    /**
     * cpu模型
     */
    private String cpuModel;

    /**
     * cpu数
     */
    private Integer cpuCount;

    /**
     * mem大小
     */
    private Integer memSize;

    /**
     * 磁盘大小
     */
    private String diskSize;

    /**
     * uuid
     */
    private String uuid;

    /**
     * env
     */
    private String env;

    /**
     * 状态
     */
    private String status;

    /**
     * 泳道
     */
    private String swimlane;

    /**
     * 镜像ID
     */
    private String mirrorId;

    /**
     * ctime
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    private Date ctime;

    /**
     * mtime
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    private Date mtime;

    /**
     * 集群
     */
    private String cluster;

    /**
     * sn
     */
    private String sn;

    /**
     * 味道名字
     */
    private String flavorName;

    /**
     * 操作系统主要版本
     */
    private Integer osMajorVersion;

    /**
     * 操作系统小版本
     */
    private Integer osMinorVersion;

    /**
     * 磁盘类型
     */
    private String diskType;

    /**
     * 机架信息
     */
    private String rackInfo;

    /**
     * SET
     */
    private String cell;

    /**
     * 描述
     */
    private String comment;

    /**
     * 管理平台
     */
    private String managePlat;

    /**
     * 局域网ip版本6
     */
    private String ipLanV6;

    /**
     * 业务分组
     */
    private String grouptags;

}
