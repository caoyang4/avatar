package com.sankuai.avatar.resource.orgRole;

import com.sankuai.avatar.client.org.OrgClient;
import com.sankuai.avatar.client.org.model.Org;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.dao.cache.OrgRoleAdminCacheRepository;
import com.sankuai.avatar.dao.resource.repository.OrgRoleAdminRepository;
import com.sankuai.avatar.dao.resource.repository.model.OrgRoleAdminDO;
import com.sankuai.avatar.dao.resource.repository.request.OrgRoleAdminRequest;
import com.sankuai.avatar.resource.orgRole.bo.OrgBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgRoleAdminBO;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.resource.orgRole.impl.OrgRoleAdminResourceImpl;
import com.sankuai.avatar.resource.orgRole.request.OrgRoleAdminRequestBO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
/**
 * @author caoyang
 * @create 2022-11-10 17:22
 */
public class OrgRoleAdminResourceTest extends TestBase {

    @Mock
    private OrgRoleAdminRepository repository;

    @Mock
    private OrgRoleAdminCacheRepository cacheRepository;

    @Mock
    private OrgClient orgClient;

    private OrgRoleAdminResource resource;

    static OrgRoleAdminDO orgRoleAdminDO = new OrgRoleAdminDO();
    static {
        orgRoleAdminDO.setOrgId(UUID.randomUUID().toString().substring(0,8));
        orgRoleAdminDO.setOrgName("unit");
        orgRoleAdminDO.setRole("op_admin");
        orgRoleAdminDO.setRoleUsers("unit");
        orgRoleAdminDO.setUpdateUser("unit");
        orgRoleAdminDO.setOrgPath("unit");
        orgRoleAdminDO.setGroupId("");
    }
    static OrgRoleAdminBO orgRoleAdminBO = new OrgRoleAdminBO();
    static {
        orgRoleAdminBO.setOrgId(UUID.randomUUID().toString().substring(0,8));
        orgRoleAdminBO.setOrgName("unit");
        orgRoleAdminBO.setRole(OrgRoleType.OP_ADMIN);
        orgRoleAdminBO.setRoleUsers("unit");
        orgRoleAdminBO.setUpdateUser("unit");
        orgRoleAdminBO.setOrgPath("unit");
        orgRoleAdminBO.setGroupId("");
    }

    static Org org = new Org();
    static {
        org.setOrgId("1021866");
        org.setOrgName("变更管理开发组");
        org.setOrgNamePath("公司-美团-基础研发平台-基础技术部-服务运维部-运维工具开发组-变更管理开发组");
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        EntityHelper.initEntityNameMap(OrgRoleAdminDO.class, new Config());
        resource = new OrgRoleAdminResourceImpl(repository, cacheRepository, orgClient);
    }

    @Test
    public void queryPage() {
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        PageResponse<OrgRoleAdminBO> pageResponse = resource.queryPage(OrgRoleAdminRequestBO.builder().build());
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(10, pageResponse.getPageSize());
    }

