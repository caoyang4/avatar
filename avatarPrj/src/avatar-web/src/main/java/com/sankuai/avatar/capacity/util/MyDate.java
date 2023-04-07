package com.sankuai.avatar.capacity.util;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by shujian on 2020/2/19.
 */
public class MyDate {
    /**
     * 获取当前时间,
     *
     * @return 格式: yyyy-MM-dd
     */
    public static String getStringNowDay() {
        Date curTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(curTime);
    }

    /**
     * 获取昨天的日期
     *
     * @return 时间格式: yyyy-MM-dd
     */
    public static String getStringOffsetDay() {
        Integer offset = 0;
        return getStringOffsetDay(offset);
    }

    public static String getStringOffsetDay(Integer offset) {
        String format = "yyyy-MM-dd";
        return getStringOffsetDay(offset, format);
    }

    public static String getStringOffsetDay(Integer offset, String format) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime offsetTime = now.plusDays(offset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return offsetTime.format(formatter);
    }
}
