package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.application.ApplicationRoleAdminVO;
import com.sankuai.avatar.web.vo.application.ApplicationRoleUserAdminVO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author caoyang
 * @create 2023-01-17 15:07
 */
public class ApplicationRoleAdminControllerTest extends TestBase {

    @Test
    public void getPageApplicationRoleAdmin() throws Exception {
        String url = "/api/v2/avatar/applicationRoleAdmin";
        PageResponse<ApplicationRoleAdminVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void getByApplicationId() throws Exception {
        String url = "/api/v2/avatar/applicationRoleAdmin/applicationId/6309";
        ApplicationRoleUserAdminVO adminVO = getMock(url, ApplicationRoleUserAdminVO.class);
        Assert.assertEquals(6309, adminVO.getApplicationId().intValue());
    }

    @Test
    public void getByApplicationName() throws Exception {
        String url = "/api/v2/avatar/applicationRoleAdmin/applicationName/Domain";
        ApplicationRoleUserAdminVO adminVO = getMock(url, ApplicationRoleUserAdminVO.class);
        Assert.assertEquals("Domain", adminVO.getApplicationName());
    }

    @Test
    public void saveApplicationRoleAdmin() throws Exception {
        ApplicationRoleAdminVO adminVO = new ApplicationRoleAdminVO();
        adminVO.setApplicationId(1);
        adminVO.setApplicationName("UnitTest");
        adminVO.setApplicationCnName("单测");
        adminVO.setEpAdmin("avatar");
        adminVO.setOpAdmin("avatar");
        adminVO.setCreateUser("avatar");
        adminVO.setUpdateUser("avatar");
        System.out.println(JsonUtil.bean2Json(adminVO));
        String url = "/api/v2/avatar/applicationRoleAdmin";
        Boolean save = postMock(url, adminVO, Boolean.class);
        Assert.assertTrue(save);
    }

    @Test
    public void deleteApplicationRoleAdminByPk() throws Exception {
        String url = "/api/v2/avatar/applicationRoleAdmin/1";
        Boolean delete = deleteMock(url, new Object(), Boolean.class);
        Assert.assertTrue(delete);
    }
}