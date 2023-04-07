package com.sankuai.avatar.resource.application.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 应用详细信息BO
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@Data
public class ApplicationDetailBO {
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
    private OrgTreeNodeBO team;

    /**
     * 负责人
     */
    private OrgTreeUserBO admin;

    /**
     * 产品经理
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<OrgTreeUserBO> pms;

    /**
     * 团队成员
     */
    private TeamBO adminTeam;

    /**
     * 其他团队
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<TeamBO> participatedTeams;

    /**
     * 服务数量
     */
    private Integer appKeyCount;
}

