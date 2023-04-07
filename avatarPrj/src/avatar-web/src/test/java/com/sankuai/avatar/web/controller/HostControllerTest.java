package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.host.HostCountDTO;
import com.sankuai.avatar.web.vo.host.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author qinwei05
 */
public class HostControllerTest extends TestBase {

    @Test
    public void testGetAppkeyHost() throws Exception {
        String url = "/api/v2/avatar/host?appkey=com.sankuai.avatar.cscscscs&env=test";
        PageResponse<HostAttributesVO> response = getMock(url, PageResponse.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetHostOfflineReason() throws Exception {
        String url = "/api/v2/avatar/host/offlineReason";
        List<String> response = getMock(url, List.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetTitleConfig() throws Exception {
        String url = "/api/v2/avatar/host/titleConfig";
        List<HostTitleConfigVO> response = getMock(url, List.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetHostFilterConfig() throws Exception {
        String url = "/api/v2/avatar/host/filterConfig";
        List<HostFilterConditionVO> response = getMock(url, List.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetHostConfig() throws Exception {
        String url = "/api/v2/avatar/host/hostConfig";
        HostConfigVO response = getMock(url, HostConfigVO.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetAppkeyHostSumAttribute() throws Exception {
        String url = "/api/v2/avatar/host/title?appkey=com.sankuai.avatar.cscscscs&env=test";
        HostSumAttributeVO response = getMock(url, HostSumAttributeVO.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetAppkeyHostCount() throws Exception {
        String url = "/api/v2/avatar/host/count?appkey=com.sankuai.avatar.cscscscs&env=test";
        HostCountDTO response = getMock(url, HostCountDTO.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetAppkeyHostCell() throws Exception {
        String url = "/api/v2/avatar/host/cell?appkey=com.sankuai.avatar.cscscscs&env=test";
        List<HostCellVO> response = getMock(url, List.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetAppkeyGrouptags() throws Exception {
        String url = "/api/v2/avatar/host/grouptags?appkey=com.sankuai.avatar.cscscscs&env=test";
        List<GroupTagVO> response = getMock(url, List.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetAppkeyIdcDistributed() throws Exception {
        String url = "/api/v2/avatar/host/idc?appkey=com.sankuai.avatar.cscscscs&env=test";
        HostIdcDistributedVO response = getMock(url, HostIdcDistributedVO.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testAppkeyFirstApplyHulk() throws Exception {
        String url = "/api/v2/avatar/host/firstHulk?appkey=com.sankuai.avatar.cscscscs&env=test";
        Boolean response = getMock(url, Boolean.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetExternalHost() throws Exception {
        String url = "/api/v2/avatar/host/external/100.101.131.49";
        ExternalHostVO response = getMock(url, ExternalHostVO.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetExternalParentHost() throws Exception {
        String url = "/api/v2/avatar/host/external/parent/100.101.131.49";
        List<HostVO> response = getMock(url, List.class);
        Assert.assertNotNull(response);
    }

    @Test
    public void testGetExternalIdc() throws Exception {
        String url = "/api/v2/avatar/host/external/idc";
        List<IdcVO> response = getMock(url, List.class);
        Assert.assertNotNull(response);
    }
}