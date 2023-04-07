package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.resource.application.bo.ApplicationBO;
import com.sankuai.avatar.resource.application.bo.ApplicationDetailBO;
import com.sankuai.avatar.resource.application.bo.TeamBO;
import com.sankuai.avatar.resource.application.bo.UserOwnerApplicationBO;
import com.sankuai.avatar.resource.application.request.ApplicationPageRequestBO;
import com.sankuai.avatar.resource.application.request.ScQueryApplicationBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import com.sankuai.avatar.web.dto.application.*;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeNodeDTO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeUserDTO;
import com.sankuai.avatar.web.request.application.ApplicationPageRequestDTO;
import com.sankuai.avatar.web.service.impl.ApplicationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * ApplicationService的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationResource mockApplicationResource;

    @InjectMocks
    private ApplicationServiceImpl applicationServiceImplUnderTest;


    /**
     * 获取应用列表功能逻辑测试
     */
    @Test
    public void testGetApplications() {
        // Setup
        final ApplicationPageRequestDTO requestDTO = new ApplicationPageRequestDTO();
        requestDTO.setPage(1);
        requestDTO.setPageSize(10);
        requestDTO.setMember("member");

        final PageResponse<ApplicationDTO> expectedResult = new PageResponse<>();
        expectedResult.setPage(1);
        expectedResult.setTotalPage(2);
        expectedResult.setPageSize(10);
        expectedResult.setTotalCount(17);
        expectedResult.setItems(Arrays.asList(
                ApplicationDTO.builder().name("Domain").chName("域名管理系统").build(),
                ApplicationDTO.builder().name("Avatar").chName("阿凡达运维平台").build()
        ));

        // Configure ApplicationResource.getApplications(...).
        final PageResponse<ApplicationBO> boPageResponse = new PageResponse<>();
        boPageResponse.setPage(1);
        boPageResponse.setTotalPage(2);
        boPageResponse.setPageSize(10);
        boPageResponse.setTotalCount(17);
        boPageResponse.setItems(Arrays.asList(
                ApplicationBO.builder().name("Domain").chName("域名管理系统").build(),
                ApplicationBO.builder().name("Avatar").chName("阿凡达运维平台").build()
        ));
        ApplicationPageRequestBO requestBO = new ApplicationPageRequestBO();
        requestBO.setPage(1);
        requestBO.setPageSize(10);
        requestBO.setMember("member");
        when(mockApplicationResource.getApplications(requestBO)).thenReturn(boPageResponse);

        // Run the test
        final PageResponse<ApplicationDTO> result = applicationServiceImplUnderTest.getApplications(requestDTO);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * 获取应用详情功能测试
     */
    @Test
    public void testGetApplication() {
        // Setup
        final ApplicationDetailDTO expectedResult = ApplicationDetailDTO.builder().build();
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
        expectedResult.setTeam(OrgTreeNodeDTO.builder().build());
        expectedResult.setAdmin(OrgTreeUserDTO.builder().build());
        expectedResult.setPms(Arrays.asList(OrgTreeUserDTO.builder().build()));
        expectedResult.setAppKeyCount(0);
        final TeamDTO adminTeam = new TeamDTO();
        expectedResult.setAdminTeam(adminTeam);

        // Configure ApplicationResource.getApplication(...).
        final ApplicationDetailBO applicationDetailBO = new ApplicationDetailBO();
        applicationDetailBO.setId(0);
        applicationDetailBO.setName("name");
        applicationDetailBO.setChName("chName");
        applicationDetailBO.setAppKeyTotal(0);
        applicationDetailBO.setIsPublic(false);
        applicationDetailBO.setPortalLink("portalLink");
        applicationDetailBO.setDescription("description");
        applicationDetailBO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        applicationDetailBO.setDomains(Arrays.asList("value"));
        applicationDetailBO.setBillingUnitName("billingUnitName");
        applicationDetailBO.setProductName("productName");
        applicationDetailBO.setTeam(OrgTreeNodeBO.builder().build());
        applicationDetailBO.setAdmin(OrgTreeUserBO.builder().build());
        applicationDetailBO.setPms(Arrays.asList(OrgTreeUserBO.builder().build()));
        applicationDetailBO.setAppKeyCount(0);
        final TeamBO team = new TeamBO();
        applicationDetailBO.setAdminTeam(team);
        when(mockApplicationResource.getApplication("name")).thenReturn(applicationDetailBO);

        // Run the test
        final ApplicationDetailDTO result = applicationServiceImplUnderTest.getApplication("name");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * 测试检索应用QueryApplications
     */
    @Test
    public void testQueryApplications() {
        final ApplicationPageRequestDTO requestDTO = new ApplicationPageRequestDTO();
        requestDTO.setPage(1);
        requestDTO.setPageSize(10);
        requestDTO.setQuery("Avatar");

        PageResponse<ScQueryApplicationBO> scUserOwnerApplicationPageResponse = new PageResponse<>();
        scUserOwnerApplicationPageResponse.setPage(1);
        scUserOwnerApplicationPageResponse.setPageSize(1);
        scUserOwnerApplicationPageResponse.setTotalPage(1);
        scUserOwnerApplicationPageResponse.setTotalCount(1);
        scUserOwnerApplicationPageResponse.setItems(Collections.singletonList(
                ScQueryApplicationBO.builder().applicationName("Avatar").build()
        ));

        // Setup
        when(mockApplicationResource.queryApplications(Mockito.any(ApplicationPageRequestBO.class))).thenReturn(scUserOwnerApplicationPageResponse);

        // Run the test
        PageResponse<ScQueryApplicationDTO> userOwnerApplicationBOPageResponse = applicationServiceImplUnderTest.queryApplications(requestDTO);
        assertThat(userOwnerApplicationBOPageResponse.getItems()).isNotEmpty();

    }

    /**
     * 测试getUserOwnerApplications
     */
    @Test
    public void testGetUserOwnerApplications() {
        final ApplicationPageRequestDTO requestDTO = new ApplicationPageRequestDTO();
        requestDTO.setPage(1);
        requestDTO.setPageSize(10);
        requestDTO.setMis("qinwei05");

        PageResponse<UserOwnerApplicationBO> scUserOwnerApplicationPageResponse = new PageResponse<>();
        scUserOwnerApplicationPageResponse.setItems(Collections.singletonList(
                UserOwnerApplicationBO.builder().name("Domain").build()
        ));

        // Setup
        when(mockApplicationResource.getUserOwnerApplications(Mockito.any(ApplicationPageRequestBO.class))).thenReturn(scUserOwnerApplicationPageResponse);

        // Run the test
        PageResponse<UserOwnerApplicationDTO> userOwnerApplicationBOPageResponse = applicationServiceImplUnderTest.getUserOwnerApplications(requestDTO);
        assertThat(userOwnerApplicationBOPageResponse.getItems()).isNotEmpty();

    }


}
