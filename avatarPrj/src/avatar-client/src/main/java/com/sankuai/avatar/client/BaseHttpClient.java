package com.sankuai.avatar.client;


import com.sankuai.avatar.client.http.builder.HttpClientFactory;
import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.http.core.HttpClient;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.http.utils.UrlUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * HttpClient抽象类
 *
 * @author qinwei05
 * @date 2022/10/08
 */
public abstract class BaseHttpClient {

    /**
     * http客户端
     */
    protected final HttpClient httpClient = this.buildHttpClient();

    /**
     * 读取http数据超时时间（秒）
     */
    private static final int READ_TIMEOUT = 10;

    /**
     * 失败重试次数(默认不重试)
     */
    private static final int RETRY_COUNT = 0;

    /**
     * 构建http客户端, 设置相关参数, 业务子类客户端按需实现
     *
     * @return {@link HttpClient}
     */
    private HttpClient buildHttpClient() {
        HttpClient baseHttpClient = HttpClientFactory.factory();
        baseHttpClient.setReadTimeout(this.readTimeout());
        baseHttpClient.setRetryCount(this.retryCount());
        baseHttpClient.setHttpHeaders(this.httpHeader());
        baseHttpClient.setOctoAuth(this.octoAuth());
        baseHttpClient.setTokenAuth(this.tokenAuth());
        this.httpEventListener().forEach(baseHttpClient::addHttpEventListener);
        return baseHttpClient;
    }

    /**
     * 根域名URL
     *
     * @return {@link String} appkey
     */
    protected abstract String baseUrl();

    /**
     * 根域名URL,支持按照环境切换
     * 支持场景：线上prod环境调用第三方系统的prod、test等环境
     *
     * @return {@link String} appkey
     */
    protected String baseUrlByEnv(EnvEnum env) {
        return baseUrl();
    }

    /**
     * 设置http头信息
     */
    protected Map<String, String> httpHeader() {
        HashMap<String, String> httpHeader = new HashMap<>(10);
        httpHeader.put("Content-Type", "application/json;charset=UTF-8");
        return httpHeader;
    }

    /**
     * 根域名
     *
     * @return {@link String} appkey
     */
    protected String octoAuth() {
        return "";
    }

    /**
     * 令牌认证
     */
    protected Map<String, String> tokenAuth() {
        return new HashMap<>();
    }

    /**
     * 读超时
     *
     * @return {@link int}
     */
    protected int readTimeout() {
        return READ_TIMEOUT;
    }

    /**
     * 重试次数
     *
     * @return {@link int}
     */
    protected int retryCount() {
        return RETRY_COUNT;
    }

    /**
     * http事件监听器
     *
     * @return {@link List}<{@link HttpEventListener}>
     */
    protected List<HttpEventListener> httpEventListener() {
        return Stream.of(CommonListener.explainHttpFailureStatusCode())
                .collect(Collectors.toList());
    }

    /**
     * 构建url, 逐个填充占位符
     * @param urlPath  urlPath
     * @param args     arg变长参数
     * @return {@link String}
     */
    protected String buildUrl(String urlPath, String...args) {
        String fullUrl = buildUrl(urlPath);
        if (args == null || args.length == 0) {
            return fullUrl;
        }
        Object[] arrObject = new String[args.length];
        System.arraycopy(args, 0, arrObject, 0, args.length);
        return MessageFormat.format(fullUrl, arrObject);
    }

    /**
     * 构建url, 无urlParams
     *
     * @param urlPath    url路径
     * @param pathParams 路径参数
     * @return {@link String}
     */
    protected String buildUrl(String urlPath, Map<String, String> pathParams) {
        String url = buildUrl(urlPath);
        return UrlUtils.buildUrl(url, pathParams, null);
    }

    /**
     * 构建url
     *
     * @param urlPath    urlPath
     * @param pathParams 路径参数
     * @param urlParams  url参数
     * @return {@link String}
     */
    protected String buildUrl(String urlPath, Map<String, String> pathParams, Map<String, String> urlParams) {
        String url = buildUrl(urlPath);
        return UrlUtils.buildUrl(url, pathParams, urlParams);
    }

    /**
     * 构建url（根域名+路径）
     *
     * @param urlPath url路径
     * @return {@link String}
     */
    protected String buildUrl(String urlPath) {
        String baseUrl = baseUrl();
        return UrlUtils.getFullUrl(baseUrl, urlPath);
    }

    protected String buildUrl(String urlPath, EnvEnum env) {
        String baseUrl = baseUrlByEnv(env);
        return UrlUtils.getFullUrl(baseUrl, urlPath);
    }
}
