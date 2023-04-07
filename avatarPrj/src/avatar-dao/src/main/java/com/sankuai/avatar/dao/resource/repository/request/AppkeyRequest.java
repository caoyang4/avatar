package com.sankuai.avatar.dao.resource.repository.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 服务条件查询对象
 *
 * @author qinwei05
 * @date 2022/10/28
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppkeyRequest {

    /**
     * 服务所在服务树节点
     */
    private String srv;

    /**
     * 业务appkey
     */
    private String appkey;

    /**
     * appkeys
     */
    private List<String> appkeys;

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

    /**
     * 服务负责人
     */
    private String owner;


    public AppkeyRequest(String appkey){
        this.appkey = appkey;
    }
}
