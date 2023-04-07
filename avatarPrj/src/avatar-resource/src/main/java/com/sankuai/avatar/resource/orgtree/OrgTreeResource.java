package com.sankuai.avatar.resource.orgtree;

import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeOrgInfoBO;

import java.util.List;

/**
 * OrgTreeResource接口
 *
 * @author zhangxiaoning07
 * @create 2022/11/15
 **/
public interface OrgTreeResource {

    /**
     * 获取组织树
     *
     * @param user 用户mis号
     * @return 组织树，树节点为OrgTreeNodeBO对象
     */
    List<OrgTreeNodeBO> getOrgTree(String user);

    /**
     * 获取部门信息
     *
     * @param orgIds 逗号隔开的部门节点id的字符串
     * @return 部门信息
     */
    OrgTreeOrgInfoBO getOrgInfo(String orgIds);
}
