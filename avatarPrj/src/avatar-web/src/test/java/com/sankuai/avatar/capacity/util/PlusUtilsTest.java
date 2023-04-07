package com.sankuai.avatar.capacity.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author caoyang
 * @create 2023-03-06 14:55
 */
public class PlusUtilsTest {

    @Test
    public void isHostDeployed(){
        String host = "set-gh-avatar-web01";
        String ip = "10.20.198.110";
        boolean hostDeployed = PlusUtils.isHostDeployed(host, ip, 30);
        Assert.assertTrue(hostDeployed);

    }

}
