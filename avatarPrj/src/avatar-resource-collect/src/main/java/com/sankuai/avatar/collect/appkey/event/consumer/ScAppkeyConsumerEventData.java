package com.sankuai.avatar.collect.appkey.event.consumer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * appkey事件对象
 *
 * @author qinwei05
 * @date 2022/11/04
 */
@Data
public class ScAppkeyConsumerEventData {

    /**
     * 服务名
     */
    private String appKey;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 服务Owner
     */
    private Admin admin;

    /**
     * 团队
     */
    private Team team;

    /**
     * 成员
     */
    private List<Members> members;

    /**
     * 验证状态
     */
    private String validateState;

    /**
     * 资格类型
     */
    private String qualificationType;

    /**
     * 应用id
     */
    private int applicationId;

    /**
     * 应用负责人
     */
    private ApplicationAdmin applicationAdmin;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 应用中文名字
     */
    private String applicationChName;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 服务名称
     */
    private String serviceName;

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
     * 服务是否下线
     */
    private Boolean isOffline;

    /**
     * 服务删除时间
     */
    private Date offlineTime;

    /**
     * 服务等级
     */
    private String serviceLevel;

    /**
     * 结算单元
     */
    private String billingUnit;

    /**
     * 结算单元id
     */
    private Integer billingUnitId;

    /**
     * git存储地址
     */
    private String gitRepository;

    /**
     * 有服务状态：有状态、无状态
     */
    private Boolean stateful;

    /**
     * 预装软件
     */
    private String containerType;

    /**
     * 操作类型
     * <p>
     * ADD：新增 UPDATE：修改 DELETE：下线
     */
    private String operation;

    /**
     * 子结算单元id
     */
    private int subBillingUnitId;

    /**
     * 服务类型：语言/组件
     */
    private String serviceType;

    /**
     * 应用负责人
     */
    @Data
    public static class ApplicationAdmin {
        private String mis;
        private String name;
    }

    /**
     * 服务负责人
     */
    @Data
    public static class Members {
        private String mis;
        private String name;
    }

    /**
     * 服务团队
     */
    @Data
    public static class Team {
        private String id;
        private String name;
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
    }
}
