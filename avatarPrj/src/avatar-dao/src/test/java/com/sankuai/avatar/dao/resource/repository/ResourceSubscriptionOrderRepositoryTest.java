package com.sankuai.avatar.dao.resource.repository;

import com.sankuai.avatar.dao.config.DataSourceConfig;
import com.sankuai.avatar.dao.resource.repository.model.ResourceSubscriptionOrderDO;
import com.sankuai.avatar.dao.resource.repository.request.ResourceSubscriptionOrderRequest;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2023-02-10 14:55
 */
public class ResourceSubscriptionOrderRepositoryTest extends TestBase {

    private final ResourceSubscriptionOrderRepository repository;
    static ResourceSubscriptionOrderDO orderDO = new ResourceSubscriptionOrderDO();
    static {
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
    }

    public ResourceSubscriptionOrderRepositoryTest(){
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        repository = (ResourceSubscriptionOrderRepository) ctx.getBean("resourceSubscriptionOrderRepositoryImpl");
    }

    @Test
    public void query() {
        String appkey = "com.sankuai.avatar.develop";
        List<ResourceSubscriptionOrderDO> doList = repository.query(ResourceSubscriptionOrderRequest.builder().appkey(appkey).build());
        Assert.assertTrue(CollectionUtils.isNotEmpty(doList));
        for (ResourceSubscriptionOrderDO orderDO : doList) {
            Assert.assertEquals(appkey, orderDO.getAppkey());
        }
    }

    @Test
    public void insert() {
        Boolean insert = repository.insert(orderDO);
        Assert.assertTrue(insert);
    }

    @Test
    public void update() {
        String appkey = "test-appkey";
        List<ResourceSubscriptionOrderDO> doList = repository.query(ResourceSubscriptionOrderRequest.builder().appkey(appkey).build());
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            ResourceSubscriptionOrderDO subscriptionOrderDO = doList.get(0);
            subscriptionOrderDO.setReason("无可奉告");
            Assert.assertTrue(repository.update(subscriptionOrderDO));
        }
    }

    @Test
    public void delete() {
        String appkey = "test-appkey";
        List<ResourceSubscriptionOrderDO> doList = repository.query(ResourceSubscriptionOrderRequest.builder().appkey(appkey).build());
        Assert.assertNotNull(doList);
        if (CollectionUtils.isNotEmpty(doList)) {
            ResourceSubscriptionOrderDO subscriptionOrderDO = doList.get(0);
            Assert.assertTrue(repository.delete(subscriptionOrderDO.getId()));
        }
    }
}