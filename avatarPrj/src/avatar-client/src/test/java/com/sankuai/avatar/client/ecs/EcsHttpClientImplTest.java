package com.sankuai.avatar.client.ecs;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.ecs.model.BillingUnit;
import com.sankuai.avatar.client.ecs.model.EcsIdc;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ECS系统接口实现测试
 * @author qinwei05
 */
public class EcsHttpClientImplTest extends TestBase {

    @Autowired
    private EcsHttpClient ecsHttpClient;

    @Test
    public void getIdcList() {
        List<EcsIdc> idcList = ecsHttpClient.getIdcList();
        Assert.assertTrue(CollectionUtils.isNotEmpty(idcList));
    }

    @Test
    public void testGetAppkeyUnitList() {
        BillingUnit billingUnit = ecsHttpClient.getAppkeyUnitList(testAppkey);
        Assert.assertEquals(31, (int) billingUnit.getBillingUnitId());
    }
}
