package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * OPS服务树节点对象
 *
 * @author qinwei05
 * @date 2022/11/08
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class OpsSrv {

    /**
     * 描述信息
     */
    private String comment;

    /**
     * 研发负责人
     */
    @JsonProperty("rd_owner")
    private String rdOwner;

    /**
     * 是否满足soa
     */
    @JsonProperty("meet_soa")
    private Boolean meetSoa;

    /**
     * 最大单交换机下属机器数量
     */
    @JsonProperty("switch_divide_max")
    private Integer switchDivideMax;

    /**
     * 服务不可容器化原因
     */
    @JsonProperty("uncontainerable_reason")
    private String uncontainerableReason;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 最大单机柜下属机器数量
     */
    @JsonProperty("rack_divide_max")
    private Integer rackDivideMax;

    /**
     * 最大单宿主机下属机器数量
     */
    @JsonProperty("container_divide_max")
    private Integer containerDivideMax;
    /**
     * 测试部署负责人
     */
    @JsonProperty("beta_deploy_owner")
    private List<String> betaDeployOwner;
    /**
     * 值班人
     */
    @JsonProperty("duty_admin")
    private String dutyAdmin;

    /**
     * 项目状态
     */
    @JsonProperty("project_status")
    private String projectStatus;

    /**
     * 项目类型
     */
    @JsonProperty("project_type")
    private String projectType;

    /**
     * id
     */
    private Integer id;

    /**
     * 类别
     */
    private String category;

    /**
     * 容灾等级更新时间
     */
    @JsonProperty("capacity_update_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date capacityUpdateAt;

    /**
     * 容灾等级
     */
    private Integer capacity;

    /**
     * 单上联机器占比 | 单上联指机器只连接了一台交换机
     */
    @JsonProperty("risk_host_rate")
    private Integer riskHostRate;

    /**
     * 是否接受单机重启：新增时默认必须为True
     */
    @JsonProperty("single_host_restart")
    private Boolean singleHostRestart;

    /**
     * 模块名
     */
    private String soamod;
    /**
     * 预装软件
     */
    @JsonProperty("container_type")
    private String containerType;

    /**
     * 服务是否包含liteset主机
     */
    @JsonProperty("is_liteset")
    private Boolean isLiteset;

    /**
     * 容灾等级等级设置原因
     */
    @JsonProperty("capacity_reason")
    private String capacityReason;

    /**
     * 机柜打散率
     */
    @JsonProperty("rack_divide_rate")
    private Integer rackDivideRate;

    /**
     * rd负责人
     */
    @JsonProperty("rd_admin")
    private String rdAdmin;

    /**
     * 服务是否包含set主机
     */
    @JsonProperty("is_set")
    private Boolean isSet;

    /**
     * 服务类型：语言/组件
     */
    @JsonProperty("service_type")
    private String serviceType;

    /**
     * 有状态原因
     */
    @JsonProperty("stateful_reason")
    private String statefulReason;

    /**
     * SRE负责人
     */
    @JsonProperty("op_admin")
    private String opAdmin;

    /**
     * 备份研发负责人
     */
    @JsonProperty("backup_rd")
    private String backupRd;

    /**
     * 服务状态
     */
    private Boolean stateful;

    /**
     * 模块
     */
    private String module;

    /**
     * srv key
     */
    private String key;

    /**
     * 弹性伸缩
     */
    private String autoscale;

    /**
     * tenant  隔离环境标识isol
     */
    private String tenant;

    /**
     * appkey
     */
    private String appkey;

    /**
     * 开关分率
     */
    @JsonProperty("switch_divide_rate")
    private Integer switchDivideRate;

    /**
     * soasrv
     */
    private String soasrv;

    /**
     * 服务名字
     */
    private String name;

    /**
     * 容灾等级更新人
     */
    @JsonProperty("capacity_update_by")
    private String capacityUpdateBy;

    /**
     * 最小实例
     */
    @JsonProperty("min_instances")
    private String minInstances;

    /**
     * 创建时间
     */
    @JsonProperty("create_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * soaapp：应用名
     */
    private String soaapp;

    /**
     * 宿主机打散率
     */
    @JsonProperty("container_divide_rate")
    private Integer containerDivideRate;

    /**
     * 服务是否可容器化：新增时默认必须为True
     */
    private Boolean containerable;
    /**
     * 兼容ipv6
     */
    @JsonProperty("compatible_ipv6")
    private Boolean compatibleIpv6;

    /**
     * 测试负责人
     */
    @JsonProperty("ep_admin")
    private String epAdmin;
}
