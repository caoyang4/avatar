package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.UserDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRequest;

import java.util.List;

/**
 * Appkey Paas层容灾等级数据管理
 * @author caoyang
 * @create 2022-10-19 12:06
 */
public interface UserRepository {
    /**
     * 根据查询条件查询 user 列表
     * @param userRequest 请求对象
     * @return user列表
     */
    List<UserDO> query(UserRequest userRequest);

    /**
     * 新增 user 信息
     * @param userDO user DO对象
     * @return 是否新增成功
     */
    boolean insert(UserDO userDO);

    /**
     * 新增病返回 user 信息
     * @param userDO user DO对象
     * @return user DO对象
     */
    UserDO insertAndQuery(UserDO userDO);

    /**
     * 更新 user 信息
     * @param userDO user DO对象
     * @return 是否更新成功
     */
    boolean update(UserDO userDO);

    /**
     * 根据主键 id 删除 user 信息
     * @param id 主键 id
     * @return 是否删除成功
     */
    boolean delete(int id);
}
