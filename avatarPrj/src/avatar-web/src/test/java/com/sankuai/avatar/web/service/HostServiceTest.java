package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.appkey.AppkeyResource;
import com.sankuai.avatar.resource.appkey.bo.OpsSrvBO;
import com.sankuai.avatar.resource.host.HostResource;
import com.sankuai.avatar.resource.host.bo.DayuGroupTagBO;
import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.bo.HostBO;
import com.sankuai.avatar.resource.host.bo.IdcMetaDataBO;
import com.sankuai.avatar.resource.host.request.GroupTagQueryRequestBO;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import com.sankuai.avatar.web.dal.entity.CasUser;
import com.sankuai.avatar.web.dto.host.*;
import com.sankuai.avatar.web.request.GroupTagQueryRequest;
import com.sankuai.avatar.web.request.HostQueryRequest;
import com.sankuai.avatar.web.service.impl.HostServiceImpl;
import com.sankuai.avatar.web.util.UserUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author qinwei05
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {UserUtils.class})
public class HostServiceTest {

    @Mock
    private HostResource mockHostResource;
    @Mock
    private AppkeyResource mockAppkeyResource;

    private HostServiceImpl hostService;

    @Before
    public void setUp() throws Exception {
        hostService = new HostServiceImpl(mockHostResource, mockAppkeyResource);
    }

    private HostQueryRequest getHostQueryRequest() {
        final HostQueryRequest request = new HostQueryRequest();
        request.setPage(1);
        request.setPageSize(10);
        request.setEnv("test");
        request.setAppkey("appkey");
        request.setQuery("query");
        request.setSet("set");
        request.setSwimlane("swimlane");
        request.setGrouptags("grouptags");
        request.setNetType("netType");
        request.setManagePlat("managePlat");
        request.setIdcName("idcName");
        request.setKindName("kindName");
        request.setOriginType("originType");
        request.setRsNetType("rsNetType");
        return request;
    }

    private HostQueryRequest getSimpleHostQueryRequest() {
        final HostQueryRequest request = new HostQueryRequest();
        request.setPage(1);
        request.setPageSize(10);
        request.setEnv("test");
        request.setAppkey("appkey");
        return request;
    }

    static HostAttributesBO hostAttributesBO = new HostAttributesBO();
    static HostAttributesBO patchHostAttributesBO = new HostAttributesBO();
    static HostAttributesBO patchVmHostAttributesBO = new HostAttributesBO();

    static {
        hostAttributesBO.setIpLan("ipLan");
        hostAttributesBO.setName("name");
        hostAttributesBO.setEnv("test");
        hostAttributesBO.setCell("cell");
        hostAttributesBO.setIdc("idc");

        patchHostAttributesBO.setIpLan("ipLan");
        patchHostAttributesBO.setName("name");
        patchHostAttributesBO.setEnv("test");
        patchHostAttributesBO.setKindName("kindName");
        patchHostAttributesBO.setCell("cell");

        patchVmHostAttributesBO.setIpLan("ipLan");
        patchVmHostAttributesBO.setName("name");
        patchVmHostAttributesBO.setKindName("kindName");
        patchVmHostAttributesBO.setEnv("test");
        patchVmHostAttributesBO.setHostTags(Collections.singletonList("lvm磁盘"));
        patchVmHostAttributesBO.setCell("cell");
    }


    @Test
    public void testGetSrvHostsByQueryRequest() {
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");

        when(mockHostResource.getSrvHostsByQueryRequest(any(HostQueryRequestBO.class))).thenReturn(Collections.singletonList(hostAttributesBO));

        when(mockHostResource.patchHostTagsAndFeatures(Collections.singletonList(hostAttributesBO), "test"))
                .thenReturn(Collections.singletonList(patchHostAttributesBO));

        when(mockHostResource.patchVmHostDiskType(Collections.singletonList(patchHostAttributesBO), "appkey", "test"))
                .thenReturn(Collections.singletonList(patchVmHostAttributesBO));

        final PageResponse<HostAttributesDTO> result = hostService.getSrvHostsByQueryRequest(getSimpleHostQueryRequest());

        assertThat(result.getItems()).hasSize(1);

    }

