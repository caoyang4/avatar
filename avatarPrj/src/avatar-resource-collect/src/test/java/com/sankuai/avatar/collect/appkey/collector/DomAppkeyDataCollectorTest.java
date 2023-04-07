package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.event.*;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.AppkeyResourceUtilBO;
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
public class DomAppkeyDataCollectorTest {

    @Mock
    private AppkeyResource mockAppkeyResource;

    @Mock
    private ApplicationResource applicationResource;

    @Mock
    private CacheClient cacheClient;

    private DomAppkeyDataCollector domAppkeyDataCollectorUnderTest;

    private CollectEvent collectEventData = null;

    @Before
    public void setUp() throws Exception {
        domAppkeyDataCollectorUnderTest = new DomAppkeyDataCollector(mockAppkeyResource, applicationResource, cacheClient);
        collectEventData = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of("appkey"))
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .build();
    }

    @Test
    public void testCollect() {
        // Setup
        // Configure AppkeyResource.getAppkeyResourceUtil(...).
        final AppkeyResourceUtilBO appkeyResourceUtilBO = new AppkeyResourceUtilBO();
        appkeyResourceUtilBO.setStandardReason("standardReason");
        final AppkeyResourceUtilBO.CellList cellList = new AppkeyResourceUtilBO.CellList();
        cellList.setYearPeekValue(0.0);
        appkeyResourceUtilBO.setCellList(Collections.singletonList(cellList));
        final AppkeyResourceUtilBO.ResourceUtil resourceUtil = new AppkeyResourceUtilBO.ResourceUtil();
        resourceUtil.setLastWeekValue(0.0);
        appkeyResourceUtilBO.setResourceUtil(resourceUtil);

        when(mockAppkeyResource.getAppkeyResourceUtil("appkey")).thenReturn(appkeyResourceUtilBO);

        final AppkeyCollectEventResult result = domAppkeyDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testCollect_AppkeyResourceThrowsSdkCallException() {
        // Setup
        when(mockAppkeyResource.getAppkeyResourceUtil("appkey")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> domAppkeyDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testCollect_AppkeyResourceThrowsSdkBusinessErrorException() {
        // Setup
        when(mockAppkeyResource.getAppkeyResourceUtil("appkey")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> domAppkeyDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkBusinessErrorException.class);
    }
}
