package com.sankuai.avatar.dao.workflow.repository;

import com.sankuai.avatar.dao.workflow.repository.entity.PermissionEntity;
import com.sankuai.avatar.dao.workflow.repository.request.PermissionRequest;

import java.util.List;

/**
 * 权限数据管理
 *
 * @author zhaozhifan02
 */
public interface PermissionRepository {
    /**
     * 查询权限对象
     *
     * @param request 查询对象
     * @return List<PermissionDO>
     */
    List<PermissionEntity> query(PermissionRequest request);
}

