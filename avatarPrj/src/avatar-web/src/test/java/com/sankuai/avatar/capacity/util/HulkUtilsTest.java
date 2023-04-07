package com.sankuai.avatar.capacity.util;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author caoyang
 * @create 2022-08-18 15:42
 */
public class HulkUtilsTest extends TestCase {

    @Test
    public void testIsSetElastic() {
        String appkey = "com.sankuai.mptrade.buy.process";
        String set = "gray-release-nib-test-shanghai";
        System.out.println(HulkUtils.isSetElastic(appkey, set));
    }
}