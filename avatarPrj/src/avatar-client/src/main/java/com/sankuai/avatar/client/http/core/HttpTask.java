package com.sankuai.avatar.client.http.core;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author xk
 */
public interface HttpTask {

    /**
     * 创建 HttpTask 对象
     *
     * @param okHttpClient   okhttp客户端
     * @return {@link HttpTask}
     */
    static HttpTask of(OkHttpClient okHttpClient) {
        return new HttpTaskImpl(okHttpClient);
    }

    /**
     * 执行 http 请求
     *
     * @param request     请求
     * @param retryCount  失败重试次数
     * @return {@link HttpResult}
     */
    HttpResult execute(Request request, int retryCount);
}
