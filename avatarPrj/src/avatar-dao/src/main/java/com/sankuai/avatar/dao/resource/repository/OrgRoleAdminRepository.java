package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.OrgRoleAdminRequest;

import java.util.List;

/**
 * orgRoleAdmin管理接口
 * @author caoyang
 * @create 2022-11-01 19:56
 */
public interface OrgRoleAdminRepository {

    /**
     * 查询OrgRoleAdmin
     *
     * @param orgRoleAdminRequest 请求对象
     * @return {@link List}<{@link OrgRoleAdminDO}>
     */
    List<OrgRoleAdminDO> query(OrgRoleAdminRequest orgRoleAdminRequest);

    /**
     * 新增OrgRoleAdmin
     *
     * @param orgRoleAdminDO orgRoleAdminDO
     * @return boolean
     */
    boolean insert(OrgRoleAdminDO orgRoleAdminDO);

    /**
     * 更新OrgRoleAdmin
     *
     * @param orgRoleAdminDO orgRoleAdminDO
     * @return boolean
     */
    boolean update(OrgRoleAdminDO orgRoleAdminDO);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return boolean
     */
    boolean delete(int id);
}
