package com.sankuai.avatar.capacity.util;

import org.apache.commons.collections.MapUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class DomUtilsTest {

    @Test
    public void getWeekAppKeyUtilizationWithoutCell() {
        Double util = DomUtils.getWeekAppKeyUtilizationWithoutCell("com.sankuai.rmsmarketing.product.base");
        Assert.assertNotNull(util);
    }

    @Test
    public void getWeekAppKeyUtilizationWithCell() {
        Double util = DomUtils.getWeekAppKeyUtilizationWithCell("", "");
        Assert.assertNotNull(util);
    }

    @Test
    public void getWeekAppKeyUtils() throws Exception{
        Map<String, Double> map = DomUtils.getAppKeyWeekUtilsByUnit("物理机");
        Assert.assertNotNull(map);
    }

    @Test
    public void getAppKeyUtilization(){
        String appkey = "com.sankuai.dipper.nod.firewall";
        String set = "";
        Double appKeyUtilization = DomUtils.getAppKeyUtilization(appkey, set, false);
        Assert.assertNotNull(appKeyUtilization);
    }

    @Test
    public void getWeekAppKeyUtilization(){
        String appkey = "com.sankuai.avatar.web";
        Map<String, Double> map = DomUtils.getWeekAppKeyUtilization(appkey);
        Assert.assertTrue(MapUtils.isNotEmpty(map));

    }
}