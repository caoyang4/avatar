package com.sankuai.avatar.collect.appkey.process.request;

import lombok.Data;

/**
 * Appkey查询对象
 * @author qinwei05
 */
@Data
public class AppkeyDbQueryRequest{

    /* ---------------------------- 服务基础信息 ---------------------------- */

    /**
     * 服务名
     */
    private String appkey;

    /**
     * 服务中文别名
     */
    private String name;

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
     * 服务等级：核心、非核心
     */
    private String rank;

    /**
     * 服务状态：有状态、无状态
     */
    private Boolean stateful;

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
     * 服务是否下线
     */
    private Boolean isOffline;

    /**
     * 服务容灾等级
     */
    private Integer capacity;

    /**
     * 服务所在应用ID
     */
    private Integer applicationId;

    /**
     * 服务所在应用名称
     */
    private String applicationName;

    /**
     * 服务所在部门ID
     */
    private String orgId;

    /**
     * 服务类型（前端、后端、Maven发布项、虚拟......）
     */
    private String type;

    public AppkeyDbQueryRequest(String appkey){
        this.appkey = appkey;
    }
}