    @Test
    public void testGetSrvHostsByQueryRequest_AppkeyResourceThrowsSdkCallException() {
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenThrow(SdkCallException.class);
        // Run the test
        assertThatThrownBy(() -> hostService.getSrvHostsByQueryRequest(getHostQueryRequest()))
                .isInstanceOf(SdkCallException.class);
    }

    @Test
    public void testGetSrvHostsByQueryRequest_AppkeyResourceThrowsSdkBusinessErrorException() {
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenThrow(SdkBusinessErrorException.class);
        // Run the test
        assertThatThrownBy(() -> hostService.getSrvHostsByQueryRequest(getHostQueryRequest()))
                .isInstanceOf(SdkBusinessErrorException.class);
    }

    @Test
    public void testGetHostsCountByQueryRequest() {
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");
        when(mockHostResource.getSrvHostsByQueryRequest(any(HostQueryRequestBO.class))).thenReturn(Collections.singletonList(hostAttributesBO));
        final HostCountDTO result = hostService.getHostsCountByQueryRequest(getHostQueryRequest());
        assertThat(result.getTest()).isEqualTo(1);
    }

    @Test
    public void testGetOriginSrvHostsByQueryRequest() {
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");
        when(mockHostResource.getSrvHostsByQueryRequest(any(HostQueryRequestBO.class))).thenReturn(Collections.singletonList(hostAttributesBO));
        final List<HostAttributesDTO> result = hostService.getOriginSrvHostsByQueryRequest(getHostQueryRequest());
        assertThat(result).hasSize(1);
    }

    @Test
    public void testGetHostInfo() {
        when(mockHostResource.getHostInfo("name")).thenReturn(HostBO.builder().name("name").build());
        final HostDTO result = hostService.getHostInfo("name");
        assertThat(result.getName()).isEqualTo("name");
    }

    @Test
    public void testGetHostCellByQueryRequest() throws InvocationTargetException, IllegalAccessException {
        HostAttributesBO hostAttributesBOTwo = new HostAttributesBO();
        BeanUtils.copyProperties(hostAttributesBOTwo, hostAttributesBO);
        hostAttributesBOTwo.setCell("");

        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");
        when(mockHostResource.getSrvHostsByQueryRequest(any(HostQueryRequestBO.class))).thenReturn(
                Arrays.asList(hostAttributesBO, hostAttributesBOTwo)
        );

        final List<HostCellDTO> result = hostService.getHostCellByQueryRequest(getHostQueryRequest());

        // Verify the results
        List<String> cellList = result.stream().map(HostCellDTO::getLabel).collect(Collectors.toList());
        assertThat(cellList).contains("主干道", "cell");
    }

    @Test
    public void testGetHostSumAttribute() {
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");

        when(mockHostResource.getSrvHostsByQueryRequest(any(HostQueryRequestBO.class))).thenReturn(Collections.singletonList(hostAttributesBO));

        when(mockHostResource.patchHostTagsAndFeatures(Collections.singletonList(hostAttributesBO), "test"))
                .thenReturn(Collections.singletonList(patchHostAttributesBO));

        when(mockHostResource.patchVmHostDiskType(Collections.singletonList(patchHostAttributesBO), "appkey", "test"))
                .thenReturn(Collections.singletonList(patchVmHostAttributesBO));

        final HostSumAttributeDTO result = hostService.getHostSumAttribute(getHostQueryRequest());

        assertThat(result.getCell()).hasSize(2);
        assertThat(result.getKindName()).hasSize(1);
    }

    @Test
    public void testGetAppkeyHostDistributed() {
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");
        when(mockHostResource.getSrvHostsByQueryRequest(any(HostQueryRequestBO.class))).thenReturn(Collections.singletonList(hostAttributesBO));

        final List<IdcMetaDataBO> idcMetaDataBOS = Collections.singletonList(
                new IdcMetaDataBO("city", "cnName", "region", "idc", "desc"));
        when(mockHostResource.getIdcList()).thenReturn(idcMetaDataBOS);

        // Run the test
        final HostIdcDistributedDTO result = hostService.getAppkeyHostDistributed(getHostQueryRequest());

        // Verify the results
        assertThat(result.getIdcList()).hasSize(1);
    }

