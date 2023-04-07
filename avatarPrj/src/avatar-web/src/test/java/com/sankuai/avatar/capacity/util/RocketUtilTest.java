package com.sankuai.avatar.capacity.util;

import com.sankuai.rocket.host.instance.openapi.model.HostDTO;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class RocketUtilTest {

    @Test
    public void listSet() {
        Set<String> set = RocketUtil.listSet("com.sankuai.basicsearch.bsproxy.commonsearch");
    }

    @Test
    public void testHttpGetHostDataByAppKey() {
        PageResultHostDTO pageResultHostDTO = RocketUtil.httpGetHostDataByAppKey("com.sankuai.avatar.web");
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResultHostDTO.getData()));
    }

    @Test
    public void httpGetHostData() {
        String name = "set-tx-avatar-web03";
        HostDTO hostDTO = RocketUtil.httpGetHostData(name);
        Assert.assertNotNull(hostDTO);
    }
}