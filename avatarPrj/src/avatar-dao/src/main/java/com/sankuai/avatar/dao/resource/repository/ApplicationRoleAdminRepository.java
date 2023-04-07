package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.ApplicationRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.ApplicationRoleAdminRequest;

import java.util.List;

/**
 * 应用测试/运维角色负责人管理
 * @author caoyang
 * @create 2023-01-11 14:32
 */
public interface ApplicationRoleAdminRepository {

    /**
     * 查询
     *
     * @param request 请求
     * @return {@link List}<{@link ApplicationRoleAdminDO}>
     */
    List<ApplicationRoleAdminDO> query(ApplicationRoleAdminRequest request);

    /**
     * 新增
     *
     * @param applicationRoleAdminDO applicationRoleAdminDO
     * @return boolean
     */
    boolean insert(ApplicationRoleAdminDO applicationRoleAdminDO);

    /**
     * 更新
     *
     * @param applicationRoleAdminDO applicationRoleAdminDO
     * @return boolean
     */
    boolean update(ApplicationRoleAdminDO applicationRoleAdminDO);

    /**
     * 删除
     *
     * @param id id
     * @return boolean
     */
    boolean delete(int id);

}
