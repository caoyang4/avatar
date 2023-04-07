package com.sankuai.avatar.client.http.listener;

import com.sankuai.avatar.client.http.core.HttpResult;
import okhttp3.Request;

/**
 * http事件监听器
 *
 * @author xk
 * @date 2022/11/09
 */
public interface HttpEventListener {

    /**
     * http之前
     *
     * @param request 请求
     */
    default void httpBefore(Request request) {
    }

    /**
     * http成功
     *
     * @param httpResult http结果
     */
    default void httpSuccess(HttpResult httpResult) {
    }

    /**
     * http失败
     *
     * @param httpResult http结果
     */
    default void httpFailure(HttpResult httpResult) {
    }

    /**
     * http后
     *
     * @param httpResult http结果
     */
    default void httpAfter(HttpResult httpResult) {
    }

}
