package com.sankuai.avatar.resource.application;

import com.sankuai.avatar.client.soa.ScHttpClient;
import com.sankuai.avatar.client.soa.model.*;
import com.sankuai.avatar.client.soa.request.ApplicationPageRequest;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.bo.ApplicationBO;
import com.sankuai.avatar.resource.application.bo.ApplicationDetailBO;
import com.sankuai.avatar.resource.application.bo.TeamBO;
import com.sankuai.avatar.resource.application.bo.UserOwnerApplicationBO;
import com.sankuai.avatar.resource.application.impl.ApplicationResourceImpl;
import com.sankuai.avatar.resource.application.request.ApplicationPageRequestBO;
import com.sankuai.avatar.resource.application.request.ScQueryApplicationBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * ApplicationResource的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationResourceImplTest {

    @Mock
    private ScHttpClient mockScHttpClient;

    private ApplicationResource applicationResourceImplUnderTest;

    @Before
    public void setUp() {
        applicationResourceImplUnderTest = new ApplicationResourceImpl(mockScHttpClient);
    }

    /**
     * 测试getApplications功能逻辑
     */
    @Test
    public void testGetApplications() {
        // Setup
        final ApplicationPageRequestBO requestBO = new ApplicationPageRequestBO();
        requestBO.setPage(1);
        requestBO.setPageSize(10);
        requestBO.setMember("member");

        final PageResponse<ApplicationBO> expectedResult = new PageResponse<>();
        expectedResult.setPage(1);
        expectedResult.setTotalPage(2);
        expectedResult.setPageSize(10);
        expectedResult.setTotalCount(17);
        expectedResult.setItems(Arrays.asList(
                ApplicationBO.builder().name("Domain").chName("域名管理系统").build(),
                ApplicationBO.builder().name("Avatar").chName("阿凡达运维平台").build()
        ));

        // Configure ScHttpClient.getApplications(...).
        final ScPageResponse<ScApplication> scApplicationScPageResponse = new ScPageResponse<>();
        scApplicationScPageResponse.setTn(17);
        scApplicationScPageResponse.setCn(1);
        scApplicationScPageResponse.setPn(2);
        scApplicationScPageResponse.setSn(10);
        scApplicationScPageResponse.setItems(Arrays.asList(
                ScApplication.builder().name("Domain").chName("域名管理系统").build(),
                ScApplication.builder().name("Avatar").chName("阿凡达运维平台").build()
        ));

        ApplicationPageRequest request = new ApplicationPageRequest();
        request.setPage(1);
        request.setPageSize(10);
        request.setMember("member");
        when(mockScHttpClient.getApplications(request)).thenReturn(scApplicationScPageResponse);

        // Run the test
        final PageResponse<ApplicationBO> result = applicationResourceImplUnderTest.getApplications(requestBO);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * 测试getApplication功能逻辑
     */
    @Test
    public void testGetApplication() {
        // Setup
        final ApplicationDetailBO expectedResult = new ApplicationDetailBO();
        expectedResult.setId(0);
        expectedResult.setName("name");
        expectedResult.setChName("chName");
        expectedResult.setAppKeyTotal(0);
        expectedResult.setIsPublic(false);
        expectedResult.setPortalLink("portalLink");
        expectedResult.setDescription("description");
        expectedResult.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        expectedResult.setDomains(Arrays.asList("value"));
        expectedResult.setBillingUnitName("billingUnitName");
        expectedResult.setProductName("productName");
        expectedResult.setTeam(OrgTreeNodeBO.builder().build());
        expectedResult.setAdmin(OrgTreeUserBO.builder().build());
        expectedResult.setPms(Arrays.asList(OrgTreeUserBO.builder().build()));
        expectedResult.setAppKeyCount(0);
        final TeamBO adminTeam = new TeamBO();
        expectedResult.setAdminTeam(adminTeam);

        ScApplicationDetail scApplicationDetail = new ScApplicationDetail();
        scApplicationDetail.setId(0);
        scApplicationDetail.setName("name");
        scApplicationDetail.setChName("chName");
        scApplicationDetail.setAppKeyTotal(0);
        scApplicationDetail.setIsPublic(false);
        scApplicationDetail.setPortalLink("portalLink");
        scApplicationDetail.setDescription("description");
        scApplicationDetail.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        scApplicationDetail.setDomains(Arrays.asList("value"));
        scApplicationDetail.setBillingUnitName("billingUnitName");
        scApplicationDetail.setProductName("productName");
        scApplicationDetail.setTeam(ScOrgTreeNode.builder().build());
        scApplicationDetail.setAdmin(ScUser.builder().build());
        scApplicationDetail.setPms(Arrays.asList(ScUser.builder().build()));
        final ScTeam scTeam = new ScTeam();
        scApplicationDetail.setAdminTeam(scTeam);

        when(mockScHttpClient.getApplication("name")).thenReturn(scApplicationDetail);

        // Run the test
        final ApplicationDetailBO result = applicationResourceImplUnderTest.getApplication("name");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * 测试getApplication边界Case: ScHttpClient抛出SdkBusinessErrorException
     */
    @Test
    public void testGetApplicationThatThrowsSdkBusinessErrorException() {
        // Setup
        when(mockScHttpClient.getApplication("name")).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.getApplication("name"))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockScHttpClient).getApplication(Mockito.anyString());
    }


    /**
     * 测试getApplication边界Case: ScHttpClient抛出SdkCallException
     */
    @Test
    public void testGetApplicationThatThrowsSdkCallException1() {
        // Setup
        when(mockScHttpClient.getApplication("name")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.getApplication("name"))
                .isInstanceOf(SdkCallException.class);
        verify(mockScHttpClient).getApplication(Mockito.anyString());
    }


    /**
     * 测试getApplication边界Case: ScHttpClient抛出SdkBusinessErrorException
     */
    @Test
    public void testGetApplications_ScHttpClientThrowsSdkBusinessErrorException() {
        // Setup
        final ApplicationPageRequestBO requestBO = new ApplicationPageRequestBO();
        requestBO.setPage(0);
        requestBO.setPageSize(0);
        requestBO.setQuery("query");
        requestBO.setOrgIds("orgIds");
        requestBO.setDomain("domain");
        requestBO.setIsPublic(false);
        requestBO.setAdmin("admin");
        requestBO.setMember("member");
        requestBO.setStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        requestBO.setEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        final ApplicationPageRequest request = new ApplicationPageRequest();
        request.setPage(0);
        request.setPageSize(0);
        request.setQuery("query");
        request.setOrgIds("orgIds");
        request.setDomain("domain");
        request.setIsPublic(false);
        request.setAdmin("admin");
        request.setMember("member");
        request.setStartTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        request.setEndTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockScHttpClient.getApplications(request)).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.getApplications(requestBO))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockScHttpClient).getApplications(any(ApplicationPageRequest.class));
    }


    /**
     * 测试getApplication边界Case: ScHttpClient抛出SdkCallException
     */
    @Test
    public void testGetApplicationThatThrowsSdkCallException() {
        // Setup
        when(mockScHttpClient.getApplication("name")).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.getApplication("name"))
                .isInstanceOf(SdkCallException.class);
        verify(mockScHttpClient).getApplication(Mockito.anyString());
    }

    /**
     * 测试getPaasApplications边界Case: ScHttpClient抛出SdkCallException
     */
    @Test
    public void testGetPaasApplications_ScHttpClientThrowsSdkCallException1() {
        // Setup
        when(mockScHttpClient.getAllPaasApplications()).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.getPaasApplications())
                .isInstanceOf(SdkCallException.class);
        verify(mockScHttpClient).getAllPaasApplications();
    }


    /**
     * 测试getPaasApplications边界Case: ScHttpClient抛出SdkBusinessErrorException
     */
    @Test
    public void testGetPaasApplicationsThatThrowsSdkBusinessErrorException() {
        // Setup

        when(mockScHttpClient.getAllPaasApplications()).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.getPaasApplications())
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockScHttpClient).getAllPaasApplications();
    }


    /**
     * 测试getPaasApplications
     */
    @Test
    public void testGetPaasApplications() {
        // Setup
        when(mockScHttpClient.getAllPaasApplications()).thenReturn(Collections.singletonList("pass"));

        // Run the test
        List<String> paas = applicationResourceImplUnderTest.getPaasApplications();
        assertThat(paas).hasSize(1);

    }

    /**
     * 测试getUserOwnerApplications边界Case: ScHttpClient抛出SdkCallException
     */
    @Test
    public void testGetUserOwnerApplications_ScHttpClientThrowsSdkCallException() {
        final ApplicationPageRequestBO requestBO = new ApplicationPageRequestBO();
        requestBO.setPage(1);
        requestBO.setPageSize(10);
        requestBO.setMember("member");
        // Setup
        when(mockScHttpClient.getUserOwnerApplications(Mockito.any(ApplicationPageRequest.class))).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.getUserOwnerApplications(requestBO))
                .isInstanceOf(SdkCallException.class);
        verify(mockScHttpClient).getUserOwnerApplications(Mockito.any(ApplicationPageRequest.class));
    }


    /**
     * 测试getUserOwnerApplications边界Case: ScHttpClient抛出SdkBusinessErrorException
     */
    @Test
    public void testGetUserOwnerApplications_ThrowsSdkBusinessErrorException() {
        final ApplicationPageRequestBO requestBO = new ApplicationPageRequestBO();
        requestBO.setPage(1);
        requestBO.setPageSize(10);
        requestBO.setMember("member");
        // Setup

        when(mockScHttpClient.getUserOwnerApplications(Mockito.any(ApplicationPageRequest.class))).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.getUserOwnerApplications(requestBO))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockScHttpClient).getUserOwnerApplications(Mockito.any(ApplicationPageRequest.class));
    }


    /**
     * 测试getUserOwnerApplications
     */
    @Test
    public void testGetUserOwnerApplications() {
        final ApplicationPageRequestBO requestBO = new ApplicationPageRequestBO();
        requestBO.setPage(1);
        requestBO.setPageSize(10);
        requestBO.setMis("member");

        final ApplicationPageRequest request = new ApplicationPageRequest();
        request.setPage(1);
        request.setPageSize(10);
        request.setMis("member");

        ScPageResponse<ScUserOwnerApplication> scUserOwnerApplicationPageResponse = new ScPageResponse<>();
        scUserOwnerApplicationPageResponse.setTn(17);
        scUserOwnerApplicationPageResponse.setCn(1);
        scUserOwnerApplicationPageResponse.setPn(2);
        scUserOwnerApplicationPageResponse.setSn(10);
        scUserOwnerApplicationPageResponse.setItems(Arrays.asList(
                ScUserOwnerApplication.builder().name("Domain").build(),
                ScUserOwnerApplication.builder().name("Avatar").build()
        ));

        // Setup
        when(mockScHttpClient.getUserOwnerApplications(request)).thenReturn(scUserOwnerApplicationPageResponse);

        // Run the test
        PageResponse<UserOwnerApplicationBO> userOwnerApplicationBOPageResponse = applicationResourceImplUnderTest.getUserOwnerApplications(requestBO);
        assertThat(userOwnerApplicationBOPageResponse.getItems()).isNotEmpty();

    }

    /**
     * 测试testQueryApplications边界Case: ScHttpClient抛出SdkCallException
     */
    @Test
    public void testQueryApplications_ScHttpClientThrowsSdkCallException() {
        final ApplicationPageRequestBO requestBO = new ApplicationPageRequestBO();
        requestBO.setPage(1);
        requestBO.setPageSize(10);
        requestBO.setQuery("Avatar");
        // Setup
        when(mockScHttpClient.queryApplications(Mockito.any(ApplicationPageRequest.class))).thenThrow(SdkCallException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.queryApplications(requestBO))
                .isInstanceOf(SdkCallException.class);
        verify(mockScHttpClient).queryApplications(Mockito.any(ApplicationPageRequest.class));
    }


    /**
     * 测试testQueryApplications边界Case: ScHttpClient抛出SdkBusinessErrorException
     */
    @Test
    public void testQueryApplications_ThrowsSdkBusinessErrorException() {
        final ApplicationPageRequestBO requestBO = new ApplicationPageRequestBO();
        requestBO.setPage(1);
        requestBO.setPageSize(10);
        requestBO.setQuery("Avatar");
        // Setup

        when(mockScHttpClient.queryApplications(Mockito.any(ApplicationPageRequest.class))).thenThrow(SdkBusinessErrorException.class);

        // Run the test
        assertThatThrownBy(() -> applicationResourceImplUnderTest.queryApplications(requestBO))
                .isInstanceOf(SdkBusinessErrorException.class);
        verify(mockScHttpClient).queryApplications(Mockito.any(ApplicationPageRequest.class));
    }


    /**
     * 测试getUserOwnerApplications
     */
    @Test
    public void testQueryApplications() {
        final ApplicationPageRequestBO requestBO = new ApplicationPageRequestBO();
        requestBO.setPage(1);
        requestBO.setPageSize(10);
        requestBO.setQuery("Avatar");

        ScPageResponse<ScQueryApplication> scUserOwnerApplicationPageResponse = new ScPageResponse<>();
        scUserOwnerApplicationPageResponse.setTn(1);
        scUserOwnerApplicationPageResponse.setCn(1);
        scUserOwnerApplicationPageResponse.setPn(1);
        scUserOwnerApplicationPageResponse.setSn(10);
        scUserOwnerApplicationPageResponse.setItems(Collections.singletonList(
                ScQueryApplication.builder().applicationName("Avatar").costProductId(1).costProduct("bill").build()
        ));

        // Setup
        when(mockScHttpClient.queryApplications(Mockito.any(ApplicationPageRequest.class)))
                .thenReturn(scUserOwnerApplicationPageResponse);

        // Run the test
        PageResponse<ScQueryApplicationBO> userOwnerApplicationBOPageResponse = applicationResourceImplUnderTest.queryApplications(requestBO);
        assertThat(userOwnerApplicationBOPageResponse.getItems()).isNotEmpty();
    }
}
