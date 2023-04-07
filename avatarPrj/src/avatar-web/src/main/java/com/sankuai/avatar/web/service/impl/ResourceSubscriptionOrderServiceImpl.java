package com.sankuai.avatar.web.service.impl;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.ResourceSubscriptionOrderResource;
import com.sankuai.avatar.resource.activity.bo.ResourceSubscriptionOrderBO;
import com.sankuai.avatar.web.dto.activity.ResourceSubscriptionOrderDTO;
import com.sankuai.avatar.web.request.ResourceSubscriptionOrderPageRequest;
import com.sankuai.avatar.web.service.ResourceSubscriptionOrderService;
import com.sankuai.avatar.web.transfer.activity.ResourceSubscriptionOrderDTOTransfer;
import org.springframework.stereotype.Service;

/**
 * @author caoyang
 * @create 2023-02-13 14:55
 */
@Service
public class ResourceSubscriptionOrderServiceImpl implements ResourceSubscriptionOrderService {

    private final ResourceSubscriptionOrderResource resource;

    public ResourceSubscriptionOrderServiceImpl(ResourceSubscriptionOrderResource resource) {
        this.resource = resource;
    }

    @Override
    public PageResponse<ResourceSubscriptionOrderDTO> queryPage(ResourceSubscriptionOrderPageRequest pageRequest) {
        PageResponse<ResourceSubscriptionOrderBO> boPageResponse = resource.queryPage(ResourceSubscriptionOrderDTOTransfer.INSTANCE.toRequestBO(pageRequest));
        PageResponse<ResourceSubscriptionOrderDTO> pageResponse = new PageResponse<>();
        pageResponse.setPage(boPageResponse.getPage());
        pageResponse.setPageSize(boPageResponse.getPageSize());
        pageResponse.setTotalPage(boPageResponse.getTotalPage());
        pageResponse.setTotalCount(boPageResponse.getTotalCount());
        pageResponse.setItems(ResourceSubscriptionOrderDTOTransfer.INSTANCE.toDTOList(boPageResponse.getItems()));
        return pageResponse;
    }

    @Override
    public Boolean saveResourceSubscriptionOrder(ResourceSubscriptionOrderDTO orderDTO) {
        return resource.save(ResourceSubscriptionOrderDTOTransfer.INSTANCE.toBO(orderDTO));
    }

    @Override
    public Boolean deleteResourceSubscriptionOrder(int pk) {
        return resource.deleteByPk(pk);
    }
}
