package com.sankuai.avatar.capacity.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author caoyang
 * @create 2022-12-13 17:48
 */
public class OctoUtilsTest {

    @Test
    public void isNotAppkeyCityIdcStrategy() {
        String appkey = "com.sankuai.avatar.web";
        boolean strategy = OctoUtils.isNotAppkeyCityIdcStrategy(appkey, true);
        Assert.assertTrue(strategy);
    }
}