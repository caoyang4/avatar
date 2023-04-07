package com.sankuai.avatar.web.controller.open;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.orgRole.OrgRoleAdminTreeVO;
import com.sankuai.avatar.web.vo.orgRole.OrgRoleAdminVO;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author caoyang
 * @create 2023-02-09 16:01
 */
public class OrgRoleAdminOpenControllerTest extends TestBase {

    @Test
    public void getOrgRoleAdminTreeByOrgId() throws Exception {
        String url = "/open/api/v2/avatar/orgRoleAdmin/org/1021866?role=op_admin";
        OrgRoleAdminTreeVO roleAdminTreeVO = getMock(url, OrgRoleAdminTreeVO.class);
        Assert.assertEquals("1021866", roleAdminTreeVO.getOrgId());
        Assert.assertTrue(StringUtils.isNotEmpty(roleAdminTreeVO.getRoleUsers()));
    }

    @Test
    public void getPageOrgRoleAdmin() throws Exception {
        String url = "/open/api/v2/avatar/orgRoleAdmin?pageSize=1";
        PageResponse<OrgRoleAdminVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(1, pageResponse.getItems().size());
    }
}