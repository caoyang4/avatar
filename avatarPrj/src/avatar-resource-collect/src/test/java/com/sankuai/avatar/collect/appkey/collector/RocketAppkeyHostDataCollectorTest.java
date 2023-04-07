package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.client.rocket.RocketHttpClient;
import com.sankuai.avatar.client.rocket.model.HostQueryRequest;
import com.sankuai.avatar.client.rocket.model.RocketHost;
import com.sankuai.avatar.client.rocket.response.RocketHostResponseData;
import com.sankuai.avatar.collect.appkey.event.*;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.dao.cache.CacheClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RocketAppkeyHostDataCollectorTest {

    @Mock
    private RocketHttpClient mockRocketHttpClient;
    @Mock
    private AppkeyResource mockAppkeyResource;
    @Mock
    private ApplicationResource applicationResource;

    @Mock
    private CacheClient cacheClient;

    private RocketAppkeyHostDataCollector rocketAppkeyHostDataCollectorUnderTest;

    private CollectEvent collectEventData = null;

    @Before
    public void setUp() throws Exception {
        rocketAppkeyHostDataCollectorUnderTest = new RocketAppkeyHostDataCollector(mockRocketHttpClient,
                mockAppkeyResource, applicationResource, cacheClient);
        collectEventData = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of("appkey"))
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .build();
    }

    @Test
    public void testCollect() {
        // Setup
        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(null);

        // Configure RocketHttpClient.getAppkeyHosts(...).
        final RocketHostResponseData<RocketHost> rocketHostRocketHostResponseData = new RocketHostResponseData<>();
        rocketHostRocketHostResponseData.setTotal(0);
        rocketHostRocketHostResponseData.setOffset(0);
        rocketHostRocketHostResponseData.setLimit(0);
        rocketHostRocketHostResponseData.setData(Collections.emptyList());
        when(mockRocketHttpClient.getAppkeyHosts("appkey")).thenReturn(rocketHostRocketHostResponseData);

        // Configure RocketHttpClient.getAppkeyHostsByQueryRequest(...).
        final RocketHostResponseData<RocketHost> rocketHostRocketHostResponseData1 = new RocketHostResponseData<>();
        rocketHostRocketHostResponseData1.setTotal(0);
        rocketHostRocketHostResponseData1.setOffset(0);
        rocketHostRocketHostResponseData1.setLimit(0);
        rocketHostRocketHostResponseData1.setData(Collections.emptyList());
        when(mockRocketHttpClient.getAppkeyHostsByQueryRequest(HostQueryRequest.builder()
                .appkey("appkey")
                .env("prod")
                .build())).thenReturn(rocketHostRocketHostResponseData1);

        // Run the test
        final AppkeyCollectEventResult result = rocketAppkeyHostDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testCollect_RocketHttpClientGetAppkeyHostsThrowsSdkCallException() {
        // Setup
        when(mockRocketHttpClient.getAppkeyHosts("appkey")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> rocketAppkeyHostDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testCollect_RocketHttpClientGetAppkeyHostsThrowsSdkBusinessErrorException() {
        // Setup
        when(mockRocketHttpClient.getAppkeyHosts("appkey")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> rocketAppkeyHostDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testCollect_RocketHttpClientGetAppkeyHostsByQueryRequestThrowsSdkCallException() {
        // Setup

        // Configure RocketHttpClient.getAppkeyHosts(...).
        final RocketHostResponseData<RocketHost> rocketHostRocketHostResponseData = new RocketHostResponseData<>();
        rocketHostRocketHostResponseData.setTotal(0);
        rocketHostRocketHostResponseData.setOffset(0);
        rocketHostRocketHostResponseData.setLimit(0);
        rocketHostRocketHostResponseData.setData(Collections.emptyList());
        when(mockRocketHttpClient.getAppkeyHosts("appkey")).thenReturn(rocketHostRocketHostResponseData);

        when(mockRocketHttpClient.getAppkeyHostsByQueryRequest(HostQueryRequest.builder()
                .appkey("appkey")
                .env("prod")
                .build())).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> rocketAppkeyHostDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testCollect_RocketHttpClientGetAppkeyHostsByQueryRequestThrowsSdkBusinessErrorException() {
        // Setup
        // Configure RocketHttpClient.getAppkeyHosts(...).
        final RocketHostResponseData<RocketHost> rocketHostRocketHostResponseData = new RocketHostResponseData<>();
        rocketHostRocketHostResponseData.setTotal(0);
        rocketHostRocketHostResponseData.setOffset(0);
        rocketHostRocketHostResponseData.setLimit(0);
        rocketHostRocketHostResponseData.setData(Collections.emptyList());
        when(mockRocketHttpClient.getAppkeyHosts("appkey")).thenReturn(rocketHostRocketHostResponseData);

        when(mockRocketHttpClient.getAppkeyHostsByQueryRequest(HostQueryRequest.builder()
                .appkey("appkey")
                .env("prod")
                .build())).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> rocketAppkeyHostDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkBusinessErrorException.class);
    }
}
