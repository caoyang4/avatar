package com.sankuai.avatar.collect.appkey.collector;

import com.sankuai.avatar.collect.appkey.event.*;
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

@RunWith(MockitoJUnitRunner.Silent.class)
public class ScAppkeyDataCollectorTest {

    @Mock
    private AppkeyResource mockAppkeyResource;
    @Mock
    private ApplicationResource applicationResource;
    @Mock
    private CacheClient cacheClient;

    private ScAppkeyDataCollector scAppkeyDataCollectorUnderTest;

    private CollectEvent collectEventData = null;

    @Before
    public void setUp() throws Exception {
        scAppkeyDataCollectorUnderTest = new ScAppkeyDataCollector(mockAppkeyResource, applicationResource, cacheClient);
        collectEventData = AppkeyCollectEvent.builder()
                .collectEventData(AppkeyCollectEventData.of("appkey"))
                .collectEventName(CollectEventName.APPKEY_REFRESH)
                .build();
    }

    private ScAppkeyBO getScAppkeyBO(){
        // Configure AppkeyResource.getAppkeyBySc(...).
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
        return scAppkeyBO;
    }

    @Test
    public void testCollect() {
        // Setup
        final AppkeyCollectEventResult expectedResult = AppkeyCollectEventResult.ofSource(null);

        // Configure AppkeyResource.getAppkeyBySc(...).
        final ScAppkeyBO scAppkeyBO = getScAppkeyBO();
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenReturn(scAppkeyBO);

        // Configure AppkeyResource.getAppkeyByScV1(...).
        final ScAppkeyBO scAppkeyBO1 = getScAppkeyBO();
        // Run the test
        final AppkeyCollectEventResult result = scAppkeyDataCollectorUnderTest.collect(collectEventData);

        // Verify the results
        assertThat(result).isNotNull();
    }

    @Test
    public void testCollect_AppkeyResourceGetAppkeyByScV2ThrowsSdkCallException() {
        // Setup
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> scAppkeyDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testCollect_AppkeyResourceGetAppkeyByScV2ThrowsSdkBusinessErrorException() {
        // Setup
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> scAppkeyDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testCollect_AppkeyResourceGetAppkeyByScV1ThrowsSdkCallException() {
        // Setup

        // Configure AppkeyResource.getAppkeyBySc(...).
        final ScAppkeyBO scAppkeyBO = getScAppkeyBO();

        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> scAppkeyDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testCollect_AppkeyResourceGetAppkeyByScV1ThrowsSdkBusinessErrorException() {
        // Setup

        // Configure AppkeyResource.getAppkeyBySc(...).
        final ScAppkeyBO scAppkeyBO = getScAppkeyBO();
        when(mockAppkeyResource.getAppkeyBySc("appkey")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> scAppkeyDataCollectorUnderTest.collect(collectEventData))
                .isInstanceOf(SdkBusinessErrorException.class);
    }
}
