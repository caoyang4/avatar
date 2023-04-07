package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.user.*;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2022-11-01 19:28
 */
public class UserControllerTest extends TestBase {

    private final String baseUrl = "/api/v2/avatar/user";

    @Test
    public void queryUser() throws Exception {
        String url = baseUrl + "?pageSize=1&loginName=caoyang42";
        PageResponse<UserVO> pageResponse = getMock(url, PageResponse.class);
        assert pageResponse != null;
        Assert.assertEquals(1, pageResponse.getPageSize());
        Assert.assertEquals(1, pageResponse.getItems().size());
    }

    @Test
    public void queryUserNoParam() throws Exception {
        String url = baseUrl;
        PageResponse<UserVO> pageResponse = getMock(url, PageResponse.class);
        assert pageResponse != null;
        Assert.assertEquals(10, pageResponse.getPageSize());
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void queryOrgUser() throws Exception {
        String url = baseUrl + "?isGroup=true";
        PageResponse<UserVO> pageResponse = getMock(url, PageResponse.class);
        assert pageResponse != null;
        Assert.assertEquals(20, pageResponse.getPageSize());
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void querySingleUser() throws Exception {
        String mis = "caoyang42";
        String url = baseUrl + "/" + mis;
        UserVO userVO = getMock(url, UserVO.class);
        Assert.assertNotNull(userVO);
        Assert.assertEquals(mis, userVO.getLoginName());
    }

    @Test
    public void getOrgUserVO() throws Exception {
        String mises = "caoyang42,qinwei05";
        String url = baseUrl + "/info?mis=" + mises;
        List<OrgUserVO> orgUserVOList = Arrays.asList(getMock(url, OrgUserVO[].class));
        Assert.assertEquals(2, orgUserVOList.size());
        Assert.assertTrue(mises.contains(orgUserVOList.get(0).getMis()));
    }

    @Test
    public void addTopAppkey() throws Exception {
        String url = baseUrl + "/topAppkey";
        UserTopAppkeyVO topAppkeyVO = new UserTopAppkeyVO();
        topAppkeyVO.setAppkey("com.sankuai.avatar.develop");
        Boolean save = postMock(url, topAppkeyVO, Boolean.class);
        Assert.assertTrue(save);
    }

    @Test
    public void deleteTopAppkey() throws Exception {
        String url = baseUrl + "/topAppkey";
        UserTopAppkeyVO topAppkeyVO = new UserTopAppkeyVO();
        topAppkeyVO.setAppkey("com.sankuai.avatar.develop");
        Boolean save = deleteMock(url, topAppkeyVO, Boolean.class);
        Assert.assertTrue(save);
    }

    @Test
    public void getUserStatus() throws Exception {
        String url = baseUrl + "/jobStatus?users=qinwei05,caoyang42";
        Map<String, String> map = getMock(url, Map.class);
        Assert.assertNotNull(map);
    }

    @Test
    public void isUserSre() throws Exception {
        String url = baseUrl + "/sre";
        UserPermissionVO map = getMock(url, UserPermissionVO.class);
        Assert.assertNotNull(map);
    }

    @Test
    public void login() throws Exception {
        String url = baseUrl + "/my";
        UserLoginVO loginVO = getMock(url, UserLoginVO.class);
        Assert.assertNotNull(loginVO);
    }
}