    @Test
    public void queryOrgOpRoles() {
        List<String> orgIds = Collections.singletonList("123");
        when(cacheRepository.multiGetOpRole(Mockito.anyList())).thenReturn(Collections.emptyList());
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.emptyList());
        List<OrgRoleAdminBO> boList0 = resource.queryOrgOpRoles(orgIds);
        Assert.assertTrue(boList0.isEmpty());
        orgRoleAdminDO.setUpdateUser("test1");
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        List<OrgRoleAdminBO> boList1 = resource.queryOrgOpRoles(orgIds);
        Assert.assertEquals(1, boList1.size());
        Assert.assertEquals("test1", boList1.get(0).getUpdateUser());
        orgRoleAdminDO.setUpdateUser("test2");
        when(cacheRepository.multiGetOpRole(Mockito.anyList())).thenReturn(Collections.singletonList(orgRoleAdminDO));
        List<OrgRoleAdminBO> boList2 = resource.queryOrgOpRoles(orgIds);
        Assert.assertEquals(1, boList2.size());
        Assert.assertEquals("test2", boList2.get(0).getUpdateUser());
    }

    @Test
    public void queryOrgEpRoles() {
        List<String> orgIds = Collections.singletonList("123");
        when(cacheRepository.multiGetEpRole(Mockito.anyList())).thenReturn(Collections.emptyList());
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.emptyList());
        List<OrgRoleAdminBO> boList0 = resource.queryOrgEpRoles(orgIds);
        Assert.assertTrue(boList0.isEmpty());
        orgRoleAdminDO.setUpdateUser("test1");
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        List<OrgRoleAdminBO> boList1 = resource.queryOrgEpRoles(orgIds);
        Assert.assertEquals(1, boList1.size());
        Assert.assertEquals("test1", boList1.get(0).getUpdateUser());
        orgRoleAdminDO.setUpdateUser("test2");
        when(cacheRepository.multiGetEpRole(Mockito.anyList())).thenReturn(Collections.singletonList(orgRoleAdminDO));
        List<OrgRoleAdminBO> boList2 = resource.queryOrgEpRoles(orgIds);
        Assert.assertEquals(1, boList2.size());
        Assert.assertEquals("test2", boList2.get(0).getUpdateUser());
    }

    @Test
    public void save() {
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.emptyList());
        when(repository.insert(Mockito.any(OrgRoleAdminDO.class))).thenReturn(true);
        Assert.assertTrue(resource.save(orgRoleAdminBO));
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        when(repository.update(Mockito.any(OrgRoleAdminDO.class))).thenReturn(false);
        Assert.assertFalse(resource.save(orgRoleAdminBO));
    }

    @Test
    public void deleteByCondition() {
        Assert.assertFalse(resource.deleteByCondition(null));
        orgRoleAdminDO.setId(1);
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        when(repository.delete(Mockito.anyInt())).thenReturn(true);
        Assert.assertTrue(resource.deleteByCondition(OrgRoleAdminRequestBO.builder().role(OrgRoleType.OP_ADMIN).build()));
    }

    @Test
    public void getOrgByOrgClient() {
        String orgId = "1021866";
        when(orgClient.getOrgByOrgId(Mockito.anyString())).thenReturn(org);
        OrgBO orgBO = resource.getOrgByOrgClient(orgId);
        Assert.assertNotNull(orgBO);
        Assert.assertEquals(orgId, orgBO.getOrgId());
        Assert.assertEquals("变更管理开发组", orgBO.getOrgName());
    }

    @Test
    public void getRoleUsers() {
        String orgId = "1021866";
        when(cacheRepository.getOrgRoleUsers(Mockito.anyString(), Mockito.anyString())).thenReturn("xxx");
        String roleUsers = resource.getRoleUsers(orgId, OrgRoleType.OP_ADMIN);
        Assert.assertEquals("xxx", roleUsers);
        when(cacheRepository.getOrgRoleUsers(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        when(cacheRepository.get(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        when(cacheRepository.set(Mockito.any(OrgRoleAdminDO.class), Mockito.anyInt())).thenReturn(true);
        when(cacheRepository.setOrgRoleUsers(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
        roleUsers = resource.getRoleUsers(orgId, OrgRoleType.OP_ADMIN);
        Assert.assertEquals("unit", roleUsers);
    }

    @Test
    public void getRoleUserMap() {
        String orgId = "1021866";
        when(cacheRepository.getOrgRoleUsers(Mockito.anyString(), Mockito.anyString())).thenReturn("xxx");
        Map<String, String> userMap = resource.getRoleUserMap(orgId, OrgRoleType.OP_ADMIN);
        Assert.assertEquals("xxx", userMap.get("1021866"));
    }

    @Test
    public void getRoleUsersNoCache() {
        String orgId = "1021866";
        when(cacheRepository.get(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        when(cacheRepository.set(Mockito.any(OrgRoleAdminDO.class), Mockito.anyInt())).thenReturn(true);
        when(cacheRepository.setOrgRoleUsers(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
        String roleUsers = resource.getRoleUsersNoCache(orgId, OrgRoleType.OP_ADMIN);
        Assert.assertEquals("unit", roleUsers);
    }

    @Test
    public void cacheRoleUsers() {
        String orgId = "1021866";
        when(cacheRepository.get(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        when(cacheRepository.set(Mockito.any(OrgRoleAdminDO.class), Mockito.anyInt())).thenReturn(true);
        when(cacheRepository.setOrgRoleUsers(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
        Assert.assertTrue(resource.cacheRoleUsers(orgId, OrgRoleType.OP_ADMIN));
        Assert.assertTrue(resource.cacheRoleUsers(orgId, OrgRoleType.OP_ADMIN, "xxx"));
    }

    @Test
    public void cacheOrgRoleAdminBO() {
        when(cacheRepository.multiSet(Mockito.anyList(), Mockito.anyInt())).thenReturn(true);
        Assert.assertTrue(resource.cacheOrgRoleAdminBO(Collections.singletonList(orgRoleAdminBO)));
    }

    @Test
    public void getAncestorOrgRole() {
        String orgId = "150044";
        when(orgClient.getOrgByOrgId(Mockito.anyString())).thenReturn(org);
        when(cacheRepository.get(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        orgRoleAdminDO.setOrgId(orgId);
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        when(cacheRepository.set(Mockito.any(OrgRoleAdminDO.class), Mockito.anyInt())).thenReturn(true);
        OrgRoleAdminBO ancestor = resource.getAncestorOrgRole(orgId, OrgRoleType.OP_ADMIN);
        Assert.assertEquals(orgId, ancestor.getOrgId());
    }

    @Test
    public void getChildrenOrgRole() {
        String orgId = "150044";
        orgRoleAdminDO.setOrgId(orgId);
        when(orgClient.getOrgByOrgId(Mockito.anyString())).thenReturn(org);
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        List<OrgRoleAdminBO> children = resource.getChildrenOrgRole(orgId, OrgRoleType.OP_ADMIN);
        Assert.assertTrue(CollectionUtils.isNotEmpty(children));
        Assert.assertEquals(orgId, children.get(0).getOrgId());
    }

    @Test
    public void queryOrgOpRolesWithNoCache() {
        String orgId = "150044";
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        List<OrgRoleAdminBO> boList = resource.queryOrgOpRolesWithNoCache(Collections.singletonList(orgId));
        Assert.assertTrue(CollectionUtils.isNotEmpty(boList));
    }

    @Test
    public void queryOrgEpRolesWithNoCache() {
        String orgId = "150044";
        when(repository.query(Mockito.any(OrgRoleAdminRequest.class))).thenReturn(Collections.singletonList(orgRoleAdminDO));
        List<OrgRoleAdminBO> boList = resource.queryOrgEpRolesWithNoCache(Collections.singletonList(orgId));
        Assert.assertTrue(CollectionUtils.isNotEmpty(boList));
    }
}