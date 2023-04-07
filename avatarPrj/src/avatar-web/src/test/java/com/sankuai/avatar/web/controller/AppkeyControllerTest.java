package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.appkey.*;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2023-01-16 14:46
 */
public class AppkeyControllerTest extends TestBase {

    @Test
    public void getAppkeyDetailInfo() throws Exception {
        String appkey = "com.sankuai.avatar.web";
        String url = "/api/v2/avatar/appkey/" + appkey + "/detail";
        AppkeyDetailVO detailVO = getMock(url, AppkeyDetailVO.class);
        Assert.assertEquals("dianping.tbd.change.avatar-web", detailVO.getKey());
    }

    @Test
    public void getHomeUserRelatedAppkey() throws Exception {
        String url = "/api/v2/avatar/appkey/home/mine";
        PageResponse<AppkeyHomeVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void getAppkeyFlow() throws Exception {
        String url = "/api/v2/avatar/appkey/flow?appkeys=com.sankuai.avatar.web";
        PageResponse<AppkeyFlowVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
    }

    @Test
    public void getUserRelatedAppkey() throws Exception {
        String url = "/api/v2/avatar/appkey/mine?user=caoyang42&query=avatar";
        PageResponse<AppkeyVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void getFavorPage() throws Exception {
        String url = "/api/v2/avatar/appkey/favor?user=caoyang42&query=avatar";
        PageResponse<AppkeyVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void getBySrv() throws Exception {
        String srv = "dianping.tbd.change.avatar-web";
        String url = "/api/v2/avatar/appkey/srv/" + srv;
        AppkeyCollectVO appkeyCollectVO = getMock(url, AppkeyCollectVO.class);
        Assert.assertEquals("com.sankuai.avatar.web", appkeyCollectVO.getAppkey());
    }

    @Test
    public void queryPage() throws Exception {
        String url = "/api/v2/avatar/appkey?appkey=com.sankuai.avatar.web";
        PageResponse<AppkeyCollectVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void searchPage() throws Exception {
        String url = "/api/v2/avatar/appkey/search?user=caoyang42";
        PageResponse<AppkeyCollectVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void addFavorAppkey() throws Exception {
        String url = "/api/v2/avatar/appkey/com.sankuai.avatar.web/favor?mis=caoyang42";
        Boolean favor = postMock(url, new Object(), Boolean.class);
        Assert.assertTrue(favor);
    }

    @Test
    public void cancelFavorAppkey() throws Exception {
        String url = "/api/v2/avatar/appkey/com.sankuai.avatar.cytestwhite/favor?mis=caoyang42";
        Boolean cancel = deleteMock(url, new Object(), Boolean.class);
        Assert.assertTrue(cancel);
    }

    @Test
    public void getAppkeys() throws Exception {
        String url = "/api/v2/avatar/appkey/query?query=com.sankuai.avatar.web";
        List<String> appkeys = Arrays.asList(getMock(url, String[].class));
        Assert.assertTrue(CollectionUtils.isNotEmpty(appkeys));
    }

    @Test
    public void getAppkeyUtilInfo() throws Exception {
        String url = "/api/v2/avatar/appkey/com.sankuai.avatar.web/utilization";
        AppkeyResourceUtilVO utilVO = getMock(url, AppkeyResourceUtilVO.class);
        Assert.assertNotNull(utilVO);
    }

    @Test
    public void getAppkeyContainerType() throws Exception {
        String url = "/api/v2/avatar/appkey/containerType";
        ContainerTypeVO result = getMock(url, ContainerTypeVO.class);
        Assert.assertTrue(result.getItems().size() > 0);
    }

    @Test
    public void getAppkeyStateReason() throws Exception {
        String url = "/api/v2/avatar/appkey/stateReason";
        List<String> result = getMock(url, List.class);
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void getIsoltAppkey() throws Exception {
        String url = "/api/v2/avatar/appkey/isolt";
        PageResponse<IsoltAppkeyVO> isoltAppkeyVOPageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(isoltAppkeyVOPageResponse);
    }

    @Test
    public void generateIsoltAppkey() throws Exception {
        String url = "/api/v2/avatar/appkey/isolt/generate?originAppkey=com.sankuai.avatar.web";
        IsoltAppkeyGenerateDisplayVO isoltAppkey = getMock(url, IsoltAppkeyGenerateDisplayVO.class);
        Assert.assertEquals("com.sankuai.avatar.webisolt", isoltAppkey.getIsoltAppkeyName());
    }

    @Test
    public void getElasticTips() throws Exception {
        String url = "/api/v2/avatar/appkey/elastic/tips";
        ElasticTipVO elasticTipVO = getMock(url, ElasticTipVO.class);
        Assert.assertNotNull(elasticTipVO);
    }

    @Test
    public void getElasticGrayScale() throws Exception {
        String url = "/api/v2/avatar/appkey/elastic/gray";
        Map<String, Boolean> response = getMock(url, Map.class);
        Assert.assertTrue(response.get("gray"));
    }

    @Test
    public void getAppkeyFavor() throws Exception {
        String url = "/api/v2/avatar/appkey/com.sankuai.avatar.develop/favor";
        Boolean response = getMock(url, Boolean.class);
        Assert.assertNotNull(response);

        String url2 = "/api/v2/avatar/appkey/com.sankuai.avatar.develop/favor?mis=qinwei05";
        Boolean response2 = getMock(url2, Boolean.class);
        Assert.assertNotNull(response2);
    }

    @Test
    public void getAppkeyOctoRouteStrategy() throws Exception {
        String url = "/api/v2/avatar/appkey/com.sankuai.avatar.develop/routeStrategy";
        AppkeyRouteStrategyVO response = getMock(url, AppkeyRouteStrategyVO.class);
        Assert.assertNotNull(response.getContext());
    }

    @Test
    public void testGetAppkeyBillingUnit() throws Exception {
        String url = "/api/v2/avatar/appkey/com.sankuai.avatar.develop/billingUnit";
        AppkeyBillingUnitVO response = getMock(url, AppkeyBillingUnitVO.class);
        Assert.assertNotNull(response.getBillingUnitName());
    }
}