    @Test
    public void testGetIdc() {
        final List<IdcMetaDataDTO> expectedResult = Collections.singletonList(
                new IdcMetaDataDTO("city", "idcName", "region", "idc", "desc"));

        final List<IdcMetaDataBO> idcMetaDataBOS = Collections.singletonList(
                new IdcMetaDataBO("city", "idcName", "region", "idc", "desc"));
        when(mockHostResource.getIdcList()).thenReturn(idcMetaDataBOS);

        // Run the test
        final List<IdcMetaDataDTO> result = hostService.getIdc();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetExternalHostInfo() {
        PowerMockito.mockStatic(UserUtils.class);
        CasUser casUser = new CasUser();
        casUser.setLoginName("testUser");
        PowerMockito.when(UserUtils.getCurrentCasUser()).thenReturn(casUser);

        when(mockHostResource.getHostInfo("name")).thenReturn(HostBO.builder().name("name").vendor("it").build());
        when(mockAppkeyResource.getByHost("name")).thenReturn("appkey");

        final OpsSrvBO opsSrvBO = new OpsSrvBO();
        opsSrvBO.setComment("comment");
        opsSrvBO.setRank("rank");
        opsSrvBO.setId(0);
        opsSrvBO.setRdAdmin("rdAdmin,testUser");
        opsSrvBO.setOpAdmin("opAdmin,testUser");
        when(mockAppkeyResource.getAppkeyByOps("appkey")).thenReturn(opsSrvBO);

        // Run the test
        final ExternalHostDTO result = hostService.getExternalHostInfo("name");

        // Verify the results
        assertThat(result.getHost().getName()).isEqualTo("name");
        assertThat(result.getCanDelete()).isTrue();
        assertThat(result.getCanUpdate()).isTrue();
    }

    @Test
    public void testGetExternalParentHostInfo() {
        when(mockHostResource.getHostInfo("name")).thenReturn(HostBO.builder().vendor("it").name("name").parent("parent").build());
        final List<HostDTO> result = hostService.getExternalParentHostInfo("name");
        assertThat(result).hasSize(1);
    }

    @Test
    public void testGetGrouptags() {
        // Setup
        final GroupTagQueryRequest request = new GroupTagQueryRequest("env", "appkey", "keyword", "owt");
        final GroupTagDTO groupTagDTO = new GroupTagDTO();
        groupTagDTO.setHostCount(0L);
        groupTagDTO.setGroupTagsName("groupTagsName");
        when(mockAppkeyResource.getSrvKeyByAppkey("appkey")).thenReturn("srv");

        final DayuGroupTagBO dayuGroupTagBO = new DayuGroupTagBO();
        dayuGroupTagBO.setId(0);
        dayuGroupTagBO.setGroupTagsName("groupTagsName");
        dayuGroupTagBO.setGroupTagsAlias("groupTagsAlias");
        dayuGroupTagBO.setGroupTags("groupTags");
        dayuGroupTagBO.setOwt("owt");
        dayuGroupTagBO.setHidden(false);
        dayuGroupTagBO.setDescription("description");
        dayuGroupTagBO.setCreator("creator");
        dayuGroupTagBO.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        dayuGroupTagBO.setUpdateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<DayuGroupTagBO> dayuGroupTagBOS = Collections.singletonList(dayuGroupTagBO);
        when(mockHostResource.getGrouptags(any(GroupTagQueryRequestBO.class)))
                .thenReturn(dayuGroupTagBOS);

        when(mockHostResource.getSrvHostsByQueryRequest(any(HostQueryRequestBO.class))).thenReturn(Collections.singletonList(hostAttributesBO));

        // Run the test
        final List<GroupTagDTO> result = hostService.getGrouptags(request);

        // Verify the results groupTagsName__groupTags
        assertThat(result.get(0).getGroupTagsName()).isEqualTo("groupTagsName__groupTags");
    }
}