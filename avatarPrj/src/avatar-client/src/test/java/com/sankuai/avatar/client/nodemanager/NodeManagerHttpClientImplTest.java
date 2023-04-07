package com.sankuai.avatar.client.nodemanager;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.http.core.EnvEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qinwei05
 */

public class NodeManagerHttpClientImplTest extends TestBase {

    @Autowired
    private NodeManagerHttpClient nodeManagerHttpClient;

    @Test
    public void getHostsParentFeatures() {
        // 线上
        List<String> prodParentHostsName = new ArrayList<>();
        prodParentHostsName.add("hh-hulk-k8s-node247");
        Map<String, List<String>> prodHostsParentFeatures = nodeManagerHttpClient.getHostsParentFeatures(prodParentHostsName, EnvEnum.PROD);
        Assert.assertTrue(prodHostsParentFeatures.size() > 0);
        // 线下
        List<String> testParentHostsName = new ArrayList<>();
        testParentHostsName.add("hh-hulk-k8s-node247");
        Map<String, List<String>> hostsParentFeatures = nodeManagerHttpClient.getHostsParentFeatures(testParentHostsName, EnvEnum.TEST);
        Assert.assertNotNull(hostsParentFeatures);
    }
}
