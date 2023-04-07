package com.sankuai.avatar.client.kapiserver;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.kapiserver.model.HostFeature;
import com.sankuai.avatar.client.kapiserver.request.VmHostDiskQueryRequest;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qinwei05
 */

public class KApiServerHttpClientImplTest extends TestBase {

    @Autowired
    private KApiServerHttpClient kApiServerHttpClient;

    @Test
    public void getHulkHostsFeatures() {
        // 线上
        List<String> prodHostsName = new ArrayList<>();
        prodHostsName.add("set-yp-avatar-cscscscs01");
        Map<String, HostFeature> prodHostsFeatures = kApiServerHttpClient.getHulkHostsFeatures(prodHostsName, EnvEnum.PROD);
        Assert.assertNotNull(prodHostsFeatures);
        // 线下
        List<String> testHostsName = new ArrayList<>();
        testHostsName.add("set-yp-avatar-cscscscs-test02");
        Map<String, HostFeature> testHostsFeatures = kApiServerHttpClient.getHulkHostsFeatures(testHostsName, EnvEnum.TEST);
        Assert.assertNotNull(testHostsFeatures);
    }

    @Test
    public void getVmHostsDiskFeatures() {
        // 线上
        List<String> prodHostsIP = new ArrayList<>();
        prodHostsIP.add("10.72.160.183");
        VmHostDiskQueryRequest vmHostDiskQueryRequest = VmHostDiskQueryRequest.builder()
                .appkey("com.sankuai.avatar.cscscscs").env("prod").ips(prodHostsIP).build();
        try {
            kApiServerHttpClient.getVmHostsDiskFeatures(vmHostDiskQueryRequest);
        } catch (SdkBusinessErrorException e) {
            Assert.assertNotNull(e.getMessage());
        }
        // 线下
        List<String> testHostsIp = new ArrayList<>();
        testHostsIp.add("10.73.209.224");
        VmHostDiskQueryRequest vmHostDiskQueryRequest2 = VmHostDiskQueryRequest.builder()
                .appkey("com.sankuai.avatar.develop").env("test").ips(testHostsIp).build();
        try {
            kApiServerHttpClient.getVmHostsDiskFeatures(vmHostDiskQueryRequest2);
        } catch (SdkBusinessErrorException e) {
            Assert.assertNotNull(e.getMessage());
        }
    }
}
