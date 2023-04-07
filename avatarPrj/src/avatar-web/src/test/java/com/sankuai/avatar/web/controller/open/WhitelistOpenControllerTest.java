package com.sankuai.avatar.web.controller.open;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.whitelist.OwtSetWhitelistVO;
import com.sankuai.avatar.web.vo.whitelist.ServiceWhitelistVO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-15 19:58
 */
public class WhitelistOpenControllerTest extends TestBase {

    @Test
    public void reportOwtCapacityList() throws Exception {
        String url = "/open/api/v2/avatar/whitelist/capacity/owt";
        OwtSetWhitelistVO owtSetWhitelistVO = new OwtSetWhitelistVO();
        owtSetWhitelistVO.setOwt("test-owt");
        owtSetWhitelistVO.setSetName("test-set");
        owtSetWhitelistVO.setApp("capacity");
        owtSetWhitelistVO.setReason("无可奉告");
        owtSetWhitelistVO.setApplyBy("test");
        owtSetWhitelistVO.setStartTime(new Date());
        owtSetWhitelistVO.setEndTime(DateUtils.getAfterDaysFromNow(30));
        List<OwtSetWhitelistVO> voList = Collections.singletonList(owtSetWhitelistVO);
        Integer report = postMock(url, voList, Integer.class);
        Assert.assertEquals(1, report.intValue());
    }

    @Test
    public void reportAppkeyCapacityList() throws Exception {
        String url = "/open/api/v2/avatar/whitelist/capacity/appkey";
        ServiceWhitelistVO serviceWhitelistVO = new ServiceWhitelistVO();
        serviceWhitelistVO.setApp("capacity");
        serviceWhitelistVO.setReason("无可奉告");
        serviceWhitelistVO.setInputUser("unitTest");
        serviceWhitelistVO.setAppkey("test-appkey");
        serviceWhitelistVO.setApplication("unit-test-application");
        serviceWhitelistVO.setOrgIds("100046,150042,1573,150044,1021866");
        serviceWhitelistVO.setSetName("test-set");
        serviceWhitelistVO.setAddTime(new Date());
        serviceWhitelistVO.setEndTime(DateUtils.getAfterDaysFromNow(30));
        List<ServiceWhitelistVO> voList = Collections.singletonList(serviceWhitelistVO);
        Integer report = postMock(url, voList, Integer.class);
        Assert.assertEquals(1, report.intValue());
    }

    @Test
    public void getPageServiceWhitelist() throws Exception {
        String url = "/open/api/v2/avatar/whitelist?pageSize=1";
        PageResponse<ServiceWhitelistVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(1, pageResponse.getPageSize());
        Assert.assertEquals(1, pageResponse.getItems().size());
    }

    @Test
    public void getPageOwtSetWhitelist() throws Exception {
        String url = "/open/api/v2/avatar/whitelist/owt?pageSize=1";
        PageResponse<OwtSetWhitelistVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(1, pageResponse.getPageSize());
        Assert.assertEquals(1, pageResponse.getItems().size());
    }
}