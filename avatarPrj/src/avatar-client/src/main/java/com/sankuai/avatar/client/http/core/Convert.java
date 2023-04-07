package com.sankuai.avatar.client.http.core;

import com.jayway.jsonpath.TypeRef;

import java.util.List;

/**
 * HTTP返回体格式转换
 *
 * @author qinwei05
 * @date 2022/10/09
 */
public interface Convert {

    /**
     * 转换为类对象
     * @param <T> 目标泛型
     * @param type 目标类型
     * @return 报文体Json文本转JavaBean
     */
    <T> T toBean(Class<T> type);

    /**
     * 转换为类对象集合
     *
     * @param type     类型
     * @param jsonPath json路径
     * @return {@link List}<{@link T}>
     */
    <T> List<T> toBeanList(Class<T> type, String jsonPath);

    /**
     * 根据jsonPath层级反序列化
     *
     * @param type     类型
     * @param jsonPath json路径
     * @return {@link T}
     */
    <T> T toBean(Class<T> type, String jsonPath);

    /**
     * 支持反序列化多层嵌套泛型类
     *
     * @param type 目标类型
     * @return 报文体Json文本转JavaBean
     */
    <T> T toBean(TypeRef<T> type);

    /**
     * 支持反序列化多层嵌套泛型类与jsonPath层级
     *
     * @param type 目标类型
     * @return 报文体Json文本转JavaBean
     */
    <T> T toBean(TypeRef<T> type, String jsonPath);
}
