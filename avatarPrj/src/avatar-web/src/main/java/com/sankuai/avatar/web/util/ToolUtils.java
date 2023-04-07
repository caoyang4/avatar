package com.sankuai.avatar.web.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by shujian on 2020/3/1.
 *
 */
public final class ToolUtils {


    private ToolUtils() {
    }

    public static String nullToEmpty(String s){ return s != null ? s : ""; }

    public static Integer nullToZero(Integer n){
        return n != null ? n : 0;
    }

    public static <T> List<T> nullToEmptyList(List<T> l){ return l != null ? l : Lists.newArrayList(); }
}
