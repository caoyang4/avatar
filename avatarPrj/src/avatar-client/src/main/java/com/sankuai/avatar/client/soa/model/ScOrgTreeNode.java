package com.sankuai.avatar.client.soa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * SC接口返回的OrgTreeNode对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/10
 **/
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScOrgTreeNode {
    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 显示中文名称
     */
    private String displayName;

    /**
     * 是否有子节点
     */
    private Boolean hasChild;

    /**
     * 下层子节点
     */
    private List<ScOrgTreeNode> children;

    /**
     * 父节点
     */
    private List<ScOrgTreeNode> ancestors;

}

