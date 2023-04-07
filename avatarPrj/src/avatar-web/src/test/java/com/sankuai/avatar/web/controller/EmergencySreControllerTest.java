package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.emergency.EmergencySreVO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-02-02 10:47
 */
public class EmergencySreControllerTest extends TestBase {

    private final String baseUrl = "/api/v2/avatar/emergencySre";

    @Test
    public void getPageEmergencySre() throws Exception {
        String url = baseUrl + "?appkey=com.sankuai.avatar.develop";
        PageResponse<EmergencySreVO> pageResponse = getMock(url, PageResponse.class);
        assert pageResponse != null;
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertTrue(pageResponse.getItems().size() > 0);
    }

    @Test
    public void getEmergencySreByPk() throws Exception {
        String url = baseUrl + "/2";
        EmergencySreVO emergencySreVO = getMock(url, EmergencySreVO.class);
        Assert.assertEquals("com.sankuai.avatar.develop", emergencySreVO.getAppkey());
    }

    @Test
    public void saveEmergencySreAdmin() throws Exception {
        EmergencySreVO emergencySreVO = new EmergencySreVO();
        emergencySreVO.setSourceId(10086);
        emergencySreVO.setTime(1);
        emergencySreVO.setState("FINISH");
        emergencySreVO.setAppkey("appkey");
        emergencySreVO.setOpAdmin("avatar");
        emergencySreVO.setAttachAdmin("mcm");
        emergencySreVO.setCreateUser("长者");
        emergencySreVO.setCreateTime(new Date());
        Boolean save = postMock(baseUrl, emergencySreVO, Boolean.class);
        Assert.assertTrue(save);
    }

    @Test
    public void updateEmergencySreAdminByPk() throws Exception {
        String url = baseUrl + "/2";
        EmergencySreVO emergencySreVO = new EmergencySreVO();
        emergencySreVO.setSourceId(1234);
        emergencySreVO.setTime(1);
        emergencySreVO.setState("FINISH");
        emergencySreVO.setAppkey("com.sankuai.avatar.develop");
        emergencySreVO.setOpAdmin("qinwei06");
        emergencySreVO.setAttachAdmin("qinwei06");
        emergencySreVO.setCreateUser("qinwei06");
        Boolean update = putMock(url, emergencySreVO, Boolean.class);
        Assert.assertTrue(update);
    }

    @Test
    public void deleteEmergencySreAdminByPk() throws Exception {
        String url = baseUrl + "/1";
        Boolean delete = deleteMock(url, new Object(), Boolean.class);
        Assert.assertTrue(delete);
    }
}