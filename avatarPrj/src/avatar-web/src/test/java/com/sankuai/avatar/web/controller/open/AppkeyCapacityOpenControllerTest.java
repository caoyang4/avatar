package com.sankuai.avatar.web.controller.open;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.vo.capacity.AppKeyCapacityDetailVO;
import com.sankuai.avatar.web.vo.capacity.AppkeyCapacityVO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-07 10:36
 */
public class AppkeyCapacityOpenControllerTest extends TestBase {

    @Test
    public void getPageAppkeyCapacity() throws Exception {
        String appkey = "com.sankuai.avatar.web";
        String url = "/open/api/v2/avatar/capacity?pageSize=1&appkey=" + appkey;
        PageResponse<AppkeyCapacityVO> pageResponse = getMock(url, PageResponse.class);
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(1, pageResponse.getItems().size());
    }

    @Test
    public void getAppkeyCapacity() throws Exception {
        String appkey = "com.sankuai.deliveryturing.nextbox.dispatch";
        String url = "/open/api/v2/avatar/capacity/" + appkey;
        List<AppkeyCapacityVO> voList = Arrays.asList(getMock(url, AppkeyCapacityVO[].class));
        Assert.assertNotNull(voList);
        for (AppkeyCapacityVO appkeyCapacityVO : voList) {
            Assert.assertEquals(appkey, appkeyCapacityVO.getAppkey());
        }
    }

    @Test
    public void getAppKeyCapacityDetail() throws Exception {
        String appkey = "com.sankuai.deliveryturing.nextbox.dispatch";
        String url = "/open/api/v2/avatar/capacity/" + appkey + "/detail";
        AppKeyCapacityDetailVO detailVO = getMock(url, AppKeyCapacityDetailVO.class);
        Assert.assertNotNull(detailVO);
        Assert.assertTrue(detailVO.getCapacityLevels().size() > 0);
    }
}