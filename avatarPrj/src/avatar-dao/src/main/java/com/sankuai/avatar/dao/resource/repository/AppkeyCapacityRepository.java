package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyCapacityDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyCapacityRequest;

import java.util.List;

/**
 * Appkey 业务容灾等级数据管理
 * @author caoyang
 * @create 2022-11-03 14:27
 */
public interface AppkeyCapacityRepository {

    /**
     * 查询
     *
     * @param appkeyCapacityRequest 请求对象
     * @return {@link List}<{@link AppkeyCapacityDO}>
     */
    List<AppkeyCapacityDO> query(AppkeyCapacityRequest appkeyCapacityRequest);

    /**
     * 新增
     *
     * @param appkeyCapacityDO appkeyCapacityDO
     * @return boolean
     */
    boolean insert(AppkeyCapacityDO appkeyCapacityDO);

    /**
     * 更新
     *
     * @param appkeyCapacityDO appkeyCapacityDO
     * @return boolean
     */
    boolean update(AppkeyCapacityDO appkeyCapacityDO);

    /**
     * 删除
     *
     * @param id id
     * @return boolean
     */
    boolean delete(int id);
}
