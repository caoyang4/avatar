package com.sankuai.avatar.client.http.core;

import com.meituan.mtrace.util.StringUtils;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.http.utils.OceanusAuthUtils;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象http客户端
 *
 * @author qinwei05
 * @date 2022/10/17
 */
public abstract class AbstractHttpClient implements HttpClient {

    /**
     * 读超时
     */
    protected int readTimeout;

    /**
     * 重试计数
     */
    protected int retryCount;

    /**
     * http报头
     */
    protected Map<String, String> httpHeaders = new HashMap<>();

    /**
     * octoAuth
     */
    protected String octoAuth;

    /**
     * 令牌认证
     */
    protected Map<String, String> tokenAuth = new HashMap<>();

    /**
     * http事件监听器
     */
    protected final List<HttpEventListener> httpEventListeners = new ArrayList<>();

    @Override
    public HttpResult get(HttpRequest httpRequest) throws SdkCallException, SdkBusinessErrorException {
        return httpCall(httpRequest, HttpMethod.GET);
    }

    @Override
    public HttpResult post(HttpRequest httpRequest) throws SdkCallException, SdkBusinessErrorException{
        return httpCall(httpRequest, HttpMethod.POST);
    }

    @Override
    public HttpResult delete(HttpRequest httpRequest) throws SdkCallException, SdkBusinessErrorException{
        return httpCall(httpRequest, HttpMethod.DELETE);
    }

    @Override
    public HttpResult put(HttpRequest httpRequest) throws SdkCallException, SdkBusinessErrorException{
        return httpCall(httpRequest, HttpMethod.PUT);
    }

    @Override
    public void setOctoAuth(String octoAuth) {
        this.octoAuth = octoAuth;
    }

    @Override
    public void setTokenAuth(Map<String, String> tokenAuth) {
        this.tokenAuth.putAll(tokenAuth);
    }

    @Override
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    @Override
    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public void setHttpHeaders(Map<String, String> httpHeaders) {
        this.httpHeaders.putAll(httpHeaders);
    }

    @Override
    public void addHttpEventListener(HttpEventListener httpEventListener) {
        this.httpEventListeners.add(httpEventListener);
    }

    @Override
    public Map<String, String> octoAuth(){
        if (StringUtils.isNotBlank(octoAuth)){
            return OceanusAuthUtils.getUniformAuthHeaders(octoAuth);
        }
        return new HashMap<>();
    }

    /**
     * http调用
     *
     * @param httpRequest    请求
     * @param httpMethod     http方法
     * @return {@link HttpResult}
     */
    protected abstract HttpResult httpCall(HttpRequest httpRequest, HttpMethod httpMethod) throws SdkCallException, SdkBusinessErrorException;
}
