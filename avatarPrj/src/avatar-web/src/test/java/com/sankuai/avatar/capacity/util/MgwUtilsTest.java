package com.sankuai.avatar.capacity.util;

import com.sankuai.avatar.capacity.dto.MgwVsResponse;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author caoyang
 * @create 2022-09-19 11:39
 */
public class MgwUtilsTest extends TestCase {

    @Test
    public void testGetMgwVs() {
        String appkey = "com.sankuai.mtsdb.cloud.mgwmonitor";
        List<MgwVsResponse.MgwVs> mgwVs = MgwUtils.getMgwVs(appkey);
        assert mgwVs.size() > 0;
    }

    @Test
    public void testGetMgwRip() {
        String appkey = "com.sankuai.mtsdb.cloud.mgwmonitor";
        Set<String> mgwRip = MgwUtils.getMgwRip(appkey);
        assert mgwRip.size() > 0;
    }

}