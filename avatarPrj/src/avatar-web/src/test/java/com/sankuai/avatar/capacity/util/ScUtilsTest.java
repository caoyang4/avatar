package com.sankuai.avatar.capacity.util;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author caoyang
 * @create 2022-08-23 15:32
 */
public class ScUtilsTest {

    @Test
    public void testIsAppkeyJbox() {
        String appkey = "com.sankuai.meishitopic.jbox.meishitopicjbox";
        boolean isJbox = ScUtils.isJboxAppkey(appkey);
        System.out.println(isJbox);
    }

}