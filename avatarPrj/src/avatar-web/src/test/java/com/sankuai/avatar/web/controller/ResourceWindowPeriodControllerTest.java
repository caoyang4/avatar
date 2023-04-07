package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.activity.WindowPeriodHitVO;
import com.sankuai.avatar.web.vo.activity.WindowPeriodResourceVO;
import com.sankuai.avatar.web.vo.activity.WindowPermissionVO;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * @author caoyang
 * @create 2023-03-16 11:43
 */
public class ResourceWindowPeriodControllerTest extends TestBase {

    private final String baseUrl = "/api/v2/avatar/window_period";

    @Test
    public void getPageResourceWindowPeriod() throws Exception {
        String url = baseUrl + "?pageSize=1&createUser=caoyang42";
        PageResponse<WindowPeriodResourceVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertEquals(1, pageResponse.getItems().size());
    }

    @Test
    public void saveResourceWindowPeriod() throws Exception {
        WindowPeriodResourceVO resourceVO = new WindowPeriodResourceVO();
        resourceVO.setName("unit-test");
        resourceVO.setDescription("unit-test");
        resourceVO.setStartTime(DateUtils.localDateToDate(LocalDate.now().plusDays(30)));
        resourceVO.setEndTime(DateUtils.localDateToDate(LocalDate.now().plusDays(365)));
        resourceVO.setExpectedDeliveryTime(DateUtils.localDateToDate(LocalDate.now().plusDays(1)));
        resourceVO.setCreateUser("unit");
        Integer save = postMock(baseUrl, resourceVO, Integer.class);
        Assert.assertEquals(1,save.intValue());
    }

    @Test
    public void getResourceWindowPeriodByPk() throws Exception {
        String url = baseUrl + "/33";
        WindowPeriodResourceVO resourceVO = getMock(url, WindowPeriodResourceVO.class);
        Assert.assertEquals(33,resourceVO.getId().intValue());
    }

    @Test
    public void deleteResourceWindowPeriodByPk() throws Exception {
        String url = baseUrl + "/1";
        Integer delete = deleteMock(url, new Object(), Integer.class);
        Assert.assertEquals(0,delete.intValue());
    }

    @Test
    public void updateResourceWindowPeriodByPk() throws Exception {
        String url = baseUrl + "/recent";
        WindowPeriodResourceVO resourceVO = getMock(url, WindowPeriodResourceVO.class);
        Assert.assertNotNull(resourceVO);
    }

    @Test
    public void hitResourceWindowPeriod() throws Exception {
        String url = baseUrl + "/hit";
        WindowPeriodHitVO hitVO = getMock(url, WindowPeriodHitVO.class);
        Assert.assertNotNull(hitVO);
    }

    @Test
    public void permission() throws Exception {
        String url = baseUrl + "/permission?mis=caoyang42";
        WindowPermissionVO permissionVO = getMock(url, WindowPermissionVO.class);
        Assert.assertTrue(permissionVO.getPermission());
    }
}