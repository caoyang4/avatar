package com.sankuai.avatar.client.http.builder;

import com.sankuai.avatar.client.http.core.HttpClient;
import com.sankuai.avatar.client.http.core.HttpClientImpl;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HttpBuilderImpl, http客户端建造对象
 *
 * @author qinwei05
 * @date 2022/09/30
 */
@Slf4j
public class HttpClientBuilder {

    /**
     * OkHttpClient
     */
    private static volatile OkHttpClient okHttpClient;

    /**
     * 拦截器
     */
    private final List<Interceptor> interceptors = new ArrayList<>();

    /**
     * http事件监听器
     */
    private final List<HttpEventListener> HttpEventListeners = new ArrayList<>();

    /**
     * http头
     */
    private Map<String, String> httpHeaders = new HashMap<>();

    /**
     * 重试计数
     */
    private int retryCount;

    /**
     * 连接超时
     */
    private int connectTimeout;

    /**
     * 读取超时
     */
    private int readTimeout;

    /**
     * 写超时
     */
    private int writeTimeout;

    public HttpClientBuilder retryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public HttpClientBuilder connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public HttpClientBuilder readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public HttpClientBuilder writeTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public HttpClientBuilder httpHeaders(Map<String, String> headers) {
        this.httpHeaders = headers;
        return this;
    }

    public HttpClientBuilder addInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }

    public HttpClientBuilder addHttpEventListener(HttpEventListener httpEventListener) {
        this.HttpEventListeners.add(httpEventListener);
        return this;
    }

    /**
     * 构建HttpClient
     *
     * @return {@link HttpClient}
     */
    public HttpClient build() {
        HttpClientImpl httpClientImpl = new HttpClientImpl();
        httpClientImpl.setRetryCount(this.retryCount);
        httpClientImpl.setHttpHeaders(this.httpHeaders);
        // 注册监听器
        this.HttpEventListeners.forEach(httpClientImpl::addHttpEventListener);
        // 如果底层是HttpClientImpl(依赖Okhttp实现)对象，则注入okhttp客户端
        httpClientImpl.setOkHttpClient(getOkHttpClient());
        return httpClientImpl;
    }

    /**
     * OKHttp单例
     *
     * @return {@link OkHttpClient}
     */
    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (HttpClientBuilder.class) {
                if (okHttpClient == null) {
                    okHttpClient = okHttpClientBuild();
                }
            }
        }
        return okHttpClient;
    }

    /**
     * 配置Okhttp建设者, 用于创建okhttp客户端
     * 如果使用其他httpClient库, 可换成对应的建造对象
     *
     * @return {@link OkHttpClient}
     */
    private OkHttpClient okHttpClientBuild() {
        OkHttpClient.Builder okBuilder;
        if (okHttpClient != null) {
            okBuilder = okHttpClient.newBuilder();
        } else {
            okBuilder = new OkHttpClient.Builder();
        }
        okBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        okBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
        okBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        okBuilder.retryOnConnectionFailure(true);
        // 连接池
        okBuilder.connectionPool(new ConnectionPool(32, 5, TimeUnit.MINUTES));
        // 拦截器
        this.interceptors.forEach(okBuilder::addInterceptor);
        return okBuilder.build();
    }
}
