package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.EmergencyResourceDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencyResourceRequest;

import java.util.List;

/**
 * 紧急资源管理接口
 * @author caoyang
 * @create 2022-11-25 23:24
 */
public interface EmergencyResourceRepository {

    /**
     * 查询
     *
     * @param request 请求
     * @return {@link List}<{@link EmergencyResourceDO}>
     */
    List<EmergencyResourceDO> query(EmergencyResourceRequest request);

    /**
     * 保存
     *
     * @param emergencyResourceDO 应急资源做
     * @return {@link Boolean}
     */
    Boolean insert(EmergencyResourceDO emergencyResourceDO);

    /**
     * 删除
     *
     * @param pk pk
     * @return {@link Boolean}
     */
    Boolean delete(int pk);
}
