package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityDetailVO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-09-23 14:56
 */
public class AppkeyPaasCapacityControllerTest extends TestBase {

    @Test
    public void testGetAppkeyPaasCapacity() throws Exception {
        String appkey = "com.meituan.banma.api01";
        String url = "/api/v2/avatar/appkeyPaasCapacity/" + appkey;
        AppkeyPaasCapacityDetailVO capacityDetailVO = getMock(url, AppkeyPaasCapacityDetailVO.class);
        Assert.assertEquals(appkey, capacityDetailVO.getAppkey());
    }

    @Test
    public void testGetAppkeyPaasCapacitySelfDetail() throws Exception {
        String appkey = "com.sankuai.tair.banma.qaset";
        String url = "/api/v2/avatar/appkeyPaasCapacity/self/" + appkey;
        AppkeyPaasCapacityDetailVO capacityDetailVO = getMock(url, AppkeyPaasCapacityDetailVO.class);
        Assert.assertEquals(appkey, capacityDetailVO.getAppkey());
    }

    @Test
    public void getPaasNamesByAppkey() throws Exception {
        String appkey = "com.meituan.banma.api01";
        String url = "/api/v2/avatar/appkeyPaasCapacity/paasNames?appkey=" + appkey + "&isSelf=false";
        List<String> paasNames = Arrays.asList(getMock(url, String[].class));
        Assert.assertTrue(CollectionUtils.isNotEmpty(paasNames));
    }
}
