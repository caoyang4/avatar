package com.sankuai.avatar.client.http.core;

import com.sankuai.avatar.client.http.builder.HttpClientBuilder;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpClient 客户端接口
 * @author qinwei05
 */
public interface HttpClient {

    /**
     * get 请求
     *
     * @param url      url
     * @return {@link HttpResult}
     */
    default HttpResult get(String url) throws SdkCallException, SdkBusinessErrorException{
        return this.get(HttpRequest.builder().url(url).build());
    }

    /**
     * get 请求
     *
     * @param url         url
     * @param retryCount  重试计数
     * @param readTimeout 读取超时
     * @return {@link HttpResult}
     */
    default HttpResult get(String url, int readTimeout, int retryCount) throws SdkCallException, SdkBusinessErrorException{
        return this.get(HttpRequest.builder().url(url).readTimeout(readTimeout).retryCount(retryCount).build());
    }

    /**
     * get 请求
     *
     * @param httpRequest 请求
     * @return {@link HttpResult}
     */
    HttpResult get(HttpRequest httpRequest) throws SdkCallException, SdkBusinessErrorException;

    /**
     * post 请求
     *
     * @param url         url
     * @param body        请求Body
     * @return {@link HttpResult}
     */
    default HttpResult post(String url, Object body) throws SdkCallException, SdkBusinessErrorException{
        return this.post(HttpRequest.builder().url(url).body(body).build());
    }

    /**
     * post 请求
     *
     * @param httpRequest 请求
     * @return {@link HttpResult}
     */
    HttpResult post(HttpRequest httpRequest) throws SdkCallException, SdkBusinessErrorException;

    /**
     * delete 请求
     *
     * @param httpRequest 请求
     * @return {@link HttpResult}
     */
    HttpResult delete(HttpRequest httpRequest) throws SdkCallException, SdkBusinessErrorException;

    /**
     * put 请求
     *
     * @param httpRequest 请求
     * @return {@link HttpResult}
     */
    HttpResult put(HttpRequest httpRequest) throws SdkCallException, SdkBusinessErrorException;

    /**
     * 设置OCTO鉴权Appkey
     *
     * @param octoAuth OCTO鉴权Appkey
     */
    void setOctoAuth(String octoAuth);

    /**
     * 设置令牌认证
     *
     * @param tokenAuth 令牌认证
     */
    void setTokenAuth(Map<String, String> tokenAuth);

    /**
     * 设置超时
     *
     * @param readTimeout 超时
     */
    void setReadTimeout(int readTimeout);

    /**
     * 设置重试计数
     *
     * @param retryCount 重试计数
     */
    void setRetryCount(int retryCount);

    /**
     * 设置http头信息
     *
     * @param httpHeaders http头信息
     */
    void setHttpHeaders(Map<String, String> httpHeaders);

    /**
     * http添加事件监听器
     *
     * @param httpEventListener http事件监听器
     */
    void addHttpEventListener(HttpEventListener httpEventListener);

    /**
     * octo身份验证
     */
    default Map<String, String> octoAuth() {
        return new HashMap<>();
    }

    /**
     * 令牌认证
     *
     * @param token 令牌
     */
    default void tokenAuth(Map<String, String> token) {

    }

    /**
     * 构建器
     *
     * @return {@link HttpClientBuilder}
     */
    static HttpClientBuilder builder() {
        return new HttpClientBuilder();
    }
}