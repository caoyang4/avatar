package com.sankuai.avatar.resource.activity;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.activity.bo.OrderHostBO;
import com.sankuai.avatar.resource.activity.bo.ResourceSubscriptionOrderBO;
import com.sankuai.avatar.resource.activity.constant.OrderStateType;
import com.sankuai.avatar.resource.activity.impl.ResourceSubscriptionOrderResourceImpl;
import com.sankuai.avatar.resource.activity.request.SubscriptionOrderRequestBO;
import com.sankuai.avatar.dao.resource.repository.ResourceSubscriptionOrderRepository;
import com.sankuai.avatar.dao.resource.repository.model.ResourceSubscriptionOrderDO;
import com.sankuai.avatar.dao.resource.repository.request.ResourceSubscriptionOrderRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * @author caoyang
 * @create 2023-02-13 12:06
 */
public class ResourceSubscriptionOrderResourceTest extends TestBase {

    private ResourceSubscriptionOrderResource resource;

    @Mock
    private ResourceSubscriptionOrderRepository repository;

    static ResourceSubscriptionOrderDO orderDO = new ResourceSubscriptionOrderDO();
    static ResourceSubscriptionOrderBO orderBO = new ResourceSubscriptionOrderBO();
    static OrderHostBO orderHostBO = new OrderHostBO();
    static {
        orderDO.setId(1);
        orderDO.setFlowId(123);
        orderDO.setFlowUuid(UUID.randomUUID().toString());
        orderDO.setEnv("test");
        orderDO.setAppkey("test-appkey");
        orderDO.setCount(10);
        orderDO.setInitCount(10);
        orderDO.setRegion("shanghai");
        orderDO.setIdc("jd");
        orderDO.setStatus("HOLDING");
        orderDO.setUnit("unit_31");
        orderDO.setSubscriber("caoyang42");
        orderDO.setCreateUser("caoyang42");

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
    }

    @Before
    public void setUp() throws Exception {
        resource = new ResourceSubscriptionOrderResourceImpl(repository);
    }

    @Test
    public void query() {
        when(repository.query(Mockito.any(ResourceSubscriptionOrderRequest.class))).thenReturn(Collections.singletonList(orderDO));
        List<ResourceSubscriptionOrderBO> boList = resource.query(new SubscriptionOrderRequestBO());
        Assert.assertEquals("test-appkey", boList.get(0).getAppkey());
        verify(repository).query(Mockito.any(ResourceSubscriptionOrderRequest.class));
    }

    @Test
    public void queryPage() {
        when(repository.query(Mockito.any(ResourceSubscriptionOrderRequest.class))).thenReturn(Collections.singletonList(orderDO));
        PageResponse<ResourceSubscriptionOrderBO> pageResponse = resource.queryPage(new SubscriptionOrderRequestBO());
        Assert.assertEquals(1, pageResponse.getPage());
        verify(repository).query(Mockito.any(ResourceSubscriptionOrderRequest.class));
    }

    @Test
    public void insert() {
        orderBO.setId(null);
        when(repository.insert(Mockito.any(ResourceSubscriptionOrderDO.class))).thenReturn(true);
        Boolean insert = resource.save(orderBO);
        Assert.assertTrue(insert);
        verify(repository).insert(Mockito.any(ResourceSubscriptionOrderDO.class));
        verify(repository,times(0)).update(Mockito.any(ResourceSubscriptionOrderDO.class));
    }

    @Test
    public void update() {
        orderBO.setId(1);
        when(repository.update(Mockito.any(ResourceSubscriptionOrderDO.class))).thenReturn(true);
        when(repository.query(Mockito.any(ResourceSubscriptionOrderRequest.class))).thenReturn(Collections.singletonList(orderDO));
        Boolean update = resource.save(orderBO);
        Assert.assertTrue(update);
        verify(repository).query(Mockito.any(ResourceSubscriptionOrderRequest.class));
        verify(repository).update(Mockito.any(ResourceSubscriptionOrderDO.class));
        verify(repository,times(0)).insert(Mockito.any(ResourceSubscriptionOrderDO.class));
    }

    @Test
    public void deleteByPk() {
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        Boolean delete = resource.deleteByPk(1);
        Assert.assertTrue(delete);
        verify(repository).delete(Mockito.anyInt());
    }

}