package com.sankuai.avatar.collect.appkey.collector.source;

import com.sankuai.avatar.collect.appkey.collector.transfer.CollectorSourceDataTransfer;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * OPS数据对象
 *
 * @author qinwei05
 * @create 2022-11-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OpsAppkeySource extends AppkeySource {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 服务中文别名
     */
    private String name;

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
     * 服务所在服务树节点
     */
    private String srv;

    /**
     * 产品线
     */
    private String pdl;

    /**
     * 部门owt
     */
    private String owt;

    /**
     * 技术团队
     */
    private String businessGroup;

    /**
     * 产品线中文名称
     */
    private String pdlName;

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
     * 服务类型（原上海侧字段）：WEB、SERVICE
     * 数据源：OPS
     */
    private String projectType;

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
    private Date capacityUpdateAt;

    /**
     * 服务容灾等级更新人
     */
    private String capacityUpdateBy;

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

    /**
     * 服务创建时间
     */
    private Date createTime;

    /**
     * 服务是否下线
     */
    private Boolean isOffline;

    /**
     * 服务删除时间
     */
    private Date offlineTime;

    @Override
    public SourceName getSourceName() {
        return SourceName.OPS;
    }

    @Override
    public Appkey transToAppkey(Appkey appkey){
        return CollectorSourceDataTransfer.INSTANCE.toAppkey(this, appkey);
    }
}
