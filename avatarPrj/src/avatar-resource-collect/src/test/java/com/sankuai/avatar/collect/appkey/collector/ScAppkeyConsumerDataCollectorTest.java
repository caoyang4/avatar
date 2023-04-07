package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.collector.source.AppkeySource;
import com.sankuai.avatar.collect.appkey.consumer.model.ScAppkeyConsumerData;
import com.sankuai.avatar.collect.appkey.event.*;
import com.sankuai.avatar.collect.appkey.model.Appkey;
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

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScAppkeyConsumerDataCollectorTest {

    @Mock
    private AppkeyResource mockAppkeyResource;
    @Mock
    private ApplicationResource applicationResource;

    @Mock
    private CacheClient cacheClient;

    private ScAppkeyConsumerDataCollector scAppkeyConsumerDataCollectorUnderTest;

    private CollectEvent collectEventData = null;

    @Before
    public void setUp() throws Exception {
        scAppkeyConsumerDataCollectorUnderTest = new ScAppkeyConsumerDataCollector(mockAppkeyResource, applicationResource, cacheClient);
        ScAppkeyConsumerData scAppkeyConsumerData = new ScAppkeyConsumerData();
        scAppkeyConsumerData.setAppKey("appkey");
        final ScAppkeyConsumerData.Members members = new ScAppkeyConsumerData.Members();
        members.setMis("mis");
        scAppkeyConsumerData.setMembers(Collections.singletonList(members));
        final ScAppkeyConsumerData.Team team = new ScAppkeyConsumerData.Team();
        team.setName("team");
        team.setId("12");
        team.setDisplayName("DisplayName");
        scAppkeyConsumerData.setTeam(team);
        final ScAppkeyConsumerData.Admin admin = new ScAppkeyConsumerData.Admin();
        admin.setMis("mis");
        scAppkeyConsumerData.setAdmin(admin);
        scAppkeyConsumerData.setCategories(Collections.singletonList("value"));
        scAppkeyConsumerData.setTags(Collections.singletonList("value"));

        collectEventData = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of("appkey", scAppkeyConsumerData))
                .collectEventName(CollectEventName.SC_APPKEY_UPDATE)
                .build();
    }

    @Test
    public void testCollect() {
        // Setup
        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(null);

        // Configure AppkeyResource.getAppkeyByScV1(...).
        final ScAppkeyBO scAppkeyBO = new ScAppkeyBO();
        scAppkeyBO.setAppKey("appKey");
        scAppkeyBO.setDescription("description");
        scAppkeyBO.setApplicationName("applicationName");
        scAppkeyBO.setModuleName("moduleName");
        scAppkeyBO.setServiceName("serviceName");
        final ScAppkeyBO.ApplicationAdmin applicationAdmin = new ScAppkeyBO.ApplicationAdmin();
        applicationAdmin.setMis("mis");
        scAppkeyBO.setApplicationAdmin(applicationAdmin);
        final ScAppkeyBO.Team team = new ScAppkeyBO.Team();
        team.setName("team");
        team.setId("12");
        team.setOrgIdList("1/2/3");
        team.setDisplayName("DisplayName");
        scAppkeyBO.setTeam(team);
        final ScAppkeyBO.Admin admin = new ScAppkeyBO.Admin();
        admin.setMis("mis");
        scAppkeyBO.setAdmin(admin);
        final ScAppkeyBO.Members members = new ScAppkeyBO.Members();
        members.setMis("mis");
        scAppkeyBO.setMembers(Collections.singletonList(members));
        scAppkeyBO.setFrameworks(Collections.singletonList("value"));
        scAppkeyBO.setTenant("tenant");
        scAppkeyBO.setIsBoughtExternal("isBoughtExternal");
        scAppkeyBO.setEpAdmin("epAdmin");
        scAppkeyBO.setOpAdmin("opAdmin");
        scAppkeyBO.setCompatibleIpv6(false);
        scAppkeyBO.setCategories(Collections.singletonList("value"));
        scAppkeyBO.setTags(Collections.singletonList("value"));
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenReturn(scAppkeyBO);

        // Run the test
        final AppkeyCollectEventResult result = scAppkeyConsumerDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        Appkey appkeyResourceData = new Appkey("appkey");
        for (AppkeySource source: result.getAppkeySourceList()){
            assertThat(source).isInstanceOf(AppkeySource.class);
            appkeyResourceData = source.transToAppkey(appkeyResourceData);
            assertThat(appkeyResourceData).isNotNull();
            assertThat(appkeyResourceData.getAppkey()).isEqualTo(collectEventData.getCollectEventData().getAppkey());
        }
    }

    @Test
    public void testCollect_AppkeyResourceThrowsSdkCallException() {
        // Setup
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> scAppkeyConsumerDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testCollect_AppkeyResourceThrowsSdkBusinessErrorException() {
        // Setup
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> scAppkeyConsumerDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkBusinessErrorException.class);
    }
}
