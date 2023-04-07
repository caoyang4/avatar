package com.sankuai.avatar.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author zhaozhifan02
 */
@Slf4j
public class DateUtils {

    private DateUtils() {
    }

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期串转日期对象
     * @param str 日期串
     * @return Date
     */
    public static Date StrToDate(String str) {
        return StrToDate(str, DATE_FORMAT);
    }

    public static Date StrToDate(String str, String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            log.error(String.format("Invalid dateTime %s", str));
        }
        return date;
    }

    /**
     * Date 转 String
     * @param date 日期
     * @param format 转换格式: "yyyy-MM-dd HH:mm:ss"格式
     * @return
     */
    public static String dateToString(Date date, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        String strDate = "";
        try {
            if (date != null){
                strDate = dateFormat.format(date);
            }
        } catch (Exception e) {
            log.error("Date类型转String类型出错：" + e);
        }
        return strDate;
    }

    /**
     * 日期转时间戳
     * @param date 日期
     * @return 时间戳
     */
    public static Date localDateToDate(LocalDate date){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = date.atStartOfDay(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 时间戳转日期
     *
     * @param date 时间戳
     * @return 日期
     */
    static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 当前日期延后 n 天
     *
     * @param days 天数
     * @return {@link Date}
     */
    public static Date getAfterDaysFromNow(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 时间对象格式化转字符串
     *
     * @param date Date
     * @return String
     */
    public static String dateToStr(Date date) {
        if (Objects.isNull(date)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static boolean isOverTimeGapByDays(String timeStr, int days){
        return isOverTimeGapByDays(timeStr, DATE_FORMAT, days);
    }

    public static boolean isOverTimeGapByDays(String timeStr, String format, int days){
        // 将时间字符串转换为日期对象
        LocalDate timeDate = LocalDate.parse(timeStr,  DateTimeFormatter.ofPattern(format));
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 计算日期差距
        long gap = ChronoUnit.DAYS.between(timeDate, currentDate);
        return gap > days;

    }
}
