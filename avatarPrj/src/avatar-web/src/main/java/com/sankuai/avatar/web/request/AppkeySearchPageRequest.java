package com.sankuai.avatar.web.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author caoyang
 * @create 2022-12-14 14:31
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class AppkeySearchPageRequest extends PageRequest {

    /**
     * 用户MIS
     */
    private String user;

    /**
     * 查询类型
     */
    private String type;

    /**
     * 检索关键字
     */
    private String query;

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
     * 服务所在应用名称
     */
    private String applicationName;

    /**
     * 部门ID
     */
    private String orgId;

    /**
     * 服务类型：语言/组件
     */
    private String serviceType;

    /**
     * 服务隔离环境标识
     */
    private String tenant;

    /**
     * 应用ID
     */
    private String applicationId;

    /**
     * 服务状态：有状态、无状态
     */
    private Boolean stateful;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 服务容灾等级:支持多个以逗号分隔
     */
    private String capacity;

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
