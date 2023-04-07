package com.sankuai.avatar.collect.appkey.model;

import com.sankuai.avatar.collect.appkey.annotion.SourceBy;
import com.sankuai.avatar.collect.appkey.collector.source.SourceName;
import com.sankuai.avatar.collect.appkey.event.CollectEventData;
import lombok.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 服务对象
 * @author qinwei05
 * @date 2022/10/27
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Appkey {

    /* ---------------------------- 服务基础信息 ---------------------------- */

    /**
     * 服务名
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private String appkey;

    /**
     * 服务中文别名
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private String name;

    /**
     * 服务描述信息
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String description;

    /**
     * 应用名
     */
    @SourceBy(SourceName.OPS)
    private String soaapp;

    /**
     * 模块名
     */
    @SourceBy(SourceName.OPS)
    private String soamod;

    /**
     * 服务名
     */
    @SourceBy(SourceName.OPS)
    private String soasrv;

    /**
     * 技术团队
     */
    @SourceBy(SourceName.OPS)
    private String businessGroup;

    /**
     * 产品线中文名称
     */
    @SourceBy(SourceName.OPS)
    private String pdlName;

    /**
     * 部门owt中文名称
     */
    @SourceBy(SourceName.OPS)
    private String owtName;

    /**
     * 服务所在服务树节点
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private String srv;

    /**
     * 产品线
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private String pdl;

    /**
     * 部门owt
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private String owt;

    /**
     * 服务等级：核心、非核心
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private String rank;

    /**
     * 预装软件
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private String containerType;

    /**
     * 服务类型：语言/组件
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private String serviceType;

    /**
     * 服务状态：有状态、无状态
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private Boolean stateful;

    /**
     * 服务状态原因
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private String statefulReason;

    /**
     * 服务是否兼容ipv6
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private Boolean compatibleIpv6;

    /**
     * 服务是否包含Liteset机器
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private Boolean isLiteset;

    /**
     * 服务是否包含Set机器
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private Boolean isSet;

    /**
     * 服务隔离环境标识
     * 数据源：OPS
     */
    @SourceBy(SourceName.SC)
    private String tenant;

    /**
     * git仓库
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String gitRepository;

    /**
     * 服务类型（原上海侧字段）：WEB、SERVICE
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private String projectType;

    /**
     * 服务是否下线
     * 数据源：SC/OPS
     */
    @SourceBy({SourceName.SC, SourceName.OPS})
    private Boolean isOffline;

    /* ---------------------------- 服务机器信息 ---------------------------- */

    /**
     * 服务下机器总数目
     * 数据源：rocket
     */
    @SourceBy(SourceName.ROCKET)
    private Integer hostCount;

    /**
     * 服务下线上环境机器总数目
     * 数据源：rocket
     */
    @SourceBy(SourceName.ROCKET)
    private Integer prodHostCount;

    /* ---------------------------- 服务容灾等级信息 ---------------------------- */

    /**
     * 服务容灾等级
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private Integer capacity;

    /**
     * 服务容灾等级定级原因
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private String capacityReason;

    /**
     * 服务容灾等级更新时间
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private Date capacityUpdateAt;

    /**
     * 服务容灾等级更新人
     * 数据源：OPS
     */
    @SourceBy(SourceName.OPS)
    private String capacityUpdateBy;

    /**
     * 服务周资源利用率
     * 数据源：DOM
     */
    @SourceBy(SourceName.DOM)
    private String weekResourceUtil;

    /* ---------------------------- 服务人员信息 ---------------------------- */

    /**
     * 服务owner
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String admin;

    /**
     * 服务RD
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private String rdAdmin;

    /**
     * 服务EP
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private String epAdmin;

    /**
     * 服务SRE
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private String opAdmin;

    /* ---------------------------- 服务所在应用信息 ---------------------------- */

    /**
     * 服务所在应用ID
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private Integer applicationId;

    /**
     * 服务所在应用名称
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String applicationName;

    /**
     * 服务所在应用中文名
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String applicationChName;

    /* ---------------------------- 服务所在部门信息 ---------------------------- */

    /**
     * 服务所在部门ID
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String orgId;

    /**
     * 服务所在部门名
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String orgName;

    /* ---------------------------- 服务运营属性类信息 ---------------------------- */

    /**
     * 服务类型（前端、后端、Maven发布项、虚拟......）
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String type;

    /**
     * 服务所在结算单元ID
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String billingUnitId;

    /**
     * 服务所在结算单元名称
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String billingUnit;

    /**
     * 服务端
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String categories;

    /**
     * 服务标签
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String tags;

    /**
     * 服务框架
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String frameworks;

    /**
     * 服务是否外采
     * 1.TRUE
     * 2.FALSE
     * 3.UNCERTAIN（不确定）
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private String isBoughtExternal;

    /**
     * 服务是否为PAAS
     */
    @SourceBy(SourceName.SC)
    private Boolean paas;

    /* ---------------------------- 服务时间类信息 ---------------------------- */

    /**
     * 服务创建时间
     * 数据源：OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private Date createTime;

    /**
     * 服务信息更新时间
     * 数据源：SC
     */
    @SourceBy(SourceName.SC)
    private Date updateTime;

    /**
     * 服务删除时间
     * 数据源：SC/OPS
     */
    @SourceBy({SourceName.OPS, SourceName.SC})
    private Date offlineTime;

    public Appkey(String appkey){
        this.appkey = appkey;
    }

    public Appkey(CollectEventData collectEventData){
        // 被动接受数据
        this.appkey = collectEventData.getAppkey();
    }

    public List<Field> getSourceFields(SourceName sourceName) {
        List<Field> fieldList = new ArrayList<>();
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            SourceBy sourceBy = field.getAnnotation(SourceBy.class);
            if (sourceBy == null) {
                continue;
            }
            if (Arrays.asList(field.getAnnotation(SourceBy.class).value()).contains(sourceName)) {
                fieldList.add(field);
            }
        }
        return fieldList;
    }
}
