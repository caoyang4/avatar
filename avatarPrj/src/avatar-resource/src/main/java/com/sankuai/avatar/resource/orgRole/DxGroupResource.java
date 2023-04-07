package com.sankuai.avatar.resource.orgRole;

import com.sankuai.avatar.resource.orgRole.bo.DxGroupBO;

import java.util.List;

/**
 * 部门大象群资源管理接口
 * @author caoyang
 * @create 2022-11-10 15:44
 */
public interface DxGroupResource {

    /**
     * 获取所有dx群
     *
     * @return {@link List}<{@link DxGroupBO}>
     */
    List<DxGroupBO> getAllDxGroup();

    /**
     * 根据 groupId 批量查询
     *
     * @param groupIds groupIds
     * @return {@link List}<{@link DxGroupBO}>
     */
    List<DxGroupBO> getDxGroupByGroupIds(List<String> groupIds);

    /**
     * 保存
     *
     * @param dxGroupBO dxGroupBO
     * @return boolean
     */
    boolean save(DxGroupBO dxGroupBO);

    /**
     * 批量保存
     *
     * @param dxGroupBOList dxGroupBOList
     * @return boolean
     */
    boolean batchSave(List<DxGroupBO> dxGroupBOList);
}
