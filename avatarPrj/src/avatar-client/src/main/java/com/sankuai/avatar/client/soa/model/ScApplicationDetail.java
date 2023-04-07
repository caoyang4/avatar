package com.sankuai.avatar.client.soa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * SC接口返回的Application的详细信息对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScApplicationDetail {
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
    private ScOrgTreeNode team;

    /**
     * 负责人
     */
    private ScUser admin;

    /**
     * 产品经理
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<ScUser> pms;

    /**
     * 团队成员
     */
    private ScTeam adminTeam;

    /**
     * 其他团队
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<ScTeam> participatedTeams;
}
