package com.sankuai.avatar.dao.es.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 */

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class AppkeyQueryRequest extends PageRequest {

    /**
     * 服务所在服务树节点
     */
    private String srv;

    /**
     * 业务appkey
     */
    private String appkey;

    /**
     * 服务描述信息
     */
    private String description;

    /**
     * 部门
     */
    private String owt;

    /**
     * 产品线
     */
    private String pdl;

    /**
     * 服务所在应用名称
     */
    private String applicationName;

    /**
     * 部门ID
     */
    private String orgId;

    /**
     * 服务所在部门名
     */
    private String orgName;

    /**
     * 服务类型：语言/组件
     */
    private String serviceType;

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
     * 服务隔离环境标识
     */
    private String tenant;

    /**
     * git仓库
     */
    private String gitRepository;

    /**
     * 服务类型（前端、后端、Maven发布项、虚拟......）
     */
    private String type;

    /**
     * 应用ID
     */
    private String applicationId;

    /**
     * 服务所在结算单元ID
     */
    private String billingUnitId;

    /**
     * 服务所在结算单元名称
     */
    private String billingUnit;

    /**
     * 服务状态：有状态、无状态
     */
    private Boolean stateful;

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

}
