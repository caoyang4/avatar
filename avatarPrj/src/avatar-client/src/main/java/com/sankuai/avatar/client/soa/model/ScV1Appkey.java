package com.sankuai.avatar.client.soa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * sc appkey
 *
 * @author qinwei05
 * @date 2022/11/07
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScV1Appkey {
    /**
     * 服务名称
     */
    private String appKey;

    /**
     * 描述
     */
    private String description;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 应用管理员
     */
    private ApplicationAdmin applicationAdmin;

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
     * 验证状态
     */
    private String validateState;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 类型
     */
    private String type;

    /**
     * 服务等级
     */
    private String serviceLevel;

    /**
     * 应用id
     */
    private Integer applicationId;

    /**
     * 结算单元
     */
    private String billingUnit;

    /**
     * 结算单元id
     */
    private Integer billingUnitId;

    /**
     * 应用中文名字
     */
    private String applicationChName;

    /**
     * git存储地址
     */
    private String gitRepository;

    /**
     * 预装软件
     */
    private String containerType;

    /**
     * 服务状态
     */
    private Boolean stateful;

    /**
     * 是服务
     */
    private Boolean isService;

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
     * 应用负责人
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApplicationAdmin {
        private String mis;
        private String name;
        private Boolean onJob;
    }

    /**
     * 服务负责人
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Members {
        private String mis;
        private String name;
        private Boolean onJob;
    }

    /**
     * 服务团队
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Admin {
        private String mis;
        private String name;
        private Boolean onJob;
    }
}
