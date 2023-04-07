package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.activity.ResourceSubscriptionOrderDTO;
import com.sankuai.avatar.web.request.ResourceSubscriptionOrderPageRequest;

/**
 * @author caoyang
 * @create 2023-02-13 14:39
 */
public interface ResourceSubscriptionOrderService {

    /**
     * 查询页面
     *
     * @param pageRequest 请求
     * @return {@link PageResponse}<{@link ResourceSubscriptionOrderDTO}>
     */
    PageResponse<ResourceSubscriptionOrderDTO> queryPage(ResourceSubscriptionOrderPageRequest pageRequest);

    /**
     * 保存资源订阅订单
     *
     * @param orderDTO orderDTO
     * @return {@link Boolean}
     */
    Boolean saveResourceSubscriptionOrder(ResourceSubscriptionOrderDTO orderDTO);

    /**
     * 删除资源订阅订单
     *
     * @param pk 主键
     * @return {@link Boolean}
     */
    Boolean deleteResourceSubscriptionOrder(int pk);

}
