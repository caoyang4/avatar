package com.sankuai.avatar.dao.resource.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 服务数据表
 * <a href="https://km.sankuai.com/collabpage/1415937894">WIKI</a>
 * @author qinwei05
 * @date 2022/10/27
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "resource_appkey")
public class AppkeyDO {

    /* ---------------------------- 服务基础信息 ---------------------------- */

    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 服务名
     */
    private String appkey;

    /**
     * 服务中文别名
     */
    private String name;

    /**
     * 服务描述信息
     */
    private String description;

    /**
     * 应用名
     */
    private String soaapp;

    /**
     * 模块名
     */
    private String soamod;

    /**
     * 服务名
     */
    private String soasrv;

    /**
     * 技术团队
     */
    private String businessGroup;

    /**
     * 服务所在服务树节点
     */
    private String srv;

    /**
     * 产品线
     */
    private String pdl;

    /**
     * 产品线中文名称
     */
    private String pdlName;

    /**
     * 部门owt
     */
    private String owt;

    /**
     * 部门owt中文名称
     */
    private String owtName;

    /**
     * 服务等级：核心、非核心
     */
    private String rank;

    /**
     * 预装软件
     */
    private String containerType;

    /**
     * 服务类型：语言/组件
     */
    private String serviceType;

    /**
     * 服务状态：有状态、无状态
     */
    private Boolean stateful;

    /**
     * 服务状态原因
     */
    private String statefulReason;

    /**
     * 服务是否兼容ipv6
     */
    private Boolean compatibleIpv6;

    /**
     * 服务是否包含Liteset机器
     */
    private Boolean isLiteset;

    /**
     * 服务是否包含Set机器
     */
    private Boolean isSet;

    /**
     * 服务隔离环境标识
     */
    private String tenant;

    /**
     * git仓库
     */
    private String gitRepository;

    /**
     * 服务类型（原上海侧字段）：WEB、SERVICE
     */
    private String projectType;

    /**
     * 服务是否下线
     */
    private Boolean isOffline;

    /* ---------------------------- 服务机器信息 ---------------------------- */

    /**
     * 服务下机器总数目
     */
    private Integer hostCount;

    /**
     * 服务下线上环境机器总数目
     */
    private Integer prodHostCount;

    /* ---------------------------- 服务容灾等级信息 ---------------------------- */

    /**
     * 服务容灾等级
     */
    private Integer capacity;

    /**
     * 服务容灾等级定级原因
     */
    private String capacityReason;

    /**
     * 服务容灾等级更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date capacityUpdateAt;

    /**
     * 服务容灾等级更新人
     */
    private String capacityUpdateBy;

    /**
     * 服务周资源利用率
     * 数据源：DOM
     */
    private String weekResourceUtil;

    /* ---------------------------- 服务人员信息 ---------------------------- */

    /**
     * 服务owner
     */
    private String admin;

    /**
     * 服务RD
     */
    private String rdAdmin;

    /**
     * 服务EP
     */
    private String epAdmin;

    /**
     * 服务SRE
     */
    private String opAdmin;

    /* ---------------------------- 服务所在应用信息 ---------------------------- */

    /**
     * 服务所在应用ID
     */
    private Integer applicationId;

    /**
     * 服务所在应用名称
     */
    private String applicationName;

    /**
     * 服务所在应用中文名
     */
    private String applicationChName;

    /* ---------------------------- 服务所在部门信息 ---------------------------- */

    /**
     * 服务所在部门ID
     */
    private String orgId;

    /**
     * 服务所在部门名
     */
    private String orgName;

    /* ---------------------------- 服务运营属性类信息 ---------------------------- */

    /**
     * 服务类型（前端、后端、Maven发布项、虚拟......）
     */
    private String type;

    /**
     * 服务所在结算单元ID
     */
    private String billingUnitId;

    /**
     * 服务所在结算单元名称
     */
    private String billingUnit;

    /**
     * 服务端
     */
    private String categories;

    /**
     * 服务标签
     */
    private String tags;

    /**
     * 服务框架
     */
    private String frameworks;

    /**
     * 服务是否外采
     * 1.TRUE
     * 2.FALSE
     * 3.UNCERTAIN（不确定）
     */
    private String isBoughtExternal;

    /**
     * 服务是否为PAAS
     */
    private Boolean paas;

    /* ---------------------------- 服务时间类信息 ---------------------------- */

    /**
     * 服务创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     * 服务信息更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    /**
     * 服务删除时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date offlineTime;
}
