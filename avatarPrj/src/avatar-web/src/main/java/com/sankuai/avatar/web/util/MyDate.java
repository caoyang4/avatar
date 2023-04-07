package com.sankuai.avatar.web.util;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shujian on 2020/2/19.
 *
 */
public class MyDate {
    /**
     * 获取当前时间,
     * @return 格式: yyyy-MM-dd
     */
    public static String getStringNowDay(){
        Date curTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(curTime);
    }

    /**
     * 获取昨天的日期
     * @return 时间格式: yyyy-MM-dd
     */
    public static String getStringYesterday(Integer offset){
        Date curTime = new Date();
        Date yesd = new Date(curTime.getTime() - (86400000 * offset));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(yesd);
    }

    public static String getOffsetDay(Integer offset){
        Date curTime = new Date();
        Date expectDay = new Date(curTime.getTime() - (86400000 * offset));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(expectDay);
    }

}
