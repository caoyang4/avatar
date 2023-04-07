package com.sankuai.avatar.resource.orgRole;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.orgRole.bo.OrgBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgRoleAdminBO;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.resource.orgRole.request.OrgRoleAdminRequestBO;

import java.util.List;
import java.util.Map;

/**
 * OrgRoleAdmin 资源管理接口
 * @author caoyang
 * @create 2022-11-10 13:59
 */
public interface OrgRoleAdminResource {

    /**
     * 查询页面
     *
     * @param requestBO 请求
     * @return {@link PageResponse}<{@link OrgRoleAdminBO}>
     */
    PageResponse<OrgRoleAdminBO> queryPage(OrgRoleAdminRequestBO requestBO);

    /**
     *  优先缓存查询org运维负责人角色
     *
     * @param orgIds org编号列表
     * @return {@link List}<{@link OrgRoleAdminBO}>
     */
    List<OrgRoleAdminBO> queryOrgOpRoles(List<String> orgIds);

    /**
     * 不经过缓存查询org运维负责人信息
     *
     * @param orgIds org id
     * @return {@link List}<{@link OrgRoleAdminBO}>
     */
    List<OrgRoleAdminBO> queryOrgOpRolesWithNoCache(List<String> orgIds);

    /**
     * 优先缓存查询org测试负责人信息
     *
     * @param orgIds org编号列表
     * @return {@link List}<{@link OrgRoleAdminBO}>
     */
    List<OrgRoleAdminBO> queryOrgEpRoles(List<String> orgIds);

    /**
     * 不经过缓存查询org测试负责人信息
     *
     * @param orgIds org id
     * @return {@link List}<{@link OrgRoleAdminBO}>
     */
    List<OrgRoleAdminBO> queryOrgEpRolesWithNoCache(List<String> orgIds);

    /**
     * 通过org id和角色
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link OrgRoleAdminBO}
     */
    OrgRoleAdminBO getByOrgIdAndRole(String orgId, OrgRoleType role);

    /**
     * 获取父组织
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link OrgRoleAdminBO}
     */
    OrgRoleAdminBO getAncestorOrgRole(String orgId, OrgRoleType role);

    /**
     * 获取子组织
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link List}<{@link OrgRoleAdminBO}>
     */
    List<OrgRoleAdminBO> getChildrenOrgRole(String orgId, OrgRoleType role);

    /**
     * 保存
     *
     * @param orgRoleAdminBO orgRoleAdminBO
     * @return boolean
     */
    boolean save(OrgRoleAdminBO orgRoleAdminBO);

    /**
     * 缓存orgRole
     *
     * @param orgRoleAdminBOList org角色管理bolist
     * @return boolean
     */
    boolean cacheOrgRoleAdminBO(List<OrgRoleAdminBO> orgRoleAdminBOList);

    /**
     * 根据条件删除
     *
     * @param requestBO 请求
     * @return boolean
     */
    boolean deleteByCondition(OrgRoleAdminRequestBO requestBO);

    /**
     * 被org组织客户
     * 通过 org sdk 获取 org
     *
     * @param orgId org id
     * @return {@link OrgBO}
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OrgBO getOrgByOrgClient(String orgId) throws SdkBusinessErrorException;

    /**
     * 优先缓存获取部门的运维/测试负责人
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link String}
     */
    String getRoleUsers(String orgId, OrgRoleType role);

    /**
     * 获取role 用户
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link Map}<{@link String},{@link String}>
     */
    Map<String,String> getRoleUserMap(String orgId, OrgRoleType role);

    /**
     * 不经过缓存获取部门的运维/测试负责人
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link String}
     */
    String getRoleUsersNoCache(String orgId, OrgRoleType role);

    /**
     * 缓存用户角色
     *
     * @param orgId org id
     * @param role  角色
     * @return boolean
     */
    boolean cacheRoleUsers(String orgId, OrgRoleType role);

    /**
     * 缓存用户角色
     *
     * @param orgId     org id
     * @param role      角色
     * @param roleUsers 用户角色
     * @return boolean
     */
    boolean cacheRoleUsers(String orgId, OrgRoleType role, String roleUsers);
}
