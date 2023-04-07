package com.sankuai.avatar.common.utils;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 对象通用工具类
 * @author caoyang
 * @create 2022-10-13 11:19
 */
public final class ObjectUtils {
    private ObjectUtils(){
    }

    /**
     * 对象的所有属性是否都为空
     * @param object 对象
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object){
        if (Objects.isNull(object)) {
            return true;
        }
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!Objects.isNull(field.get(object)) && StringUtils.isNotEmpty(field.get(object).toString())) {
                    return false;
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * null转空串
     *
     * @param obj     obj
     * @param nullStr str
     * @return {@link String}
     */
    public static String objectNull2Empty(Object obj, String nullStr){
        return obj == null ? nullStr : obj.toString();
    }

    /**
     * null转空串
     *
     * @param str str
     * @return {@link String}
     */
    public static String null2Empty(String str){
        return Objects.nonNull(str) ? str : "";
    }

    /**
     * null -> 0
     *
     * @param n n
     * @return int
     */
    public static int null2zero(Integer n){
        return n != null ? n : 0;
    }

    /**
     * null转空列表
     *
     * @param list 列表
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> null2EmptyList(List<T> list){
        return Objects.nonNull(list) ? list : Collections.emptyList();
    }
}
