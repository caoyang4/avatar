package com.sankuai.avatar.web.service;

import com.sankuai.avatar.web.dto.orgtree.OrgTreeNodeDTO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeOrgInfoDTO;

import java.util.List;

/**
 * OrgTree的Service
 *
 * @author zhangxiaoning07
 * @create 2022-11-15
 */
public interface OrgTreeService {
    /**
     * 获取组织树
     *
     * @param user 用户的mis号
     * @return 组织树，树节点为OrgTreeNodeDTO对象
     */
    List<OrgTreeNodeDTO> getOrgTree(String user);

    /**
     * 获取部门信息
     *
     * @param orgIds 逗号隔开的部门节点id的字符串
     * @return 部门信息
     */
    OrgTreeOrgInfoDTO getOrgInfo(String orgIds);
}
