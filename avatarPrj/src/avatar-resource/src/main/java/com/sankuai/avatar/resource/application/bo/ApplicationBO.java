package com.sankuai.avatar.resource.application.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.sankuai.avatar.client.soa.model.ScOrgTreeNode;
import com.sankuai.avatar.client.soa.model.ScUser;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 应用信息BO
 *
 * @author zhangxiaoning07
 * @create 2022/11/23
 **/
@Data
@Builder
public class ApplicationBO {
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
     * 是否公共服务
     */
    private Boolean isPublic;

    /**
     * 所属组织
     */
    private OrgTreeNodeBO team;

    /**
     * 负责人
     */
    private OrgTreeUserBO admin;

    /**
     * 服务数量
     */
    private Integer appKeyCount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
