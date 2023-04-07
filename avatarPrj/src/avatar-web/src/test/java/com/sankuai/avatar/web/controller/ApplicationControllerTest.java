package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.application.ApplicationDetailVO;
import com.sankuai.avatar.web.vo.application.ApplicationQueryVO;
import com.sankuai.avatar.web.vo.application.ApplicationVO;

import com.sankuai.avatar.web.vo.application.ApplicationWhiteVO;
import org.junit.Assert;
import org.junit.Test;

/**
 * ApplicationContorller的测试类
 */
public class ApplicationControllerTest extends TestBase {

    @Test
    public void testGetApplications() throws Exception {
        String url = "/api/v2/avatar/application?type=mine&page=1&pageSize=10";
        PageResponse<ApplicationVO> voPageResponse = getMock(url, PageResponse.class);
        assertTrue(voPageResponse.getItems().size() > 0);
    }

    @Test
    public void testGetApplication() throws Exception {
        String url = "/api/v2/avatar/application/Avatar";
        ApplicationDetailVO applicationDetailVO = getMock(url, ApplicationDetailVO.class);
        assertTrue(applicationDetailVO.getAppKeyCount() > 0);
    }

    @Test
    public void testGetDefaultApplication() throws Exception {
        String url = "/api/v2/avatar/application/applyAppkey";
        PageResponse<ApplicationQueryVO> pageResponse = getMock(url, PageResponse.class);
        assertTrue(pageResponse.getItems().size() > 0);

        String urlV2 = "/api/v2/avatar/application/applyAppkey?query=Avatar";
        PageResponse<ApplicationQueryVO> pageResponse2 = getMock(urlV2, PageResponse.class);
        assertEquals(1, pageResponse2.getItems().size());
    }

    @Test
    public void getApplicationWhiteVO() throws Exception{
        String url = "/api/v2/avatar/application/mtpt01/isWhite";
        ApplicationWhiteVO whiteVO = getMock(url, ApplicationWhiteVO.class);
        Assert.assertTrue(whiteVO.getIsWhite());
    }
}
