package com.sankuai.avatar.capacity.util;

import com.sankuai.avatar.TestBase;
import com.sankuai.avatar.capacity.dto.RegionZoneResponse;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author caoyang
 * @create 2022-12-26 15:56
 */
public class TerraUtilsTest {

    @Test
    public void getRegionZone() {
        List<RegionZoneResponse> regionZone = TerraUtils.getRegionZone();
        Assert.assertTrue(CollectionUtils.isNotEmpty(regionZone));
    }
}