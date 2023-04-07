package com.sankuai.avatar.capacity.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author caoyang
 * @create 2022-12-27 15:34
 */
public class DayuUtilsTest {

    @Test
    public void getBackupCell() {
       String  cell = "waimai-east";
        String backupCell = DayuUtils.getBackupCell(cell);
        Assert.assertNotNull(backupCell);
    }

    @Test
    public void getSetIdc() {
        String  cell = "waimai-east";
        String idc = DayuUtils.getSetIdc(cell);
        Assert.assertNotNull(idc);
    }

    @Test
    public void notExistLogicSite() {
        String cell = "banma-exp";
        Boolean exist = DayuUtils.isExistLogicSite(cell);
        Assert.assertFalse(exist);
    }

    @Test
    public void existLogicSite() {
        String cell = "waimai-east";
        Boolean exist = DayuUtils.isExistLogicSite(cell);
        Assert.assertTrue(exist);
    }
}