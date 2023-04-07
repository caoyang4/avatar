package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.web.vo.capacity.AppKeyCapacityDetailVO;
import com.sankuai.avatar.web.vo.capacity.AppkeyCapacitySummaryVO;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author caoyang
 * @create 2022-10-13 14:35
 */
public class AppkeyCapacityControllerTest extends TestBase {

    @Test
    public void testIsPaas() throws Exception {
        String appkey1 = "com.sankuai.inf.mafka.brokerofflinedx";
        String appkey2 = "com.sankuai.avatar.web";
        String url1 = "/api/v2/avatar/capacity/isPaas/" + appkey1;
        String url2 = "/api/v2/avatar/capacity/isPaas/" + appkey2;
        Boolean isPaas1 = getMock(url1, Boolean.class);
        Boolean isPaas2 = getMock(url2, Boolean.class);
        Assert.assertTrue(isPaas1);
        Assert.assertFalse(isPaas2);
    }

    @Test
    public void testGetAppkeyPaasCapacitySummary() throws Exception {
        String appkey = "com.sankuai.avatar.web";
        String url = String.format("/api/v2/avatar/capacity/%s/summary", appkey);
        AppkeyCapacitySummaryVO appkeyPaasCapacitySummary = getMock(url, AppkeyCapacitySummaryVO.class);
        Assert.assertNotNull(appkeyPaasCapacitySummary);
        Assert.assertNotNull(appkeyPaasCapacitySummary.getCapacityLevel());
        Assert.assertFalse(appkeyPaasCapacitySummary.getIsPaas());
    }

    @Test
    public void testGetAppKeyCapacityDetail() throws Exception {
        String appkey = "com.sankuai.deliveryturing.nextbox.dispatch";
        String url = "/api/v2/avatar/capacity/" + appkey;
        AppKeyCapacityDetailVO detailVO = getMock(url, AppKeyCapacityDetailVO.class);
        Assert.assertNotNull(detailVO);
        Assert.assertTrue(detailVO.getCapacityLevels().size() > 0);
    }
}