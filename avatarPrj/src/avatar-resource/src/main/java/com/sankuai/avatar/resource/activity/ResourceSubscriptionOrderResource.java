package com.sankuai.avatar.resource.activity;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.bo.ResourceSubscriptionOrderBO;
import com.sankuai.avatar.resource.activity.request.SubscriptionOrderRequestBO;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-02-13 11:17
 */
public interface ResourceSubscriptionOrderResource {

    /**
     * 查询
     *
     * @param requestBO 请求
     * @return {@link List}<{@link ResourceSubscriptionOrderBO}>
     */
    List<ResourceSubscriptionOrderBO> query(SubscriptionOrderRequestBO requestBO);

    /**
     * 分页查询
     *
     * @param requestBO 请求
     * @return {@link PageResponse}<{@link ResourceSubscriptionOrderBO}>
     */
    PageResponse<ResourceSubscriptionOrderBO> queryPage(SubscriptionOrderRequestBO requestBO);

    /**
     * 保存
     *
     * @param orderBO 订单
     * @return {@link Boolean}
     */
    Boolean save(ResourceSubscriptionOrderBO orderBO);

    /**
     * 删除
     *
     * @param pk 主键
     * @return {@link Boolean}
     */
    Boolean deleteByPk(int pk);

}
