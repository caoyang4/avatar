package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.whitelist.CancelWhiteParamVO;
import com.sankuai.avatar.web.vo.whitelist.OwtSetWhitelistVO;
import com.sankuai.avatar.web.vo.whitelist.ServiceWhitelistVO;
import com.sankuai.avatar.web.vo.whitelist.WhitelistAppVO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author caoyang
 * @create 2022-11-15 19:58
 */
public class WhitelistControllerTest extends TestBase {

    @Test
    public void getPageServiceWhitelist() throws Exception {
        String url = "/api/v2/avatar/whitelist?pageSize=1";
        PageResponse<ServiceWhitelistVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(1, pageResponse.getPageSize());
        Assert.assertEquals(1, pageResponse.getItems().size());
    }

    @Test
    public void getAppkeyValidWhitelist() throws Exception {
        String appkey = "ts-hobbit-settle-mq";
        String url = "/api/v2/avatar/whitelist/" + appkey;
        List<ServiceWhitelistVO> whitelistVOList = Arrays.asList(getMock(url, ServiceWhitelistVO[].class));
        Assert.assertTrue(CollectionUtils.isNotEmpty(whitelistVOList));
        Assert.assertEquals(appkey, whitelistVOList.get(0).getAppkey());
    }

    @Test
    public void getAllWhitelistType() throws Exception {
        String url = "/api/v2/avatar/whitelist/apps";
        List<WhitelistAppVO> voList = Arrays.asList(getMock(url, WhitelistAppVO[].class));
        Assert.assertTrue(CollectionUtils.isNotEmpty(voList));
    }

    @Test
    public void saveServiceWhitelist() throws Exception {
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
        String url = "/api/v2/avatar/whitelist";
        Integer result = postMock(url, serviceWhitelistVO, Integer.class);
        Assert.assertEquals(1, result.intValue());
    }

    @Test
    public void saveOwtWhitelist() throws Exception {
        OwtSetWhitelistVO owtSetWhitelistVO = new OwtSetWhitelistVO();
        owtSetWhitelistVO.setOwt("test-owt");
        owtSetWhitelistVO.setSetName("test-set");
        owtSetWhitelistVO.setApp("capacity");
        owtSetWhitelistVO.setReason("无可奉告");
        owtSetWhitelistVO.setApplyBy("test");
        owtSetWhitelistVO.setStartTime(new Date());
        owtSetWhitelistVO.setEndTime(DateUtils.getAfterDaysFromNow(30));
        String url = "/api/v2/avatar/whitelist/owt";
        Integer result = postMock(url, owtSetWhitelistVO, Integer.class);
        Assert.assertEquals(1, result.intValue());
    }

    @Test
    public void deleteOwtWhitelistByPk() throws Exception {
        String url = "/api/v2/avatar/whitelist/owt/2900";
        Integer result = deleteMock(url, new HashMap<>(), Integer.class);
        Assert.assertEquals(1, result.intValue());
    }

    @Test
    public void deleteServiceWhitelistByPk() throws Exception {
        String url = "/api/v2/avatar/whitelist/11353";
        Integer result = deleteMock(url, new HashMap<>(), Integer.class);
        Assert.assertEquals(1, result.intValue());
    }


    @Test
    public void getApplicationOrgWhitelist() throws Exception {
        String application = "Avatar";
        String url = "/api/v2/avatar/whitelist/" + application + "/appkey";
        PageResponse<ServiceWhitelistVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
        List<ServiceWhitelistVO> voList = Arrays.asList(Objects.requireNonNull(
                JsonUtil.json2Bean(JsonUtil.bean2Json(pageResponse.getItems()),
                        ServiceWhitelistVO[].class)));
        for (ServiceWhitelistVO vo : voList) {
            Assert.assertEquals(application.toLowerCase(), vo.getApplication());
        }
    }

    @Test
    public void getOrgWhitelist() throws Exception {
        String url = "/api/v2/avatar/whitelist/100046,150042,1573,150044/appkey";
        PageResponse<ServiceWhitelistVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void cancelWhiteCaseNoWhite() throws Exception {
        String url = "/api/v2/avatar/whitelist/cancel";
        CancelWhiteParamVO paramVO = new CancelWhiteParamVO();
        paramVO.setUser("caoyang42");
        paramVO.setAppkey("com.sankuai.avatar.cscscscs1");
        paramVO.setApp("capacity");
        Boolean cancel = postMock(url, paramVO, Boolean.class);
        Assert.assertFalse(cancel);
    }

    @Test
    public void cancelWhite() throws Exception {
        String url = "/api/v2/avatar/whitelist/cancel";
        CancelWhiteParamVO paramVO = new CancelWhiteParamVO();
        paramVO.setUser("caoyang42");
        paramVO.setAppkey("com.sankuai.avatar.cscscscs");
        paramVO.setApp("capacity");
        paramVO.setCellNames(Collections.singletonList("default"));
        Boolean cancel = postMock(url, paramVO, Boolean.class);
        Assert.assertNotNull(cancel);
    }

    @Test
    public void getPageOwtSetWhitelist() throws Exception {
        String url = "/api/v2/avatar/whitelist/owt?pageSize=1";
        PageResponse<OwtSetWhitelistVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(1, pageResponse.getPageSize());
        Assert.assertEquals(1, pageResponse.getItems().size());
    }

}