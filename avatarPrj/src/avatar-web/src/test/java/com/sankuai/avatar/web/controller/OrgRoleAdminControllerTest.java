package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.orgRole.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-11-14 16:01
 */
public class OrgRoleAdminControllerTest extends TestBase {

    private final String baseUrl = "/api/v2/avatar/orgRoleAdmin";

    @Test
    public void getPageOrgRoleAdmin() throws Exception {
        String url = baseUrl + "?pageSize=1&orgId=1573";
        PageResponse<OrgRoleAdminVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertEquals(1, pageResponse.getPageSize());
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
        List<OrgRoleAdminVO> voList = Arrays.asList(Objects.requireNonNull(JsonUtil.json2Bean(JsonUtil.bean2Json(pageResponse.getItems()), OrgRoleAdminVO[].class)));
        Assert.assertEquals("1573", voList.get(0).getOrgId());
    }

    @Test
    public void saveOrgRoleAdmin() throws Exception {
        OrgRoleAdminVO orgRoleAdminVO = new OrgRoleAdminVO();
        orgRoleAdminVO.setOrgId("1021866");
        orgRoleAdminVO.setRole("op_admin");
        orgRoleAdminVO.setRoleUsers("caoyang42,qinwei05");
        orgRoleAdminVO.setDeleteAllChild(false);
        Boolean result = postMock(baseUrl, orgRoleAdminVO, Boolean.class);
        Assert.assertTrue(result);
    }

    @Test
    public void saveOrgDxGroup() throws Exception {
        OrgDxGroupVO orgDxGroupVO = new OrgDxGroupVO();
        orgDxGroupVO.setOrgId("1021866");
        DxGroupVO dxGroupVO1 = new DxGroupVO();
        dxGroupVO1.setGroupId("65455601106");
        dxGroupVO1.setGroupName("F4-流星花园");
        dxGroupVO1.setGroupStatus("");
        DxGroupVO dxGroupVO2 = new DxGroupVO();
        dxGroupVO2.setGroupId("65455601108");
        dxGroupVO2.setGroupName("F4-勇敢飞");
        dxGroupVO2.setGroupStatus("");
        orgDxGroupVO.setDxGroupList(Arrays.asList(dxGroupVO1, dxGroupVO2));
        String url = baseUrl + "/dxgroup";
        Boolean result = postMock(url, orgDxGroupVO, Boolean.class);
        Assert.assertTrue(result);
    }

    @Test
    public void getOrgRoleAdminTreeByOrgId() throws Exception{
        String url = baseUrl + "/org/1021866?role=op_admin";
        OrgRoleAdminTreeVO roleAdminTreeVO = getMock(url, OrgRoleAdminTreeVO.class);
        Assert.assertEquals("1021866", roleAdminTreeVO.getOrgId());
        Assert.assertTrue(StringUtils.isNotEmpty(roleAdminTreeVO.getRoleUsers()));
    }

    @Test
    public void getOrgRoleAdminTreeByMis() throws Exception{
        String url = baseUrl + "/user/caoyang42?role=op_admin";
        OrgRoleAdminTreeVO roleAdminTreeVO = getMock(url, OrgRoleAdminTreeVO.class);
        Assert.assertEquals("1021866", roleAdminTreeVO.getOrgId());
        Assert.assertTrue(StringUtils.isNotEmpty(roleAdminTreeVO.getRoleUsers()));
    }

    @Test
    public void getOrgSreTreeByMis() throws Exception{
        String url = baseUrl + "/tree?orgIds=100046,150042&mis=caoyang42";
        List<OrgSreTreeVO> sreTreeVOList = Arrays.asList(getMock(url, OrgSreTreeVO[].class));
        Assert.assertTrue(CollectionUtils.isNotEmpty(sreTreeVOList));
    }

    @Test
    public void getOrgApplication() throws Exception{
        String url = baseUrl + "/role?orgIds=100046,150042,1573,150044";
        OrgRoleAdminDetailVO orgRoleAdminDetailVO = getMock(url, OrgRoleAdminDetailVO.class);
        Assert.assertNotNull(orgRoleAdminDetailVO);
        Assert.assertEquals("150044", orgRoleAdminDetailVO.getOpRoleAdmin().getOrgId());
    }

    @Test
    public void getDxGroupVO() throws Exception {
        String url1 = baseUrl + "/dxgroup";
        List<DxGroupVO> dxGroupVOList1 = Arrays.asList(getMock(url1, DxGroupVO[].class));
        Assert.assertTrue(CollectionUtils.isNotEmpty(dxGroupVOList1));
        String url2 = baseUrl + "/dxgroup?groupIds=64066052078,123";
        List<DxGroupVO> dxGroupVOList2 = Arrays.asList(getMock(url2, DxGroupVO[].class));
        Assert.assertTrue(CollectionUtils.isNotEmpty(dxGroupVOList2));
    }
}