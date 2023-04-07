package com.sankuai.avatar.client.soa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * sc appkey
 *
 * @author qinwei05
 * @date 2022/11/07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScIsoltAppkey {
    /**
     * 服务名称
     */
    private String appKey;

    /**
     * 描述
     */
    private String description;

    /**
     * 主机数
     */
    private Integer host;

    /**
     * 容灾等级
     */
    private Integer capacity;

    /**
     * 服务Owner
     */
    private Admin admin;

    /**
     * 服务负责人
     */
    private List<Members> members;

    /**
     * 团队
     */
    private Team team;

    /**
     * 语言组件
     */
    private String language;

    /**
     * 类别
     */
    private List<String> categories;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 服务框架
     */
    private List<String> frameworks;

    /**
     * 服务隔离环境标识
     * 数据源：OPS
     */
    private String tenant;

    /**
     * 服务是否外采
     * 1.TRUE
     * 2.FALSE
     * 3.UNCERTAIN（不确定）
     */
    private String isBoughtExternal;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 服务等级
     */
    private String serviceLevel;

    /**
     * 结算单元
     */
    private String billingUnitName;

    /**
     * 结算单元id
     */
    private Integer billingUnitId;

    /**
     * 应用中文名字
     */
    private String applicationName;

    /**
     * 预装软件
     */
    private String containerType;

    /**
     * 服务状态
     */
    private Boolean stateful;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 前端类型
     */
    private String frontendType;

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
     * 服务是否兼容ipv6
     */
    private Boolean compatibleIpv6;


    /**
     * 服务负责人(Owner)
     */
    @Data
    public static class Members {
        private String mis;
        private String name;
        private Boolean onJob;
    }

    /**
     * 服务团队
     */
    @Data
    public static class Team {
        private String id;
        private String name;
        private Boolean hasChild;
        private String displayName;
        private String orgIdList;
    }

    /**
     * 服务团队
     */
    @Data
    public static class Admin {
        private String mis;
        private String name;
        private Boolean onJob;
    }
}
