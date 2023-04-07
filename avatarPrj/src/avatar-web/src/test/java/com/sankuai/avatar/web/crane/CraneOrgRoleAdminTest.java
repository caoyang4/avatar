package com.sankuai.avatar.web.crane;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import com.sankuai.avatar.resource.orgRole.OrgRoleAdminResource;
import com.sankuai.avatar.resource.orgRole.bo.OrgBO;
import com.sankuai.avatar.resource.orgRole.bo.OrgRoleAdminBO;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import com.sankuai.avatar.resource.orgRole.request.OrgRoleAdminRequestBO;
import com.sankuai.avatar.resource.user.UserResource;
import com.sankuai.avatar.resource.user.bo.UserBO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CraneOrgRoleAdminTest {

    @Mock
    private OrgRoleAdminResource orgRoleAdminResource;
    @Mock
    private UserResource userResource;
    @Mock
    private DxMessageResource dxMessageResource;

    private CraneOrgRoleAdmin crane;

    static UserBO userBO1 = new UserBO();
    static UserBO userBO2 = new UserBO();
    static {
        userBO1.setNumber(666);
        userBO1.setName("unit-test");
        userBO1.setMis("mis1");
        userBO1.setSource("MT");
        userBO1.setJobStatus("在职");
        userBO1.setOrganization("公司/美团/基础研发平台/基础技术部/服务运维部/运维工具开发组/变更管理开发组");
        userBO1.setRole("rd");
        userBO1.setOrgPath("0-1-2-100046-150042-1573-150044-1021866");
        userBO1.setOrgId("1021866");
        userBO1.setLeader("xxx");
        userBO1.setUserImage("xxx");
        userBO2 = JsonUtil.json2Bean(JsonUtil.bean2Json(userBO1), UserBO.class);
        userBO2.setMis("mis2");
        userBO2.setJobStatus("离职");
    }
    static OrgRoleAdminBO orgRoleAdminBO = new OrgRoleAdminBO();
    static {
        orgRoleAdminBO.setOrgId("1021866");
        orgRoleAdminBO.setOrgName("unit");
        orgRoleAdminBO.setRole(OrgRoleType.OP_ADMIN);
        orgRoleAdminBO.setRoleUsers("mis1,mis2");
        orgRoleAdminBO.setUpdateUser("unit");
        orgRoleAdminBO.setOrgPath("unit");
        orgRoleAdminBO.setGroupId("");
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
    static PageResponse<OrgRoleAdminBO> boPageResponse1 = new PageResponse<>();
    static {
        boPageResponse1.setPage(1);
        boPageResponse1.setTotalPage(1);
        boPageResponse1.setPageSize(10);
        boPageResponse1.setTotalCount(1);
        boPageResponse1.setItems(Collections.singletonList(orgRoleAdminBO));
    }

    @Before
    public void setUp() {
        crane = new CraneOrgRoleAdmin(orgRoleAdminResource, userResource, dxMessageResource);
    }

    @Test
    public void testCorrectOrgRoleUser1() {
        OrgRoleAdminRequestBO requestBO1 = OrgRoleAdminRequestBO.builder().build();
        requestBO1.setPageSize(200);
        userBO1.setJobStatus("在职");
        when(orgRoleAdminResource.queryPage(requestBO1)).thenReturn(boPageResponse1);
        when(userResource.getUserByOrgDx("mis1")).thenReturn(userBO1);
        when(orgRoleAdminResource.getOrgByOrgClient(Mockito.anyString())).thenReturn(orgBO);
        when(orgRoleAdminResource.save(Mockito.any(OrgRoleAdminBO.class))).thenReturn(true);
        crane.correctOrgRoleUser();
        verify(orgRoleAdminResource, times(2)).queryPage(Mockito.any(OrgRoleAdminRequestBO.class));
        verify(userResource, times(1)).getUserByOrgDx(Mockito.any());
        verify(dxMessageResource,times(0)).pushDxMessage(Mockito.anyList(), Mockito.anyString());
    }

    @Test
    public void testCorrectOrgRoleUser2() {
        OrgRoleAdminRequestBO requestBO1 = OrgRoleAdminRequestBO.builder().build();
        requestBO1.setPageSize(200);
        userBO1.setJobStatus("离职");
        when(orgRoleAdminResource.queryPage(requestBO1)).thenReturn(boPageResponse1);
        when(userResource.getUserByOrgDx("mis1")).thenReturn(userBO1);
        when(dxMessageResource.pushDxMessage(Mockito.anyList(), Mockito.anyString())).thenReturn(true);
        when(orgRoleAdminResource.getOrgByOrgClient(Mockito.anyString())).thenReturn(orgBO);
        when(orgRoleAdminResource.save(Mockito.any(OrgRoleAdminBO.class))).thenReturn(true);
        crane.correctOrgRoleUser();
        verify(orgRoleAdminResource, times(2)).queryPage(Mockito.any(OrgRoleAdminRequestBO.class));
        verify(userResource, times(2)).getUserByOrgDx(Mockito.any());
        verify(dxMessageResource,times(1)).pushDxMessage(Mockito.anyList(), Mockito.anyString());
    }

    @Test
    public void testCorrectOrgRoleUserThrowsSdkBusinessErrorException() {
        OrgRoleAdminRequestBO requestBO1 = OrgRoleAdminRequestBO.builder().build();
        requestBO1.setPageSize(200);
        userBO1.setJobStatus("在职");
        when(orgRoleAdminResource.queryPage(requestBO1)).thenReturn(boPageResponse1);
        when(userResource.getUserByOrgDx("mis1")).thenReturn(userBO1);
        when(userResource.getUserByOrgDx("mis2")).thenReturn(userBO2);
        when(orgRoleAdminResource.getOrgByOrgClient(Mockito.anyString())).thenThrow(SdkBusinessErrorException.class);
        crane.correctOrgRoleUser();
        verify(orgRoleAdminResource,times(0)).save(Mockito.any(OrgRoleAdminBO.class));
        verify(dxMessageResource,times(0)).pushDxMessage(Mockito.anyList(), Mockito.anyString());
    }

    @Test
    public void testDeleteInValidOrg() {
        OrgRoleAdminRequestBO requestBO1 = OrgRoleAdminRequestBO.builder().build();
        requestBO1.setPageSize(200);
        when(orgRoleAdminResource.queryPage(requestBO1)).thenReturn(boPageResponse1);
        when(orgRoleAdminResource.getOrgByOrgClient(Mockito.anyString())).thenReturn(orgBO);
        when(orgRoleAdminResource.deleteByCondition(Mockito.any(OrgRoleAdminRequestBO.class))).thenReturn(true);
        crane.deleteInValidOrg();
        verify(orgRoleAdminResource, times(2)).queryPage(Mockito.any(OrgRoleAdminRequestBO.class));
        verify(orgRoleAdminResource, times(1)).getOrgByOrgClient(Mockito.anyString());
        verify(orgRoleAdminResource, times(1)).deleteByCondition(Mockito.any(OrgRoleAdminRequestBO.class));
    }

    @Test
    public void testCacheOrgRoleAdmin() {
        OrgRoleAdminRequestBO requestBO1 = OrgRoleAdminRequestBO.builder().build();
        requestBO1.setPageSize(200);
        when(orgRoleAdminResource.queryPage(requestBO1)).thenReturn(boPageResponse1);
        when(orgRoleAdminResource.cacheOrgRoleAdminBO(Mockito.anyList())).thenReturn(true);
        crane.cacheOrgRoleAdmin();
        verify(orgRoleAdminResource, times(1)).cacheOrgRoleAdminBO(Mockito.anyList());
    }
}
