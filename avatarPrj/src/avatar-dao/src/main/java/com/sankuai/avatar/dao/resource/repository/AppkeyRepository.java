package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyRequest;

import java.util.List;

/**
 * Appkey 数据管理
 *
 * @author qinwei05
 * @date 2022/10/28
 */
public interface AppkeyRepository {

    /**
     * 查询对象
     * @param appkeyRequest 查询对象
     * @return do list
     */
    List<AppkeyDO> query(AppkeyRequest appkeyRequest);

    /**
     * 新增
     * @param appkeyDO  do
     * @return 是否写入成功
     */
    boolean insert(AppkeyDO appkeyDO);

    /**
     * 批量新增
     * @param appkeyDOList list
     * @return 生效数量
     */
    int insertBatch(List<AppkeyDO> appkeyDOList);

    /**
     * 更新
     * @param appkeyDO do
     * @return 是否写入成功
     */
    boolean update(AppkeyDO appkeyDO);

    /**
     * 根据特定条件删除数据
     * @param id 主键
     * @return 是否删除成功
     */
    boolean delete(int id);
}
