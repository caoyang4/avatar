package com.sankuai.avatar.resource.activity.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.ResourceSubscriptionOrderResource;
import com.sankuai.avatar.resource.activity.bo.ResourceSubscriptionOrderBO;
import com.sankuai.avatar.resource.activity.request.SubscriptionOrderRequestBO;
import com.sankuai.avatar.resource.activity.transfer.ResourceSubscriptionOrderTransfer;
import com.sankuai.avatar.dao.resource.repository.ResourceSubscriptionOrderRepository;
import com.sankuai.avatar.dao.resource.repository.model.ResourceSubscriptionOrderDO;
import com.sankuai.avatar.dao.resource.repository.request.ResourceSubscriptionOrderRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-02-13 11:55
 */
@Component
public class ResourceSubscriptionOrderResourceImpl implements ResourceSubscriptionOrderResource {

    private final ResourceSubscriptionOrderRepository repository;

    @Autowired
    public ResourceSubscriptionOrderResourceImpl(ResourceSubscriptionOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ResourceSubscriptionOrderBO> query(SubscriptionOrderRequestBO requestBO) {
        List<ResourceSubscriptionOrderDO> doList = repository.query(ResourceSubscriptionOrderTransfer.INSTANCE.toRequest(requestBO));
        return ResourceSubscriptionOrderTransfer.INSTANCE.toBOList(doList);
    }

    @Override
    public PageResponse<ResourceSubscriptionOrderBO> queryPage(SubscriptionOrderRequestBO requestBO) {
        int page = requestBO.getPage();
        int pageSize = requestBO.getPageSize();
        PageResponse<ResourceSubscriptionOrderBO> pageResponse = new PageResponse<>();
        Page<ResourceSubscriptionOrderDO> doPage = PageHelper.startPage(page, pageSize).doSelectPage(
                () -> repository.query(ResourceSubscriptionOrderTransfer.INSTANCE.toRequest(requestBO))
        );
        pageResponse.setPage(page);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotalPage(doPage.getPages());
        pageResponse.setTotalCount(doPage.getTotal());
        pageResponse.setItems(ResourceSubscriptionOrderTransfer.INSTANCE.toBOList(doPage));
        return pageResponse;
    }

    @Override
    public Boolean save(ResourceSubscriptionOrderBO orderBO) {
        if (Objects.isNull(orderBO.getId()) || CollectionUtils.isEmpty(repository.query(ResourceSubscriptionOrderRequest.builder().id(orderBO.getId()).build()))) {
            return repository.insert(ResourceSubscriptionOrderTransfer.INSTANCE.toDO(orderBO));
        } else {
            return repository.update(ResourceSubscriptionOrderTransfer.INSTANCE.toDO(orderBO));
        }

    }

    @Override
    public Boolean deleteByPk(int pk) {
        return repository.delete(pk);
    }
}
