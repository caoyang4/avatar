package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.EmergencySreDO;
import com.sankuai.avatar.dao.resource.repository.request.EmergencySreRequest;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-17 15:35
 */
public interface EmergencySreRepository {

    /**
     * 查询
     *
     * @param request 请求
     * @return {@link List}<{@link EmergencySreDO}>
     */
    List<EmergencySreDO> query(EmergencySreRequest request);

    /**
     * 新增
     *
     * @param emergencySreDO emergencySreDO
     * @return {@link Boolean}
     */
    Boolean insert(EmergencySreDO emergencySreDO);

    /**
     * 更新
     *
     * @param emergencySreDO emergencySreDO
     * @return {@link Boolean}
     */
    Boolean update(EmergencySreDO emergencySreDO);

    /**
     * 删除
     *
     * @param id 主键
     * @return {@link Boolean}
     */
    Boolean delete(int id);

}
