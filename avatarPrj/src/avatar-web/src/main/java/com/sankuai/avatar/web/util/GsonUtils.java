package com.sankuai.avatar.web.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by shujian on 2020/2/18.
 *
 */
public class GsonUtils {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

    public static String serialization(Object clazz){
        return gson.toJson(clazz);
    }

    public static <T> T deserialization(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }

    public static <T> T deserialization(String json, Type t){
        return gson.fromJson(json, t);
    }
}
