package com.sankuai.avatar.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author caoyang
 * @create 2022-10-12 20:58
 */
public class DateUtilsTest{

    @Test
    public void testLocalDateToDate() {
        LocalDate localDate = DateUtils.dateToLocalDate(new Date());
        Assert.assertNotNull(localDate);
    }

    @Test
    public void testDateToLocalDate() {
        LocalDate localDate = LocalDate.now().plusDays(-1);
        Date date = DateUtils.localDateToDate(localDate);
        Assert.assertNotNull(date);
    }

    @Test
    public void testGetAfterDaysFromNow(){
        Date after = DateUtils.getAfterDaysFromNow(1);
        Assert.assertTrue(new Date().before(after));
        Date before = DateUtils.getAfterDaysFromNow(-1);
        Assert.assertTrue(new Date().after(before));
    }

    @Test
    public void isTimeGapByDays(){
        String timeStr = DateUtils.dateToStr(new Date());
        boolean gap = DateUtils.isOverTimeGapByDays(timeStr, 30);
        Assert.assertFalse(gap);

    }

    @Test
    public void strToDate() {
        String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        Date date = DateUtils.StrToDate("2020-02-14T18:28:15.709Z", format);
        Assert.assertNotNull(date);
    }
}