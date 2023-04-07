package com.sankuai.avatar.collect.appkey.collector.source;

import com.sankuai.avatar.collect.appkey.collector.transfer.CollectorSourceDataTransfer;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * sc 非后端appkey对象
 *
 * @author qinwei05
 * @date 2022/12/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScNotBackendAppkeySource extends AppkeySource {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 服务描述信息
     */
    private String description;

    /**
     * 服务等级：核心、非核心
     * 前端
     */
    private String rank;

    /**
     * 预装软件
     * 前端
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
     * 服务owner
     */
    private String admin;

    /**
     * 服务EP
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

    /**
     * git仓库
     */
    private String gitRepository;

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

    /**
     * 服务所在部门ID
     */
    private String orgId;

    /**
     * 服务所在部门名
     */
    private String orgName;

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
     * 服务框架:V1接口获取
     */
    private String frameworks;

    /**
     * 服务隔离环境标识:V1接口获取
     */
    private String tenant;

    /**
     * 服务是否外采:V1接口获取
     * 1.TRUE
     * 2.FALSE
     * 3.UNCERTAIN（不确定）
     */
    private String isBoughtExternal;

    /**
     * 服务是否为PAAS
     */
    private Boolean paas;

    /**
     * 服务是否下线
     */
    private Boolean isOffline;

    /**
     * 服务创建时间
     */
    private Date createTime;

    /**
     * 服务信息更新时间
     */
    private Date updateTime;

    /**
     * 服务删除时间
     */
    private Date offlineTime;

    @Override
    public SourceName getSourceName() {
        return SourceName.SC;
    }

    @Override
    public Appkey transToAppkey(Appkey appkey){
        return CollectorSourceDataTransfer.INSTANCE.toAppkey(this, appkey);
    }
}
