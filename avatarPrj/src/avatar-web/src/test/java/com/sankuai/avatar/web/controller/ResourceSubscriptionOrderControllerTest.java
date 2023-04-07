package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.activity.constant.OrderStateType;
import com.sankuai.avatar.web.dto.activity.OrderHostDTO;
import com.sankuai.avatar.web.vo.activity.ResourceSubscriptionOrderVO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * @author caoyang
 * @create 2023-02-14 14:25
 */
public class ResourceSubscriptionOrderControllerTest extends TestBase {

    private final String baseUrl = "/api/v2/avatar/resource_subscription";

    @Test
    public void getPageResourceSubscriptionOrder() throws Exception {
        String url = baseUrl + "?pageSize=1";
        PageResponse<ResourceSubscriptionOrderVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(1, pageResponse.getPageSize());
        Assert.assertEquals(1, pageResponse.getItems().size());
    }

    @Test
    public void saveResourceSubscriptionOrder() throws Exception {
        ResourceSubscriptionOrderVO orderVO = new ResourceSubscriptionOrderVO();
        OrderHostDTO orderHostDTO = new OrderHostDTO();
        orderHostDTO.setIdc("yp");
        orderHostDTO.setCpu(4);
        orderHostDTO.setMemory(8);
        orderVO.setFlowId(123);
        orderVO.setFlowUuid(UUID.randomUUID().toString());
        orderVO.setEnv("test");
        orderVO.setAppkey("test-appkey");
        orderVO.setHostConfig(orderHostDTO);
        orderVO.setCount(10);
        orderVO.setInitCount(10);
        orderVO.setRegion("shanghai");
        orderVO.setIdc("jd");
        orderVO.setStatus(OrderStateType.HOLDING);
        orderVO.setUnit("unit_31");
        orderVO.setSubscriber("caoyang42");
        orderVO.setCreateUser("caoyang42");
        orderVO.setCreateTime(new Date());
        orderVO.setExpireTime(new Date());
        Boolean save = postMock(baseUrl, orderVO, Boolean.class);
        Assert.assertTrue(save);
    }

    @Test
    public void deleteResourceSubscriptionOrder() throws Exception {
        String url = baseUrl + "?appkey=test-appkey";
        PageResponse<ResourceSubscriptionOrderVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        if (CollectionUtils.isNotEmpty(pageResponse.getItems())) {
            ResourceSubscriptionOrderVO orderVO = JsonUtil.json2Bean(JsonUtil.bean2Json(pageResponse.getItems().get(0)), ResourceSubscriptionOrderVO.class);
            int id = orderVO.getId();
            String url1 = baseUrl + "/" + id;
            Boolean delete = deleteMock(url1, new Object(), Boolean.class);
            Assert.assertTrue(delete);
        }

    }

    @Test
    public void updateResourceSubscriptionOrder() throws Exception {
        String url = baseUrl + "?pageSize=1";
        PageResponse<ResourceSubscriptionOrderVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        ResourceSubscriptionOrderVO orderVO = JsonUtil.json2Bean(JsonUtil.bean2Json(pageResponse.getItems().get(0)), ResourceSubscriptionOrderVO.class);
        int id = orderVO.getId();
        orderVO.setReason("无可奉告");
        orderVO.setEndTime(new Date());
        String url1 = baseUrl + "/" + id;
        Boolean update = putMock(url1, orderVO, Boolean.class);
        Assert.assertTrue(update);
    }
}