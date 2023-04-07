package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.web.vo.appkey.PlusReleaseVO;
import org.junit.Assert;
import org.junit.Test;

/**
 * PlusController的测试类
 */
public class PlusControllerTest extends TestBase {

    @Test
    public void testGetAppkeyPlusRelease() throws Exception {
        String url = "/api/v2/avatar/plus/com.sankuai.cf.component.server/releases";
        PlusReleaseVO plusReleaseVO = getMock(url, PlusReleaseVO.class);
        Assert.assertNotNull(plusReleaseVO);
    }
}
