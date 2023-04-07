package com.sankuai.avatar.resource.activity;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.bo.ActivityResourceBO;
import com.sankuai.avatar.resource.activity.request.ActivityResourceRequestBO;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-03-08 14:04
 */
public interface ActivityResource {

    /**
     * 查询
     *
     * @param requestBO requestBO
     * @return {@link List}<{@link ActivityResourceBO}>
     */
    List<ActivityResourceBO> query(ActivityResourceRequestBO requestBO);

    /**
     * 分页查询
     *
     * @param requestBO requestBO
     * @return {@link PageResponse}<{@link ActivityResourceBO}>
     */
    PageResponse<ActivityResourceBO> queryPage(ActivityResourceRequestBO requestBO);

    /**
     * 保存
     *
     * @param activityResourceBO activityResourceBO
     * @return {@link Boolean}
     */
    Boolean save(ActivityResourceBO activityResourceBO);

    /**
     * 删除
     *
     * @param pk 主键
     * @return {@link Boolean}
     */
    Boolean deleteByPk(int pk);

}
