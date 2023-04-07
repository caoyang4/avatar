package com.sankuai.avatar.sdk.entity.servicecatalog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {
    /**
     * id，唯一标识
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 中文名称
     */
    private String chName;
    /**
     * 服务数量
     */
    private Integer appKeyTotal;
    /**
     * 是否公共服务
     */
    private Boolean isPublic;
    /**
     * 应用访问链接
     */
    private String portalLink;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 域名
     */
    private List<String> domains;
    /**
     * 计费单元
     */
    private String billingUnitName;
    /**
     * 付费产品
     */
    private String productName;
    /**
     * 所属组织
     */
    private Org team;
    /**
     * 负责人
     */
    private User admin;
    /**
     * 产品经理
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<User> pms;
    /**
     * 团队成员
     */
    private AggregatedUsers adminTeam;
    /**
     * 其他团队
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<AggregatedUsers> participatedTeams;
    /**
     * 服务数量
     */
    private Integer appKeyCount;
}
