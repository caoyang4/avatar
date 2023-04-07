package com.sankuai.avatar.client.http.core;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author qinwei05
 * @date 2022/11/22 13:17
 */
@Data
@Builder
public class HttpRequest {

    /**
     * 构造好的完整url = baseUrlPath + pathParams(填充路径) + urlParams(填充参数)
     * 可用来直接发起请求
     */
    private String url;

    /**
     * env
     */
    private String env;

    /**
     * 请求自定义鉴权Appkey
     */
    private String appkey;

    /**
     * 请求自定义鉴权token
     */
    private String token;

    /**
     * 路径参数
     */
    private Map<String, String> pathParams;

    /**
     * url参数
     */
    private Map<String, String> urlParams;

    /**
     * baseUrl：这里主要用于底层CAT聚合打点
     */
    private String baseUrlPath;

    /**
     * 请求体
     */
    private Object body;

    /**
     * 读超时
     */
    private int readTimeout;

    /**
     * 重试计数
     */
    private int retryCount;
}
