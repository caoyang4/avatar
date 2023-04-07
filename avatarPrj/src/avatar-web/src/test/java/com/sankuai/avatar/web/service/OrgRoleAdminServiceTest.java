package com.sankuai.avatar.web.service;

import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.orgRole.DxGroupResource;
import com.sankuai.avatar.resource.orgRole.OrgRoleAdminResource;
import com.sankuai.avatar.resource.orgRole.bo.DxGroupBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgRoleAdminBO;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.resource.orgRole.request.OrgRoleAdminRequestBO;
import com.sankuai.avatar.sdk.entity.servicecatalog.Org;
import com.sankuai.avatar.sdk.entity.servicecatalog.OrgInfo;
import com.sankuai.avatar.sdk.entity.servicecatalog.User;
import com.sankuai.avatar.sdk.manager.ServiceCatalogOrg;
import com.sankuai.avatar.web.dto.orgRole.DxGroupDTO;
import com.sankuai.avatar.web.dto.orgRole.OrgRoleAdminDTO;
import com.sankuai.avatar.web.dto.orgRole.OrgSreTreeDTO;
import com.sankuai.avatar.web.dto.user.DxUserDTO;
import com.sankuai.avatar.web.request.OrgRoleAdminPageRequest;
import com.sankuai.avatar.web.service.impl.OrgRoleAdminServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrgRoleAdminServiceTest  {

    @Mock
    private ServiceCatalogOrg serviceCatalogOrg;
    @Mock
    private OrgRoleAdminResource orgRoleAdminResource;
    @Mock
    private DxGroupResource dxGroupResource;
    @Mock
    private UserService userService;

    private OrgRoleAdminService orgRoleAdminService;

    static OrgSreTreeDTO orgSreTreeDTO = new OrgSreTreeDTO();
    static DxUserDTO dxUserDTO = new DxUserDTO();
    static {
        dxUserDTO.setMis("zhangzhe");
        dxUserDTO.setName("name");
        dxUserDTO.setLeader("leader");
        dxUserDTO.setAvatarUrl("avatarUrl");
        dxUserDTO.setOrg("org");

        orgSreTreeDTO.setId("123");
        orgSreTreeDTO.setName("name");
        orgSreTreeDTO.setAppkeyCount(10);
        orgSreTreeDTO.setDisplayName("displayName");
        orgSreTreeDTO.setOrgPath("orgId");
        orgSreTreeDTO.setRoleUsers("roleUsers");
        orgSreTreeDTO.setChildren(Collections.singletonList(new OrgSreTreeDTO()));
        orgSreTreeDTO.setOpAdmins(Collections.singletonList(dxUserDTO));
    }
    static Org scOrg = new Org();
    static OrgInfo orgInfo = new OrgInfo();
    static User leader = new User();

    static {
        scOrg.setId(1021866);
        scOrg.setName("name");
        scOrg.setAncestors(null);
        scOrg.setDisplayName("displayName");
        scOrg.setHasChild(false);
        Org children = JsonUtil.json2Bean(JsonUtil.bean2Json(scOrg), Org.class);
        children.setId(1021867);
        scOrg.setChildren(Collections.singletonList(children));

        leader.setMis("mis");
        leader.setName("name");
        leader.setOrg(scOrg);
        leader.setAvatarUrl("avatarUrl");

        orgInfo.setAppKeyCount(10);
        orgInfo.setApplicationCount(1);
        orgInfo.setLeader(leader);
    }

    static OrgRoleAdminBO orgRoleAdminBO = new OrgRoleAdminBO();
    static OrgRoleAdminDTO orgRoleAdminDTO = new OrgRoleAdminDTO();
    static {
        orgRoleAdminBO.setOrgId("1021866");
        orgRoleAdminBO.setOrgName("unit");
        orgRoleAdminBO.setRole(OrgRoleType.OP_ADMIN);
        orgRoleAdminBO.setRoleUsers("unit");
        orgRoleAdminBO.setUpdateUser("unit");
        orgRoleAdminBO.setOrgPath("unit");
        orgRoleAdminBO.setGroupId("");

        orgRoleAdminDTO.setOrgId("1021866");
        orgRoleAdminDTO.setOrgName("unit");
        orgRoleAdminDTO.setRole(OrgRoleType.OP_ADMIN);
        orgRoleAdminDTO.setRoleUsers("unit");
        orgRoleAdminDTO.setUpdateUser("unit");
        orgRoleAdminDTO.setOrgPath("unit");
        orgRoleAdminDTO.setGroupId("");
    }

    static DxGroupBO dxGroupBO = new DxGroupBO();
    static DxGroupDTO dxGroupDTO = new DxGroupDTO();

    static {
        dxGroupBO.setGroupId("654321");
        dxGroupBO.setGroupName("groupName");
        dxGroupBO.setGroupStatus("groupStatus");
        dxGroupBO.setUpdateUser("updateUser");

        dxGroupDTO.setGroupId("123456");
        dxGroupDTO.setGroupName("groupName");
        dxGroupDTO.setGroupStatus("groupStatus");
        dxGroupDTO.setUpdateUser("updateUser");
    }
    static OrgBO orgBO = new OrgBO();
    static {
        orgBO.setOrgId("1021866");
        orgBO.setOrgName("orgName");
        orgBO.setStatus(0);
        orgBO.setOrgPath("orgPath");
        orgBO.setOrgNamePath("orgName");
        orgBO.setBgId("bgId");
        orgBO.setBgName("bgName");
    }

    @Before
    public void setUp() {
        orgRoleAdminService = new OrgRoleAdminServiceImpl(serviceCatalogOrg, orgRoleAdminResource,
                                                          dxGroupResource, userService);
    }

    @Test
    public void testGetOrgSreTreeListByOrgId() throws Exception {
        final String ORG_INFO_KEY = "ORG_INFO_KEY";
        List<Org> orgList = Collections.singletonList(scOrg);
        when(serviceCatalogOrg.listUserOrg(Mockito.anyString())).thenReturn(orgList);
        when(orgRoleAdminResource.getRoleUserMap(Mockito.anyString(),Mockito.any(OrgRoleType.class))).thenReturn(ImmutableMap.of("1021867","zhangzhe"));
        String cache = SquirrelUtils.get(ORG_INFO_KEY + "1021866,1021867");
        if (StringUtils.isEmpty(cache)) {
            when(serviceCatalogOrg.getOrgInfo(Mockito.anyString())).thenReturn(orgInfo);
        }
        when(userService.getDxUserByMis(Mockito.anyList())).thenReturn(Collections.singletonList(dxUserDTO));
        List<OrgSreTreeDTO> result = orgRoleAdminService.getOrgSreTreeListByOrgId("caoyang42", "1021866");
        verify(serviceCatalogOrg, times(1)).listUserOrg(Mockito.any());
        verify(orgRoleAdminResource, times(1)).getRoleUserMap(Mockito.any(), Mockito.any());
        verify(userService, times(1)).getDxUserByMis(Mockito.any());
        Assert.assertTrue(CollectionUtils.isNotEmpty(result));
        Assert.assertEquals(orgInfo.getAppKeyCount(), result.get(0).getAppkeyCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(result.get(0).getOpAdmins()));
        Assert.assertEquals("zhangzhe", result.get(0).getOpAdmins().get(0).getMis());
    }

    @Test
    public void testQueryPage() {
        final OrgRoleAdminPageRequest request = new OrgRoleAdminPageRequest();
        final PageResponse<OrgRoleAdminBO> boPageResponse = new PageResponse<>();
        boPageResponse.setPage(1);
        boPageResponse.setTotalPage(1);
        boPageResponse.setPageSize(10);
        boPageResponse.setTotalCount(1);
        boPageResponse.setItems(Collections.singletonList(orgRoleAdminBO));
        when(orgRoleAdminResource.queryPage(Mockito.any(OrgRoleAdminRequestBO.class))).thenReturn(boPageResponse);
        final PageResponse<OrgRoleAdminDTO> result = orgRoleAdminService.queryPage(request);
        verify(orgRoleAdminResource, times(1)).queryPage(Mockito.any());
        Assert.assertNotNull(result);
        Assert.assertEquals("unit", result.getItems().get(0).getOrgName());
    }

    @Test
    public void testGetDxGroupByGroupIds() {

        final List<DxGroupBO> dxGroupBOS = Collections.singletonList(dxGroupBO);
        when(dxGroupResource.getDxGroupByGroupIds(Mockito.anyList())).thenReturn(dxGroupBOS);
        final List<DxGroupDTO> result = orgRoleAdminService.getDxGroupByGroupIds(Collections.singletonList("123"));
        verify(dxGroupResource, times(1)).getDxGroupByGroupIds(Mockito.anyList());
        Assert.assertNotNull(result);
        Assert.assertEquals("654321", result.get(0).getGroupId());
    }

    @Test
    public void testGetDxGroupByGroupIdsNoItems() {
        when(dxGroupResource.getDxGroupByGroupIds(Mockito.anyList())).thenReturn(Collections.emptyList());
        final List<DxGroupDTO> result = orgRoleAdminService.getDxGroupByGroupIds(Collections.singletonList("123"));
        assertThat(result).isEqualTo(Collections.emptyList());
        verify(dxGroupResource,times(1)).getDxGroupByGroupIds(Mockito.anyList());
    }

    @Test
    public void testSaveOrgRoleAdmin() {
        when(orgRoleAdminResource.save(Mockito.any(OrgRoleAdminBO.class))).thenReturn(true);
        final List<OrgRoleAdminBO> orgRoleAdminBOList = Collections.singletonList(orgRoleAdminBO);
        when(orgRoleAdminResource.getChildrenOrgRole(Mockito.anyString(),Mockito.any(OrgRoleType.class))).thenReturn(orgRoleAdminBOList);
        when(orgRoleAdminResource.deleteByCondition(Mockito.any(OrgRoleAdminRequestBO.class))).thenReturn(false);
        orgRoleAdminDTO.setOrgId("1573");
        final boolean result = orgRoleAdminService.saveOrgRoleAdmin(orgRoleAdminDTO, true);
        verify(orgRoleAdminResource,times(1)).deleteByCondition(Mockito.any(OrgRoleAdminRequestBO.class));
        verify(orgRoleAdminResource,times(1)).getChildrenOrgRole(Mockito.anyString(), Mockito.any());
        assertThat(result).isTrue();
    }

    @Test
    public void testSaveOrgRoleAdminNotDeleteChildren() {
        when(orgRoleAdminResource.save(Mockito.any(OrgRoleAdminBO.class))).thenReturn(false);
        final boolean result = orgRoleAdminService.saveOrgRoleAdmin(orgRoleAdminDTO, false);
        assertThat(result).isFalse();
        verify(orgRoleAdminResource,times(0)).deleteByCondition(Mockito.any(OrgRoleAdminRequestBO.class));
        verify(orgRoleAdminResource,times(0)).getChildrenOrgRole(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testSaveOrgDxGroup() {
        final List<DxGroupDTO> dxGroupDTOList = Collections.singletonList(dxGroupDTO);
        final List<OrgRoleAdminBO> orgRoleAdminBOList = Collections.singletonList(orgRoleAdminBO);
        when(orgRoleAdminResource.queryOrgOpRolesWithNoCache(Mockito.anyList())).thenReturn(orgRoleAdminBOList);
        when(orgRoleAdminResource.getOrgByOrgClient(Mockito.anyString())).thenReturn(orgBO);
        when(dxGroupResource.batchSave(Mockito.anyList())).thenReturn(true);
        when(orgRoleAdminResource.save(Mockito.any(OrgRoleAdminBO.class))).thenReturn(true);
        final boolean result = orgRoleAdminService.saveOrgDxGroup("1573", dxGroupDTOList);
        assertThat(result).isTrue();
        verify(dxGroupResource, times(1)).batchSave(Mockito.anyList());
        verify(orgRoleAdminResource, times(1)).queryOrgOpRolesWithNoCache(Mockito.anyList());
        verify(orgRoleAdminResource, times(1)).getOrgByOrgClient(Mockito.anyString());
        verify(orgRoleAdminResource,times(0)).getAncestorOrgRole(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testSaveOrgDxGroupNoItems() {
        final List<DxGroupDTO> dxGroupDTOList = Collections.singletonList(dxGroupDTO);
        when(orgRoleAdminResource.queryOrgOpRolesWithNoCache(Mockito.anyList())).thenReturn(Collections.emptyList());
        when(orgRoleAdminResource.getOrgByOrgClient(Mockito.anyString())).thenReturn(orgBO);
        when(dxGroupResource.batchSave(Mockito.anyList())).thenReturn(true);
        when(orgRoleAdminResource.getAncestorOrgRole(Mockito.anyString(),Mockito.any(OrgRoleType.class))).thenReturn(orgRoleAdminBO);
        when(orgRoleAdminResource.save(Mockito.any(OrgRoleAdminBO.class))).thenReturn(true);
        final boolean result = orgRoleAdminService.saveOrgDxGroup("1573", dxGroupDTOList);

        assertThat(result).isTrue();
        verify(dxGroupResource, times(1)).batchSave(Mockito.anyList());
        verify(orgRoleAdminResource,times(1)).queryOrgOpRolesWithNoCache(Mockito.anyList());
        verify(orgRoleAdminResource,times(1)).getOrgByOrgClient(Mockito.anyString());
        verify(orgRoleAdminResource,times(1)).getAncestorOrgRole(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testGetByOrgIdAndRole() {
        when(orgRoleAdminResource.getByOrgIdAndRole(Mockito.anyString(),Mockito.any(OrgRoleType.class))).thenReturn(orgRoleAdminBO);
        final OrgRoleAdminDTO result = orgRoleAdminService.getByOrgIdAndRole("1021866", OrgRoleType.OP_ADMIN);
        verify(orgRoleAdminResource,times(1)).getByOrgIdAndRole(Mockito.anyString(),Mockito.any());
        Assert.assertEquals("1021866", result.getOrgId());
    }

    @Test
    public void testGetChildrenOrgByOrgId() {
        final List<OrgRoleAdminBO> orgRoleAdminBOList = Collections.singletonList(orgRoleAdminBO);
        when(orgRoleAdminResource.getChildrenOrgRole(Mockito.anyString(),Mockito.any(OrgRoleType.class))).thenReturn(orgRoleAdminBOList);
        final List<OrgRoleAdminDTO> result = orgRoleAdminService.getChildrenOrgByOrgId("1021866", OrgRoleType.EP_ADMIN);
        verify(orgRoleAdminResource,times(1)).getChildrenOrgRole(Mockito.anyString(),Mockito.any());
        Assert.assertTrue(CollectionUtils.isNotEmpty(result));
        Assert.assertEquals("1021866", result.get(0).getOrgId());
    }

    @Test
    public void testGetChildrenOrgByOrgIdNoItems() {
        when(orgRoleAdminResource.getChildrenOrgRole(Mockito.anyString(),Mockito.any(OrgRoleType.class))).thenReturn(Collections.emptyList());
        final List<OrgRoleAdminDTO> result = orgRoleAdminService.getChildrenOrgByOrgId("10086", OrgRoleType.OP_ADMIN);
        assertThat(result).isEqualTo(Collections.emptyList());
        verify(orgRoleAdminResource, times(1)).getChildrenOrgRole(Mockito.anyString(),Mockito.any());
    }

    @Test
    public void testGetAncestorOrgByOrgId() {
        when(orgRoleAdminResource.getAncestorOrgRole(Mockito.anyString(),Mockito.any(OrgRoleType.class))).thenReturn(orgRoleAdminBO);
        final OrgRoleAdminDTO result = orgRoleAdminService.getAncestorOrgByOrgId("1021866", OrgRoleType.OP_ADMIN);
        Assert.assertEquals("1021866", result.getOrgId());
        verify(orgRoleAdminResource, times(1)).getAncestorOrgRole(Mockito.anyString(),Mockito.any());
    }

    @Test
    public void getAllDxGroup() {
        when(dxGroupResource.getAllDxGroup()).thenReturn(Collections.singletonList(dxGroupBO));
        Assert.assertTrue(CollectionUtils.isNotEmpty(orgRoleAdminService.getAllDxGroup()));
    }
}
