package com.sankuai.avatar.dao.cache;

import com.sankuai.avatar.dao.cache.exception.CacheException;
import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;

import java.util.List;

/**
 * orgRoleAdmin缓存接口
 * @author caoyang
 * @create 2022-11-01 21:51
 */
public interface OrgRoleAdminCacheRepository {

    /**
     * orgRoleAdmin缓存查询运维角色
     *
     * @param orgId org id
     * @return {@link OrgRoleAdminDO}
     */
    OrgRoleAdminDO getOpRole(String orgId) throws CacheException;

    /**
     * orgRoleAdmin缓存查询测试角色
     *
     * @param orgId org id
     * @return {@link OrgRoleAdminDO}
     */
    OrgRoleAdminDO getEpRole(String orgId) throws CacheException;

    /**
     * 得到
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link OrgRoleAdminDO}
     */
    OrgRoleAdminDO get(String orgId, String role) throws CacheException;

    /**
     * 多得到op作用
     * orgRoleAdmin缓存批量查询运维角色
     *
     * @param orgIds org id
     * @return {@link List}<{@link OrgRoleAdminDO}>
     * @throws CacheException 缓存异常
     */
    List<OrgRoleAdminDO> multiGetOpRole(List<String> orgIds) throws CacheException;

    /**
     * 多得到ep作用
     * orgRoleAdmin缓存批量查询测试角色
     *
     * @param orgIds org id
     * @return {@link List}<{@link OrgRoleAdminDO}>
     * @throws CacheException 缓存异常
     */
    List<OrgRoleAdminDO> multiGetEpRole(List<String> orgIds) throws CacheException;

    /**
     * 集
     * orgRoleAdmin缓存设置
     *
     * @param orgRoleAdminDO 组织角色管理
     * @param expireTime     到期时间
     * @return boolean
     * @throws CacheException 缓存异常
     */
    boolean set(OrgRoleAdminDO orgRoleAdminDO, int expireTime) throws CacheException;

    /**
     * 多组
     * orgRoleAdmin缓存批量设置
     *
     * @param orgRoleAdminDOList org角色管理dolist
     * @param expireTime         到期时间
     * @return boolean
     * @throws CacheException 缓存异常
     */
    boolean multiSet(List<OrgRoleAdminDO> orgRoleAdminDOList, int expireTime) throws CacheException;

    /**
     * org角色用户
     * 缓存 org 的运维/测试负责人
     *
     * @param orgId      org id
     * @param role       角色
     * @param roleUsers  用户
     * @param expireTime 到期时间
     * @return boolean
     * @throws CacheException 缓存异常
     */
    boolean setOrgRoleUsers(String orgId, String role, String roleUsers, int expireTime) throws CacheException;

    /**
     * 得到组织角色用户
     * 缓存查询 org 的运维/测试负责人
     *
     * @param orgId org id
     * @param role  角色
     * @return {@link String}
     * @throws CacheException 缓存异常
     */
    String getOrgRoleUsers(String orgId, String role) throws CacheException;
}
