package com.sankuai.avatar.sdk.manager;


import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-03-17
 **/
public interface ServiceCatalogHttpClient {
    /**
     * 不带参数的get方法
     * @param path
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    <T>T get(String path, Class<T> clazz) throws Exception;
    /**
     * 带参数的get方法
     * @param path
     * @param params
     * @return
     */
    <T>T get(String path, Map<String, Object> params, Class<T> clazz) throws Exception;

    /**
     * 带参数的get方法
     * @param path
     * @param params
     * @return
     */
    <T>T get(String path, Map<String, Object> params, TypeReference<T> typeReference) throws Exception;
    /**
     * @param path
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> List<T> getListData(String path, Class<T> clazz) throws Exception;
    /**
     * @param path
     * @param params
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> List<T> getListData(String path, Map<String, Object> params, Class<T> clazz) throws Exception;
}
