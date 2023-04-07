package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.resource.repository.model.ResourceSubscriptionOrderDO;
import com.sankuai.avatar.dao.resource.repository.request.ResourceSubscriptionOrderRequest;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-02-10 14:53
 */
public interface ResourceSubscriptionOrderRepository {

    /**
     * 查询
     *
     * @param request 请求
     * @return {@link List}<{@link ResourceSubscriptionOrderDO}>
     */
    List<ResourceSubscriptionOrderDO> query(ResourceSubscriptionOrderRequest request);

    /**
     * 插入
     *
     * @param resourceSubscriptionOrderDO 资源订阅订单
     * @return {@link Boolean}
     */
    Boolean insert(ResourceSubscriptionOrderDO resourceSubscriptionOrderDO);

    /**
     * 更新
     *
     * @param resourceSubscriptionOrderDO 资源订阅订单
     * @return {@link Boolean}
     */
    Boolean update(ResourceSubscriptionOrderDO resourceSubscriptionOrderDO);

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    Boolean delete(int id);
}
