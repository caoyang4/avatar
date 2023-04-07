package com.sankuai.avatar.web.service;


import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.web.dto.orgRole.DxGroupDTO;
import com.sankuai.avatar.web.dto.orgRole.OrgRoleAdminDTO;
import com.sankuai.avatar.web.dto.orgRole.OrgSreTreeDTO;
import com.sankuai.avatar.web.request.OrgRoleAdminPageRequest;
import com.sankuai.avatar.web.vo.orgRole.OrgRoleAdminVO;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-03-18
 **/
public interface OrgRoleAdminService {

    /**
     * 通过 mis 和 orgid 获取部门运维负责人配置树
     *
     * @param mis   mis号
     * @param orgId org id
     * @return {@link List}<{@link OrgSreTreeDTO}>
     */
    List<OrgSreTreeDTO> getOrgSreTreeListByOrgId(String mis, String orgId);

    /**
     * 分页查询
     *
     * @param request 请求
     * @return {@link PageResponse}<{@link OrgRoleAdminVO}>
     */
    PageResponse<OrgRoleAdminDTO> queryPage(OrgRoleAdminPageRequest request);

    /**
     * 获取所有dx群
     *
     * @return {@link List}<{@link DxGroupDTO}>
     */
    List<DxGroupDTO> getAllDxGroup();

    /**
     * 获取部门大象群
     *
     * @param groupList dx群列表
     * @return {@link List}<{@link DxGroupDTO}>
     */
    List<DxGroupDTO> getDxGroupByGroupIds(List<String> groupList);

    /**
     * 保存组织角色管理
     *
     * @param orgRoleAdminDTO orgRoleAdminDTO
     * @param deleteChildren 是否清理子组织节点
     * @return boolean
     */
    boolean saveOrgRoleAdmin(OrgRoleAdminDTO orgRoleAdminDTO, Boolean deleteChildren);

    /**
     * 保存org dx群
     *
     * @param orgId          org id
     * @param dxGroupDTOList dxGroupDTOList
     * @return boolean
     */
    boolean saveOrgDxGroup(String orgId, List<DxGroupDTO> dxGroupDTOList);

    /**
     * 通过org id和角色获取org 信息
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link OrgRoleAdminDTO}
     */
    OrgRoleAdminDTO getByOrgIdAndRole(String orgId, OrgRoleType role);

    /**
     * 根据 orgId 和角色获取子组织
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link List}<{@link OrgRoleAdminDTO}>
     */
    List<OrgRoleAdminDTO> getChildrenOrgByOrgId(String orgId, OrgRoleType role);

    /**
     * 根据 orgId 和角色获取父组织
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link OrgRoleAdminDTO}
     */
    OrgRoleAdminDTO getAncestorOrgByOrgId(String orgId, OrgRoleType role);


}
