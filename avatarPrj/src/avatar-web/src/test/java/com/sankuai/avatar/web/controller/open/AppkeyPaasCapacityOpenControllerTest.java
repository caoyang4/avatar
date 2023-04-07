package com.sankuai.avatar.web.controller.open;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.capacity.constant.PaasCapacityType;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityReportVO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author caoyang
 * @create 2022-09-23 15:07
 */
public class AppkeyPaasCapacityOpenControllerTest extends TestBase {

    static AppkeyPaasCapacityReportVO reportVO = new AppkeyPaasCapacityReportVO();

    static {
        reportVO.setPaasName("Cellar");
        reportVO.setType(PaasCapacityType.TOPIC);
        reportVO.setTypeName("com.sankuai.tair.banma.qaset");
        reportVO.setPaasAppkey("com.sankuai.tair.banma.qaset");
        reportVO.setIsCore(false);
        reportVO.setCapacityLevel(5);
        reportVO.setStandardLevel(4);
        reportVO.setIsCapacityStandard(false);
        reportVO.setStandardTips("未达到容灾标准");
        AppkeyPaasCapacityReportVO.ClientVersion clientVersion = new AppkeyPaasCapacityReportVO.ClientVersion();
        clientVersion.setLanguage("Java");
        clientVersion.setVersion("3.10.10");
        clientVersion.setGroupId("com.taobao.tair");
        clientVersion.setArtifactId("tair3-client");
        reportVO.setStandardVersion(Arrays.asList(clientVersion));
        reportVO.setClientAppkey(Arrays.asList("com.meituan.banma.api01", "com.sankuai.banma.api.mq01"));
        reportVO.setClientSdkVersion(new ArrayList<>());
        reportVO.setClientConfig(new ArrayList<>());
        reportVO.setStandardConfig(new ArrayList<>());
        reportVO.setIsConfigStandard(true);
        reportVO.setOwner("zhangyi35,zhengkai14,liujia53,wanli07,bixiaowei,liyujie10,yangzeyi,jiangxin22,niujianya,panpan02");
        reportVO.setIsWhite(false);
        reportVO.setUpdateBy("com.sankuai.inf.kv.controlcenter");
    }

    @Test
    public void testReportPaasCapacityList() throws Exception {
        String url = "/open/api/v2/avatar/capacity/paas";
        Integer result = postMock(url, Collections.singletonList(reportVO), Integer.class);
        assertNotNull(result);
    }

    @Test
    public void testGetPaasCapacity() throws Exception {
        String paasName = "Cellar";
        String url = "/open/api/v2/avatar/capacity/paas?pageSize=1&paasName=" + paasName;
        PageResponse<AppkeyPaasCapacityReportVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(1, pageResponse.getItems().size());
    }
}