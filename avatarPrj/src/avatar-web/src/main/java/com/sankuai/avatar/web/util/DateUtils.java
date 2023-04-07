package com.sankuai.avatar.web.util;



import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author qinwei05
 */
public class DateUtils {

    /**
     * LocalDateTime convert Date
     *
     * @param localDateTime localDateTime
     * @return Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date convert LocalDateTime
     *
     * @param dateTime dateTime
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date dateTime) {
        if (dateTime == null){
            return null;
        }
        return dateTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * 计算两个日期时间的相差天数与小时
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return String
     */
    public static String calcLocalDateTime(LocalDateTime startTime, LocalDateTime endTime) {

        Duration between = Duration.between(startTime, endTime);
        StringBuilder timeBuilder = new StringBuilder();
        long dayResult = between.toDays();

        if (dayResult > 0) {
            long betweenHours = between.toHours();
            long hourResult = dayResult * 24 - betweenHours;
            timeBuilder.append(dayResult).append("天");
            if (hourResult > 0) {
                timeBuilder.append(" ").append(hourResult).append("小时");
            } else if (hourResult < 0) {
                timeBuilder.append(" ").append(-hourResult).append("小时");
            }
        } else {
            timeBuilder.append(between.toHours()).append("小时");
        }
        return timeBuilder.toString();
    }
}
