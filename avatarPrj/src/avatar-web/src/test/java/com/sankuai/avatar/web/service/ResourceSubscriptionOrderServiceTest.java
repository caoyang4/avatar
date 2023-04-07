package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.ResourceSubscriptionOrderResource;
import com.sankuai.avatar.resource.activity.bo.OrderHostBO;
import com.sankuai.avatar.resource.activity.bo.ResourceSubscriptionOrderBO;
import com.sankuai.avatar.resource.activity.constant.OrderStateType;
import com.sankuai.avatar.resource.activity.request.SubscriptionOrderRequestBO;
import com.sankuai.avatar.web.dto.activity.OrderHostDTO;
import com.sankuai.avatar.web.dto.activity.ResourceSubscriptionOrderDTO;
import com.sankuai.avatar.web.request.ResourceSubscriptionOrderPageRequest;
import com.sankuai.avatar.web.service.impl.ResourceSubscriptionOrderServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.UUID;
import static org.mockito.Mockito.*;
/**
 * @author caoyang
 * @create 2023-02-14 14:01
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceSubscriptionOrderServiceTest  {

    private ResourceSubscriptionOrderService service;

    @Mock
    private ResourceSubscriptionOrderResource resource;

    static ResourceSubscriptionOrderDTO orderDTO = new ResourceSubscriptionOrderDTO();
    static ResourceSubscriptionOrderBO orderBO = new ResourceSubscriptionOrderBO();
    static OrderHostDTO orderHostDTO = new OrderHostDTO();
    static OrderHostBO orderHostBO = new OrderHostBO();
    static PageResponse<ResourceSubscriptionOrderBO> boPageResponse = new PageResponse<>();
    static {
        orderHostBO.setIdc("jd");
        orderHostBO.setCpu(4);
        orderBO.setFlowId(123);
        orderBO.setFlowUuid(UUID.randomUUID().toString());
        orderBO.setEnv("test");
        orderBO.setAppkey("test-appkey");
        orderBO.setHostConfig(orderHostBO);
        orderBO.setCount(10);
        orderBO.setInitCount(10);
        orderBO.setRegion("shanghai");
        orderBO.setIdc("jd");
        orderBO.setStatus(OrderStateType.HOLDING);
        orderBO.setUnit("unit_31");
        orderBO.setSubscriber("caoyang42");
        orderBO.setCreateUser("caoyang42");

        orderHostDTO.setIdc("yp");
        orderHostDTO.setCpu(4);
        orderHostDTO.setMemory(8);
        orderDTO.setFlowId(123);
        orderDTO.setFlowUuid(UUID.randomUUID().toString());
        orderDTO.setEnv("test");
        orderDTO.setAppkey("test-appkey");
        orderDTO.setHostConfig(orderHostDTO);
        orderDTO.setCount(10);
        orderDTO.setInitCount(10);
        orderDTO.setRegion("shanghai");
        orderDTO.setIdc("jd");
        orderDTO.setStatus(OrderStateType.HOLDING);
        orderDTO.setUnit("unit_31");
        orderDTO.setSubscriber("caoyang42");
        orderDTO.setCreateUser("caoyang42");

        boPageResponse.setPage(1);
        boPageResponse.setPageSize(1);
        boPageResponse.setTotalPage(1);
        boPageResponse.setTotalCount(1);
    }

    @Before
    public void setUp() throws Exception {
        service = new ResourceSubscriptionOrderServiceImpl(resource);
    }

    @Test
    public void queryPage() {
        boPageResponse.setItems(Collections.singletonList(orderBO));
        when(resource.queryPage(Mockito.any(SubscriptionOrderRequestBO.class))).thenReturn(boPageResponse);
        PageResponse<ResourceSubscriptionOrderDTO> dtoPageResponse = service.queryPage(new ResourceSubscriptionOrderPageRequest());
        Assert.assertEquals("test-appkey", dtoPageResponse.getItems().get(0).getAppkey());
        verify(resource).queryPage(Mockito.any(SubscriptionOrderRequestBO.class));
    }

    @Test
    public void saveResourceSubscriptionOrder() {
        when(resource.save(Mockito.any(ResourceSubscriptionOrderBO.class))).thenReturn(true);
        Boolean save = service.saveResourceSubscriptionOrder(orderDTO);
        Assert.assertTrue(save);
        verify(resource).save(Mockito.any(ResourceSubscriptionOrderBO.class));
    }

    @Test
    public void deleteResourceSubscriptionOrder() {
        when(resource.deleteByPk(Mockito.anyInt())).thenReturn(true);
        Boolean delete = service.deleteResourceSubscriptionOrder(1);
        Assert.assertTrue(delete);
        verify(resource).deleteByPk(Mockito.anyInt());
    }
}