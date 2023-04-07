package com.sankuai.avatar.client.http.core;

import com.meituan.mtrace.util.StringUtils;
import com.sankuai.avatar.client.http.utils.UrlUtils;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.JsonUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * http客户端实现类，okhttp版本
 *
 * @author qinwei05
 * @date 2022/10/18
 */
public class HttpClientImpl extends AbstractHttpClient {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");

    private static final String CUSTOM_READ_TIMEOUT_TAG = "CustomReadTimeout";

    private OkHttpClient okHttpClient;

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    protected HttpResult httpCall(HttpRequest httpRequest, HttpMethod httpMethod) throws SdkCallException, SdkBusinessErrorException{
        return this.doHttpCall(HttpTask.of(okHttpClient), httpRequest, httpMethod);
    }

    /**
     * 执行http请求
     *
     * @param httpTask    http任务
     * @param httpRequest 请求
     * @param httpMethod  方法
     * @return {@link HttpResult}
     */
    private HttpResult doHttpCall(HttpTask httpTask, HttpRequest httpRequest, HttpMethod httpMethod)
            throws SdkCallException, SdkBusinessErrorException {
        Request request = this.getRequest(httpRequest, httpMethod);

        // 1、httpTask httpBefore 请求前置处理
        httpEventListeners.forEach(httpEventListener -> httpEventListener.httpBefore(request));
        // 2、httpTask execute
        int httpRequestRetryCount = httpRequest.getRetryCount();
        HttpResult httpResult = httpTask.execute(request, getRealRetryCount(httpRequestRetryCount));

        if (httpResult.isSuccessful()){
            // 3、httpTask httpSuccess 请求成功
            httpEventListeners.forEach(httpEventListener -> httpEventListener.httpSuccess(httpResult));
        } else {
            // 4、httpTask httpFailure 请求成功
            httpEventListeners.forEach(httpEventListener -> httpEventListener.httpFailure(httpResult));
        }
        // 5、httpTask httpAfter 请求完成处理
        httpEventListeners.forEach(httpEventListener -> httpEventListener.httpAfter(httpResult));

        return httpResult;
    }

    /**
     * 创建 Request 请求体对象
     * 创建 Request 对象, 拼接所有需要的数据，url、Header、auth...
     * task拿到三个：okHttpClient，Request，HttpEventListeners
     *
     * @return {@link Request}
     */
    private Request getRequest(HttpRequest httpRequest, HttpMethod httpMethod) {
        String url = StringUtils.isNotBlank(httpRequest.getUrl()) ? httpRequest.getUrl() : getUrl(httpRequest);
        Request.Builder builder = new Request.Builder().url(url);
        addHeader(builder, httpHeaders);
        addHeader(builder, tokenAuth);
        int realReadTimeout = httpRequest.getReadTimeout() > 0 ? httpRequest.getReadTimeout() : this.readTimeout;
        if (realReadTimeout > 0) {
            // 设置超时时间，仅对单次请求有效
            buildReadTimeoutHeader(builder, realReadTimeout);
        }
        Map<String, String> octoAuthHeaders = octoAuth();
        if (octoAuthHeaders.size() > 0){
            addHeader(builder, octoAuthHeaders);
        }
        if (StringUtils.isNotBlank(httpRequest.getBaseUrlPath())){
            builder.tag(String.class, httpRequest.getBaseUrlPath());
        }
        if (httpMethod == HttpMethod.GET) {
            builder.method(httpMethod.name(), null);
        } else if (httpRequest.getBody() != null) {
            builder.method(httpMethod.name(), toRequestBody(httpRequest.getBody()));
        }
        return builder.build();
    }

    private String getUrl(HttpRequest httpRequest){
        return UrlUtils.buildUrl(
                httpRequest.getBaseUrlPath(),
                httpRequest.getPathParams(),
                httpRequest.getUrlParams()
        );
    }

    private int getRealRetryCount(int retryCount){
        // 自定义单个请求重试次数 > 系统默认重试次数 （默认不重试）
        if (retryCount > 0){
            return retryCount;
        } else {
            return this.retryCount;
        }
    }

    /**
     * 增加http头
     *
     * @param builder 构建器
     * @param headers 头
     */
    private void addHeader(Request.Builder builder, Map<String, String> headers){
        if (headers != null) {
            headers.forEach((name, value) -> {
                if (value == null) {
                    return;
                }
                builder.addHeader(name, value);
            });
        }
    }

    /**
     * 设置超时时间
     *
     * @param builder     构建器
     * @param readTimeout 读取超时
     */
    private void buildReadTimeoutHeader(Request.Builder builder, int readTimeout) {
        if (readTimeout > 0) {
            builder.addHeader(CUSTOM_READ_TIMEOUT_TAG, String.valueOf(readTimeout * 1000));
        }
    }

    /**
     * Object请求体 -> String -> RequestBody
     *
     * @param bodyObj 请求体
     * @return {@link RequestBody}
     */
    private RequestBody toRequestBody(Object bodyObj) {
        String requestBody = JsonUtil.bean2Json(bodyObj);
        if (StringUtils.isBlank(requestBody)){
            throw new SdkCallException("JSON序列化异常");
        }
        return RequestBody.create(JSON_MEDIA_TYPE, requestBody);
    }
}
