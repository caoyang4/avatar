package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.WhitelistAppDO;

import java.util.List;

/**
 * 白名单类型管理接口
 * @author caoyang
 * @create 2022-11-02 21:44
 */
public interface WhitelistAppRepository {

    /**
     * 全量查询白名单类型
     *
     * @return {@link List}<{@link WhitelistAppDO}>
     */
    List<WhitelistAppDO> query();

    /**
     * 新增白名单类型
     *
     * @param whitelistAppDO 白名单类型对象
     * @return boolean
     */
    boolean insert(WhitelistAppDO whitelistAppDO);

    /**
     * 更新白名单类型
     *
     * @param whitelistAppDO 白名单类型对象
     * @return boolean
     */
    boolean update(WhitelistAppDO whitelistAppDO);
}
