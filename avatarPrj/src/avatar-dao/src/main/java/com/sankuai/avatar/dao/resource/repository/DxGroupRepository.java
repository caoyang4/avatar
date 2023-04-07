package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.DxGroupDO;

import java.util.List;

/**
 * 大象群数据管理接口
 * @author caoyang
 * @create 2022-11-01 19:56
 */
public interface DxGroupRepository {

    /**
     * 查询所有群
     *
     * @return {@link List}<{@link DxGroupDO}>
     */
    List<DxGroupDO> queryAllGroup();

    /**
     * 根据群 id 批量查询
     *
     * @param groupIds 群id列表
     * @return {@link List}<{@link DxGroupDO}>
     */
    List<DxGroupDO> queryByGroupId(List<String> groupIds);

    /**
     * 新增大象群信息
     *
     * @param dxGroupDO dxGroupDO
     * @return boolean
     */
    boolean insert(DxGroupDO dxGroupDO);

    /**
     * 更新大象群信息
     *
     * @param dxGroupDO dxGroupDO
     * @return boolean
     */
    boolean update(DxGroupDO dxGroupDO);
}
