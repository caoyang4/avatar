package com.sankuai.avatar.client.http.core;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.http.builder.HttpClientFactory;
import com.sankuai.avatar.client.http.listener.CommonListener;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import lombok.Data;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class SimpleTest extends TestBase {

    @Before
    public void setUp() {
        baseHttpClient = HttpClientFactory.factory(1, 3, 10, 10);
        baseHttpClient.addHttpEventListener(CommonListener.explainHttpFailureStatusCode());
    }

    @Test
    public void test() {
        HttpResult httpResult = baseHttpClient.get("https://jingmen.meituan.com/");
        logger.info(httpResult.getResponseString());
        Assert.assertNotNull(httpResult.getResponseString());
    }

    @Test
    public void testSdkCallException() {
        assertThatThrownBy(() -> baseHttpClient.get("http://ops.vip.sankuai.coms"))
                .isInstanceOf(SdkCallException.class)
                .hasMessageStartingWith("请求异常");
    }

    @Test
    public void testSdkCallExceptionWithHttpCode() {
        assertThatThrownBy(() -> baseHttpClient.get("https://ops.sankuai.com/api/v0.2/hosts/10.22.109.118/srvs"))
                .isInstanceOf(SdkCallException.class)
                .hasMessageStartingWith("请求失败! Http状态码为: [403](Forbidden -> 无访问该资源的权限，请检查鉴权逻辑！)");
    }

    /**
     * GET请求
     */
    @Test
    public void testGetRequest() {
        server.enqueue(new MockResponse().setBody("Hello World!"));
        String response = baseHttpClient.get(mockUrl + "/users").getResponseString();  // http://localhost:8080/users
        logger.info(response);
        Assert.assertEquals(response, "Hello World!");
    }

    /**
     * GET请求：超时重试
     */
    @Test
    public void testTimeoutRequest() {
        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json,charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody("Hello World!");
        mockResponse.throttleBody(1, 1, TimeUnit.SECONDS);
        server.enqueue(mockResponse);

        String response = baseHttpClient.get(mockUrl + "/users").getResponseString();  // http://localhost:8080/users
        logger.info(response);
        Assert.assertEquals(response, "Hello World!");
    }

    /**
     * POST请求
     */
    @Test
    public void testPostRequest() throws InterruptedException {
        server.enqueue(new MockResponse().setBody("OK"));
        Demo demo = new Demo("hello");

        HttpRequest httpRequest = HttpRequest.builder().url(mockUrl + "/users/").body(demo).readTimeout(2).retryCount(1).build();

        String resp = baseHttpClient.post(httpRequest).getResponseString();

        RecordedRequest request = server.takeRequest();
        Assert.assertEquals("OK", resp);
        Assert.assertTrue(request.getHeader("Content-Type").startsWith("application/json"));
        Assert.assertEquals("{\"name\":\"hello\"}", request.getBody().readUtf8());
    }

    /**
     * PUT请求
     */
    @Test
    public void testPutRequest() throws InterruptedException {
        server.enqueue(new MockResponse().setBody("OK"));
        Demo demo = new Demo("hello");

        HttpRequest httpRequest = HttpRequest.builder().url(mockUrl + "/users").body(demo).readTimeout(2).retryCount(1).build();

        String resp = baseHttpClient.put(httpRequest).getResponseString();

        RecordedRequest request = server.takeRequest();
        Assert.assertEquals("OK", resp);
        Assert.assertTrue(request.getHeader("Content-Type").startsWith("application/json"));
        Assert.assertEquals("{\"name\":\"hello\"}", request.getBody().readUtf8());
    }

    /**
     * Delete请求
     */
    @Test
    public void testDeleteRequest() throws InterruptedException {
        server.enqueue(new MockResponse().setBody("OK"));
        Demo demo = new Demo("hello");

        HttpRequest httpRequest = HttpRequest.builder().url(mockUrl + "/users/").body(demo).readTimeout(2).retryCount(1).build();
        String resp = baseHttpClient.delete(httpRequest).getResponseString();

        RecordedRequest request = server.takeRequest();
        Assert.assertEquals("OK", resp);
        Assert.assertTrue(request.getHeader("Content-Type").startsWith("application/json"));
        Assert.assertEquals("{\"name\":\"hello\"}", request.getBody().readUtf8());
    }
    @Data
    public static class Demo {
        private String name;
        public Demo(String name){
            this.name = name;
        }
    }
}
