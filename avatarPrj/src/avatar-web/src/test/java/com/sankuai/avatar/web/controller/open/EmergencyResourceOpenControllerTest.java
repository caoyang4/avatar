package com.sankuai.avatar.web.controller.open;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.emergency.EmergencyOfflineVO;
import com.sankuai.avatar.web.vo.emergency.EmergencyOnlineVO;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author caoyang
 * @create 2023-03-02 10:21
 */
public class EmergencyResourceOpenControllerTest extends TestBase {

    private final String baseUrl = "/open/api/v2/avatar/emergency_resource";

    @Test
    public void getPageEmergencyOnline() throws Exception{
        String url = baseUrl + "/online?startTime=2022-01-19 12:00:00&endTime=2022-08-16 23:30:17";
        PageResponse<EmergencyOnlineVO> pageResponse = getMock(url, PageResponse.class);
        assert pageResponse != null;
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertTrue(pageResponse.getItems().size() > 0);
    }

    @Test
    public void getPageEmergencyOffline() throws Exception{
        String url = baseUrl + "/offline?startTime=2022-08-17 10:00:00&endTime=2022-08-26 18:30:17";
        PageResponse<EmergencyOfflineVO> pageResponse = getMock(url, PageResponse.class);
        assert pageResponse != null;
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertTrue(pageResponse.getItems().size() > 0);
    }

}
