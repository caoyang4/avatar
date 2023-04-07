package com.sankuai.avatar.resource.appkey.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author caoyang
 * @create 2022-12-12 13:36
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class AppkeyRequestBO extends PageRequest {

    /**
     * 服务所在服务树节点
     */
    private String srv;

    /**
     * 业务appkey
     */
    private String appkey;

    /**
     * 部门
     */
    private String owt;

    /**
     * 产品线
     */
    private String pdl;

    /**
     * 部门ID
     */
    private String orgId;

    /**
     * 服务所在结算单元ID
     */
    private String billingUnitId;

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

}
