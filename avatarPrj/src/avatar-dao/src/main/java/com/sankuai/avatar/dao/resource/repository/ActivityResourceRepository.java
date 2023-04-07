package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.ActivityResourceDO;
import com.sankuai.avatar.dao.resource.repository.request.ActivityResourceRequest;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-03-07 16:18
 */
public interface ActivityResourceRepository {

    /**
     * 查询
     *
     * @param activityResourceRequest 请求对象
     * @return {@link List}<{@link ActivityResourceDO}>
     */
    List<ActivityResourceDO> query(ActivityResourceRequest activityResourceRequest);

    /**
     * 插入
     *
     * @param activityResourceDO activityResourceDO
     * @return boolean
     */
    boolean insert(ActivityResourceDO activityResourceDO);

    /**
     * 更新
     *
     * @param activityResourceDO activityResourceDO
     * @return boolean
     */
    boolean update(ActivityResourceDO activityResourceDO);

    /**
     * 删除
     *
     * @param id id
     * @return boolean
     */
    boolean delete(int id);

}
