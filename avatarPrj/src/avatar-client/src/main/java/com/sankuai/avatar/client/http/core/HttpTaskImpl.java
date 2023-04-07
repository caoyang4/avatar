package com.sankuai.avatar.client.http.core;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.sankuai.avatar.common.constant.State;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author qinwei05
 * @date 2022/11/11 19:30
 * <p>
 * 请求执行
 * 一个基本的 get 请求需要如下几步：
 * 1、创建 OkHttpClient 实例
 * 2、创建 Request 实例
 * 3、创建 Call 实例
 * 4、执行 Call.execute() 或者 Call.enqueue()
 */
@Slf4j
public class HttpTaskImpl implements HttpTask {

    private final OkHttpClient okHttpClient;

    public HttpTaskImpl(OkHttpClient okHttpClient){
        this.okHttpClient = okHttpClient;
    }

    /**
     * 执行
     * 此处异常会向上抛出，具体处理逻辑交由上层业务逻辑判断，降级或忽略等
     *
     * @param request     http请求
     * @param retryCount  重试次数
     * @return {@link HttpResult}
     */
    @Override
    public HttpResult execute(Request request, int retryCount) {
        int count = 0;
        HttpResult httpResult;
        while (true) {
            httpResult = call(request);
            IOException exception = httpResult.getError();
            if (exception != null && count < retryCount){
                count++;
                // 此处打点内容为：未经包装的IOException原始异常类型
                Cat.logError(exception);
                log.error("URL：{}, 请求失败重试次数: {}, 错误信息为：{}", httpResult.getRequest().url(), count, exception.getMessage());
                continue;
            }
            return httpResult;
        }
    }


    /**
     * OKHTTP真实newCall调用
     *
     * @param request http请求
     * @return {@link HttpResult}
     */
    public HttpResult call(Request request) {
        Transaction transaction = Cat.newTransaction("HttpCall", String.format("%s %s", request.method(),
                Objects.nonNull(request.tag(String.class)) ? request.tag(String.class) : request.url().toString()));
        HttpResultImpl httpResult = new HttpResultImpl(request);
        try {
            httpResult.setResponse(okHttpClient.newCall(request).execute());
        } catch (IOException e) {
            transaction.setStatus(e);
            httpResult.exception(toState(e), e);
        } finally {
            // CAT附加完整URL与Body，便于问题排查
            transaction.addData(request.url().toString());
            if (request.body() != null) {
                transaction.addData(bodyToString(request));
            }
            transaction.complete();
        }
        return httpResult;
    }

    /**
     * 请求体转字符串
     * 用途: 第三方请求Body数据加入CAT打点,便于排查问题
     *
     * @param request 请求
     * @return {@link String}
     */
    private String bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            RequestBody body = copy.body();
            if (body == null) {
                return "";
            }
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset = StandardCharsets.UTF_8;
            return buffer.readString(charset);
        } catch (Exception e) {
            Cat.logError(e);
        }
        return "";
    }

    /**
     * 异常类型解析转换为具体状态
     *
     * @param e Exception
     * @return {@link State}
     */
    private State toState(IOException e) {
        if (e instanceof SocketTimeoutException) {
            return State.TIMEOUT;
        } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
            return State.NETWORK_ERROR;
        }
        return State.EXCEPTION;
    }
}
