package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.event.*;
import com.sankuai.avatar.common.exception.ResourceNotFoundErrorException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.ScAppkeyBO;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.dao.cache.CacheClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OffLineAppkeyDataCollectorTest {

    @Mock
    private AppkeyResource mockAppkeyResource;
    @Mock
    private ApplicationResource mockApplicationResource;
    @Mock
    private CacheClient mockCacheClient;

    private OffLineAppkeyDataCollector offLineAppkeyDataCollectorUnderTest;

    private CollectEvent collectEventData = null;

    @Before
    public void setUp() throws Exception {
        offLineAppkeyDataCollectorUnderTest = new OffLineAppkeyDataCollector(mockAppkeyResource,
                mockApplicationResource, mockCacheClient);
        collectEventData = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of("appkey"))
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .build();
    }

    @Test
    public void testCollect() {
        // Setup
        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(null);
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("result");

        // Configure AppkeyResource.getAppkeyBySc(...).
        final ScAppkeyBO scAppkeyBO = new ScAppkeyBO();
        scAppkeyBO.setAppKey("appKey");
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenReturn(scAppkeyBO);

        // Run the test
        final AppkeyCollectEventResult result = offLineAppkeyDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testCollect_AppkeyResourceGetSrvKeyByAppkeyThrowsSdkCallException() {
        // Setup
        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(null);
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenThrow(SdkCallException.class);

        // Run the test
        final AppkeyCollectEventResult result = offLineAppkeyDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    public void testCollect_AppkeyResourceGetSrvKeyByAppkeyThrowsSdkBusinessErrorException() {
        // Setup
        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(null);
        ResourceNotFoundErrorException resourceNotFoundErrorException = new ResourceNotFoundErrorException(404, "");
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenThrow(resourceNotFoundErrorException);

        // Run the test
        final AppkeyCollectEventResult result = offLineAppkeyDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testCollect_AppkeyResourceGetAppkeyByScReturnsNull() {
        // Setup
        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(null);
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("result");
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenReturn(null);

        // Run the test
        final AppkeyCollectEventResult result = offLineAppkeyDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testCollect_AppkeyResourceGetAppkeyByScThrowsSdkCallException() {
        // Setup
        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(null);
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("result");
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenThrow(SdkCallException.class);

        // Run the test
        final AppkeyCollectEventResult result = offLineAppkeyDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testCollect_AppkeyResourceGetAppkeyByScThrowsSdkBusinessErrorException() {
        // Setup
        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(null);
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("result");
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        final AppkeyCollectEventResult result = offLineAppkeyDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        assertThat(result).isNotNull();
    }
}
