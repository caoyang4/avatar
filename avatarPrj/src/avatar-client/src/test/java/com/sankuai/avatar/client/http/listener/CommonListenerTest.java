package com.sankuai.avatar.client.http.listener;

import com.sankuai.avatar.client.http.core.HttpResult;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import okhttp3.Request;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
/**
 * 监听器测试
 *
 * @author root
 * @date 2022/11/23
 */
public class CommonListenerTest {

    private final String passStr = "{\n" +
            "\"code\": 0,\n" +
            "\"message\": \"操作成功\"\n" +
            "}";

    private final String errorStr = "{\n" +
            "\"code\": 3,\n" +
            "\"message\": \"操作失败\",\n" +
            "\"data\": \"data\"\n" +
            "}";

    /**
     * 测试请求失败时：HTTP状态码异常抛错
     */
    @Test
    public void testExplainHttpFailureStatusCode() {
        // Setup
        final HttpEventListener httpEventListener = CommonListener.explainHttpFailureStatusCode();
        HttpResult httpResult = Mockito.mock(HttpResult.class);
        Request request = new Request.Builder().url("https://jingmen.meituan.com/").build();
        Mockito.when(httpResult.getStatusCode()).thenReturn(0, 403);
        Mockito.when(httpResult.getError()).thenReturn(new IOException(""), new IOException(""));
        Mockito.when(httpResult.getRequest()).thenReturn(request, request);

        // Run the test and Verify the results
        assertThatThrownBy(() -> httpEventListener.httpFailure(httpResult))
                .isInstanceOf(SdkCallException.class)
                .hasMessageStartingWith("请求异常");
        assertThatThrownBy(() -> httpEventListener.httpFailure(httpResult))
                .isInstanceOf(SdkCallException.class)
                .hasMessageStartingWith("请求失败!");
    }

    /**
     * 测试请求业务逻辑异常抛错
     */
    @Test
    public void testExplainHttpBusinessCode() {
        // Setup
        final HttpEventListener httpEventListener = CommonListener.explainHttpBusinessCode("$.code", 0);
        HttpResult httpResult = Mockito.mock(HttpResult.class);
        Request request = new Request.Builder().url("https://jingmen.meituan.com/").build();
        Mockito.when(httpResult.getRequest()).thenReturn(request, request);
        Mockito.when(httpResult.getResponseString()).thenReturn(passStr, errorStr);

        // Run the test and Verify the results
        httpEventListener.httpAfter(httpResult);
        assertThatThrownBy(() -> httpEventListener.httpAfter(httpResult))
                .isInstanceOf(SdkBusinessErrorException.class)
                .hasMessageStartingWith("请求 https://jingmen.meituan.com/ 业务逻辑返回异常");
    }

    /**
     * 测试指定返回路径值是否不为空
     */
    @Test
    public void testIsNotBlank() {
        // Setup
        final HttpEventListener httpEventListener = CommonListener.isNotBlank("$.data");
        HttpResult httpResult = Mockito.mock(HttpResult.class);
        Request request = new Request.Builder().url("https://jingmen.meituan.com/").build();
        Mockito.when(httpResult.getRequest()).thenReturn(request);
        Mockito.when(httpResult.getResponseString()).thenReturn(passStr);

        // Run the test and Verify the results
        assertThatThrownBy(() -> httpEventListener.httpAfter(httpResult))
                .isInstanceOf(SdkBusinessErrorException.class);}

    /**
     * 测试指定返回路径值是否为空
     */
    @Test
    public void testIsBlank() {
        // Setup
        final HttpEventListener httpEventListener = CommonListener.isBlank("$.data");
        HttpResult httpResult = Mockito.mock(HttpResult.class);
        Request request = new Request.Builder().url("https://jingmen.meituan.com/").build();
        Mockito.when(httpResult.getRequest()).thenReturn(request);
        Mockito.when(httpResult.getResponseString()).thenReturn(errorStr);

        // Run the test and Verify the results
        assertThatThrownBy(() -> httpEventListener.httpAfter(httpResult))
                .isInstanceOf(SdkBusinessErrorException.class);}

    /**
     * 测试返回值与预期值是否相等
     */
    @Test
    public void testEqual() {
        // Setup
        final HttpEventListener httpEventListener = CommonListener.equals("操作成功", "$.message");
        HttpResult httpResult = Mockito.mock(HttpResult.class);
        Request request = new Request.Builder().url("https://jingmen.meituan.com/").build();
        Mockito.when(httpResult.getRequest()).thenReturn(request, request);
        Mockito.when(httpResult.getResponseString()).thenReturn(passStr, errorStr);

        // Run the test and Verify the results
        httpEventListener.httpAfter(httpResult);
        assertThatThrownBy(() -> httpEventListener.httpAfter(httpResult))
                .isInstanceOf(SdkBusinessErrorException.class)
                .hasMessageStartingWith("请求错误");
    }
}
