package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.activity.ActivityResourceVO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2023-03-09 14:53
 */
public class ActivityResourceControllerTest extends TestBase {

    private final static String BASE = "/api/v2/avatar/activity_resource";

    @Test
    public void getPageActivityResource() throws Exception {
        PageResponse<ActivityResourceVO> pageResponse = getMock(BASE, PageResponse.class);
        Assert.assertEquals(1, pageResponse.getPage());
        Assert.assertTrue(CollectionUtils.isNotEmpty(pageResponse.getItems()));
    }

    @Test
    public void getSingleActivityResource() throws Exception {
        String url = BASE + "/62";
        ActivityResourceVO resourceVO = getMock(url, ActivityResourceVO.class);
        Assert.assertEquals(62, resourceVO.getId().intValue());
    }

    @Test
    public void saveActivityResource() throws Exception {
        String url = BASE + "/62";
        ActivityResourceVO resourceVO = getMock(url, ActivityResourceVO.class);
        ActivityResourceVO vo = JsonUtil.json2Bean(JsonUtil.bean2Json(resourceVO), ActivityResourceVO.class);
        vo.setId(null);
        vo.setCreateTime(new Date());
        vo.setEndTime(new Date());
        Integer save = postMock(BASE, vo, Integer.class);
        Assert.assertEquals(1, save.intValue());
    }

    @Test
    public void deleteActivityResourceByPk() throws Exception {
        String url = BASE + "/1";
        Integer delete = deleteMock(url, new Object(), Integer.class);
        Assert.assertEquals(0, delete.intValue());
    }

    @Test
    public void updateActivityResourceByPk() throws Exception {
        String url = BASE + "/57";
        ActivityResourceVO resourceVO = getMock(url, ActivityResourceVO.class);
        resourceVO.setDescription("鸡年大吉");
        Integer update = putMock(url, resourceVO, Integer.class);
        Assert.assertEquals(1, update.intValue());
    }

    @Test
    public void checkUserHulkSre() throws Exception {
        String url = BASE + "/permission?user=caoyang42";
        Map<String, Boolean> map = getMock(url, Map.class);
        Boolean isHulkSre = map.getOrDefault("isHulkSre", false);
        Assert.assertTrue(isHulkSre);
    }

    @Test
    public void getActivityResourceOrg() throws Exception {
        String url = BASE + "/org";
        List<String> list = getMock(url, List.class);
        Assert.assertTrue(CollectionUtils.isNotEmpty(list));
    }

    @Test
    public void updateStatusActivityResource() throws Exception {
        String url = BASE + "/62";
        ActivityResourceVO resourceVO = getMock(url, ActivityResourceVO.class);
        url = BASE + "/status";
        Integer integer = putMock(url, resourceVO, Integer.class);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test
    public void updateStartTimeActivityResource() throws Exception {
        String url = BASE + "/56";
        ActivityResourceVO resourceVO = getMock(url, ActivityResourceVO.class);
        url = BASE + "/updateTime";
        Integer integer = putMock(url, resourceVO, Integer.class);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test
    public void getActivityResourceSum() throws Exception {
        String url = BASE + "/summary";
        Object result = getMock(url, Object.class);
        Assert.assertNotNull(result);
    }
}