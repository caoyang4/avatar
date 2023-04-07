package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.OwtSetWhitelistDO;
import com.sankuai.avatar.dao.resource.repository.request.OwtSetWhitelistRequest;

import java.util.List;

/**
 * owt 下 set 的白名单数据管理
 * @author caoyang
 * @create 2022-10-21 15:37
 */
public interface OwtSetWhitelistRepository {

    /**
     * 根据条件对象查询 owt-set 白名单
     * @param owtSetWhitelistRequest 请求对象
     * @return DOList
     */
    List<OwtSetWhitelistDO> query(OwtSetWhitelistRequest owtSetWhitelistRequest);

    /**
     * 新增 owt-set 白名单
     * @param owtSetWhitelistDO DO
     * @return 是否成功
     */
    boolean insert(OwtSetWhitelistDO owtSetWhitelistDO);

    /**
     * 更新 owt-set 白名单
     * @param owtSetWhitelistDO DO
     * @return 是否成功
     */
    boolean update(OwtSetWhitelistDO owtSetWhitelistDO);

    /**
     * 删除 owt-set 白名单
     * @param id 主键 id
     * @return 是否成功
     */
    boolean delete(int id);
}
