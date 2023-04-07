package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.UserRelationDO;
import com.sankuai.avatar.dao.resource.repository.request.UserRelationRequest;

import java.util.List;

/**
 * UserRelation数据管理接口
 * @author caoyang
 * @create 2022-11-02 18:50
 */
public interface UserRelationRepository {

    /**
     * 查询UserRelation
     *
     * @param userRelationRequest 请求
     * @return {@link List}<{@link UserRelationDO}>
     */
    List<UserRelationDO> query(UserRelationRequest userRelationRequest);

    /**
     * 插入
     *
     * @param userRelationDO 用户关系做
     * @return boolean
     */
    boolean insert(UserRelationDO userRelationDO);

    /**
     * 更新
     *
     * @param userRelationDO 用户关系做
     * @return boolean
     */
    boolean update(UserRelationDO userRelationDO);

    /**
     * 删除
     *
     * @param id id
     * @return boolean
     */
    boolean delete(int id);
}
