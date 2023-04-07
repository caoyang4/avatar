package com.sankuai.avatar.client.http.builder;

import com.sankuai.avatar.client.http.core.HttpClient;
import com.sankuai.avatar.client.http.interceptor.TimeoutInterceptor;

/**
 * http客户端工厂类，创建各类型http客户端
 *
 * @author qinwei05
 * @date 2022/10/09
 */
public class HttpClientFactory {

    private HttpClientFactory() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 默认http连接超时(秒)
     * 指的是建立连接所用的时间，适用于网络状况正常的情况下，两端连接所用的时间。
     * socket.connect(address, connectTimeout);
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 3;

    /**
     * 默认http读请求超时(秒)
     * 建立连接后从服务器读取到可用资源所用的时间
     */
    private static final int DEFAULT_READ_TIMEOUT = 10;

    /**
     * 默认http写请求超时(秒)
     * 写超时定义：向服务器发送请求时两个数据包之间的最长不活动时间
     */
    private static final int DEFAULT_WRITE_TIMEOUT = 10;

    /**
     * 默认失败重试次数
     * 默认不重试，每个系统或单个请求可自定义重试次数
     */
    private static final int DEFAULT_FAILED_RETRY_COUNT = 0;

    /**
     * 创建http客户端
     *
     * @param retryCount     重试计数
     * @param connectTimeout 连接超时
     * @param readTimeout    读取超时
     * @param writeTimeout   写超时
     * @return {@link HttpClient}
     */
    public static HttpClient factory(int retryCount, int connectTimeout, int readTimeout, int writeTimeout){
        // 每个子系统各自持有自己的HttpClient(非单例),但是底层OKHttpClient为单例
        HttpClientBuilder httpClientBuilder = new HttpClientBuilder();
        return httpClientBuilder
                .retryCount(retryCount)
                .connectTimeout(connectTimeout)
                .readTimeout(readTimeout)
                .writeTimeout(writeTimeout)
                .addInterceptor(new TimeoutInterceptor())
                .build();
    }

    /**
     * 重载，创建默认配置的http客户端
     *
     * @return {@link HttpClient}
     */
    public static HttpClient factory() {
        return factory(DEFAULT_FAILED_RETRY_COUNT, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, DEFAULT_WRITE_TIMEOUT);
    }
}
