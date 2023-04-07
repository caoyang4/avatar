package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.web.vo.tree.AppkeyTreeOwtVO;
import com.sankuai.avatar.web.vo.tree.AppkeyTreePdlVO;
import com.sankuai.avatar.web.vo.tree.BgTreeVO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * AppkeyTreeController的测试类
 */
public class AppkeyTreeControllerTest extends TestBase {

    @Test
    public void getBgs() throws Exception {
        String url = "/api/v2/avatar/appkeyTree/user/bg";
        String[] appkeyTreeBgVOs = getMock(url, String[].class);
        assertTrue(appkeyTreeBgVOs.length > 0);
    }

    @Test
    public void getOwts() throws Exception {
        String url = "/api/v2/avatar/appkeyTree/owt";
        AppkeyTreeOwtVO[] appkeyTreeOwtVOS = getMock(url, AppkeyTreeOwtVO[].class);
        assertTrue(appkeyTreeOwtVOS.length > 0);
    }

    @Test
    public void getOwtByKey() throws Exception {
        String url = "/api/v2/avatar/appkeyTree/owt/meituan.sre";
        AppkeyTreeOwtVO owtVO = getMock(url, AppkeyTreeOwtVO.class);
        Assert.assertEquals("meituan.sre", owtVO.getKey());
    }


    @Test
    public void getPdlList() throws Exception {
        String url = "/api/v2/avatar/appkeyTree/owt/dianping.tbd/pdl";
        List<AppkeyTreePdlVO> appkeyTreePdlVOList = Arrays.asList(getMock(url, AppkeyTreePdlVO[].class));
        assertTrue(appkeyTreePdlVOList.size() > 0);
    }

    @Test
    public void getPdlByKey() throws Exception {
        String url = "/api/v2/avatar/appkeyTree/pdl/dianping.tbd.change";
        AppkeyTreePdlVO pdlDetailVO = getMock(url, AppkeyTreePdlVO.class);
        assertEquals("dianping.tbd.change", pdlDetailVO.getKey());
    }

    @Test
    public void getTree() throws Exception {
        String url = "/api/v2/avatar/appkeyTree/user/tree";
        List<BgTreeVO> tree = Arrays.asList(getMock(url, BgTreeVO[].class));
        assertTrue(CollectionUtils.isNotEmpty(tree));
    }
}