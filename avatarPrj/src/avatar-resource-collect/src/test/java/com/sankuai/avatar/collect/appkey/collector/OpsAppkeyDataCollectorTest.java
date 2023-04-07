package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.collector.source.AppkeySource;
import com.sankuai.avatar.collect.appkey.collector.source.OpsAppkeySource;
import com.sankuai.avatar.collect.appkey.event.*;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.dao.cache.CacheClient;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.OpsSrvBO;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.resource.tree.AppkeyTreeResource;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeOwtBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreePdlBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OpsAppkeyDataCollectorTest {

    private OpsAppkeyDataCollector opsAppkeyDataCollectorUnderTest;

    private CollectEvent collectEventData = null;

    @Mock
    private AppkeyResource mockAppkeyResource;

    @Mock
    private AppkeyTreeResource mockAppkeyTreeResource;

    @Mock
    private ApplicationResource applicationResource;

    @Mock
    private CacheClient cacheClient;

    @Before
    public void setUp() throws Exception {
        opsAppkeyDataCollectorUnderTest = new OpsAppkeyDataCollector(mockAppkeyResource, mockAppkeyTreeResource, applicationResource, cacheClient);
        collectEventData = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of("appkey"))
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .build();
    }

    @Test
    public void testCollect() {
        // Setup
        final OpsAppkeySource opsAppkeySource = new OpsAppkeySource();
        opsAppkeySource.setAppkey("appkey");
        opsAppkeySource.setOwt("meituan.owt");
        opsAppkeySource.setPdl("meituan.owt.pdl");
        opsAppkeySource.setSrv("meituan.owt.pdl.srv");

        final OpsSrvBO opsSrvBO = new OpsSrvBO();
        opsSrvBO.setAppkey("appkey");
        opsSrvBO.setKey("meituan.owt.pdl.srv");

        AppkeyTreeBO appkeyTreeBO = new AppkeyTreeBO();
        AppkeyTreeOwtBO appkeyTreeOwtBO = AppkeyTreeOwtBO.builder().key("owtKey").name("owtName").businessGroup("bg").build();
        AppkeyTreePdlBO appkeyTreePdlBO = AppkeyTreePdlBO.builder().key("pdlKey").name("pdlName").build();
        appkeyTreeBO.setOwt(appkeyTreeOwtBO);
        appkeyTreeBO.setPdl(appkeyTreePdlBO);

        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(opsAppkeySource);

        // Run the test

        when(mockAppkeyResource.getAppkeyByOps("appkey")).thenReturn(opsSrvBO);
        when(mockAppkeyTreeResource.getAppkeyTreeByKey(opsSrvBO.getKey())).thenReturn(appkeyTreeBO);


        //when(opsAppkeyDataCollectorUnderTest.getAppkeyByOps("appkey")).thenReturn(null);

        final AppkeyCollectEventResult result = opsAppkeyDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        Appkey appkeyResourceData = new Appkey("appkey");
        for (AppkeySource source: result.getAppkeySourceList()){
            assertThat(source).isInstanceOf(AppkeySource.class);
            appkeyResourceData = source.transToAppkey(appkeyResourceData);
            assertThat(appkeyResourceData).isNotNull();
            assertThat(appkeyResourceData.getAppkey()).isEqualTo(collectEventData.getCollectEventData().getAppkey());
        }

    }
}
