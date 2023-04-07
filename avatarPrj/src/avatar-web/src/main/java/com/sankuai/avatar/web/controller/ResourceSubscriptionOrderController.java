package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.activity.ResourceSubscriptionOrderDTO;
import com.sankuai.avatar.web.request.ResourceSubscriptionOrderPageRequest;
import com.sankuai.avatar.web.service.ResourceSubscriptionOrderService;
import com.sankuai.avatar.web.transfer.activity.ResourceSubscriptionOrderVOTransfer;
import com.sankuai.avatar.web.vo.activity.ResourceSubscriptionOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author caoyang
 * @create 2023-02-13 15:58
 */
@Validated
@RestController
@RequestMapping("/api/v2/avatar/resource_subscription")
public class ResourceSubscriptionOrderController {

    private final ResourceSubscriptionOrderService service;

    @Autowired
    public ResourceSubscriptionOrderController(ResourceSubscriptionOrderService service) {
        this.service = service;
    }

    @GetMapping("")
    public PageResponse<ResourceSubscriptionOrderVO> getPageResourceSubscriptionOrder(ResourceSubscriptionOrderPageRequest pageRequest){
        PageResponse<ResourceSubscriptionOrderVO> pageResponse = new PageResponse<>();
        PageResponse<ResourceSubscriptionOrderDTO> dtoPageResponse = service.queryPage(pageRequest);
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setItems(ResourceSubscriptionOrderVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @PostMapping("")
    public Boolean saveResourceSubscriptionOrder(@Valid @RequestBody ResourceSubscriptionOrderVO resourceSubscriptionOrder){
        return service.saveResourceSubscriptionOrder(ResourceSubscriptionOrderVOTransfer.INSTANCE.toDTO(resourceSubscriptionOrder));
    }

    @DeleteMapping("/{id}")
    public Boolean deleteResourceSubscriptionOrder(@PathVariable Integer id){
        return service.deleteResourceSubscriptionOrder(id);
    }

    @PutMapping("/{id}")
    public Boolean updateResourceSubscriptionOrder( @Valid @RequestBody ResourceSubscriptionOrderVO resourceSubscriptionOrder, @PathVariable Integer id){
        resourceSubscriptionOrder.setId(id);
        return service.saveResourceSubscriptionOrder(ResourceSubscriptionOrderVOTransfer.INSTANCE.toDTO(resourceSubscriptionOrder));
    }

}
