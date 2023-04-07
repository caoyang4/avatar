package com.sankuai.avatar.resource.orgtree.bo;

import com.sankuai.avatar.client.soa.model.ScOrgTreeNode;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * OrgTree节点BO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/15
 **/
@Data
@Builder
public class OrgTreeNodeBO {
    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 下层子节点
     */
    private List<OrgTreeNodeBO> children;

    /**
     * 显示中文名称
     */
    private String displayName;

    /**
     * 是否有子节点
     */
    private Boolean hasChild;

    /**
     * 父节点
     */
    private List<OrgTreeNodeBO> ancestors;

}
