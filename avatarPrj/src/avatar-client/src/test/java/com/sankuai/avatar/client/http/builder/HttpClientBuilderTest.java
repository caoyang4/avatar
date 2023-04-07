package com.sankuai.avatar.client.http.builder;

import com.sankuai.avatar.client.BaseHttpClient;
import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.http.core.HttpClient;
import com.sankuai.avatar.client.http.interceptor.TimeoutInterceptor;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class HttpClientBuilderTest extends TestBase {

    private HttpClientBuilder httpClientBuilder;

    @Before
    public void setUp() {
        httpClientBuilder = new HttpClientBuilder();
    }

    @Test
    public void testAddInterceptor() {
        // Setup
        final Interceptor interceptor = new TimeoutInterceptor();
        HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);

        // Run the test
        httpClientBuilder.addInterceptor(interceptor);

        // Verify the results
        logger.info(httpClientBuilder.toString());
        Mockito.verify(httpClientBuilder, times(1)).addInterceptor(interceptor);
    }

    @Test
    public void testAddHttpEventListener() {
        // Setup
        final HttpEventListener mockHttpEventListener = mock(HttpEventListener.class);
        HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);

        // Run the test
        httpClientBuilder.addHttpEventListener(mockHttpEventListener);

        // Verify the results
        Mockito.verify(httpClientBuilder, times(1)).addHttpEventListener(mockHttpEventListener);
    }

    @Test
    public void testBuild() {
        // Setup
        // Run the test
        final HttpClient result = httpClientBuilder.build();

        // Verify the results
        Assert.assertNotNull(result.get("https://jingmen.meituan.com/"));
    }

    @Test
    public void testGetSingletonOkHttpClient() throws ExecutionException, InterruptedException {
        // TODO 线程安全测试
        // Setup
        Callable<OkHttpClient> singletonCallable = () -> httpClientBuilder.getOkHttpClient();

        FutureTask<OkHttpClient> singletonFutureTask1 = new FutureTask<>(singletonCallable);
        FutureTask<OkHttpClient> singletonFutureTask2 = new FutureTask<>(singletonCallable);
        FutureTask<OkHttpClient> singletonFutureTask3 = new FutureTask<>(singletonCallable);

        Thread thread1 = new Thread(singletonFutureTask1);
        Thread thread2 = new Thread(singletonFutureTask2);
        Thread thread3 = new Thread(singletonFutureTask3);

        // Run the test
        thread1.start();
        thread2.start();
        thread3.start();

        // Verify the results
        Assert.assertEquals(singletonFutureTask1.get().hashCode(), singletonFutureTask2.get().hashCode());
        Assert.assertEquals(singletonFutureTask2.get().hashCode(), singletonFutureTask3.get().hashCode());
    }

    @Test
    public void testSingletonHttpClient() {
        // Run the test

        HttpClientOne httpClientOne = new HttpClientOne();
        HttpClientTwo httpClientTwo = new HttpClientTwo();
        Assert.assertNotEquals(httpClientOne.baseUrl(), httpClientTwo.baseUrl());
        Assert.assertNotEquals(httpClientOne.readTimeout(), httpClientTwo.readTimeout());

        HttpClient okhttpClientOne = httpClientOne.getHttpClient();
        HttpClient okhttpClientTwo = httpClientTwo.getHttpClient();

        // Verify the results
        // 每个系统持有自己的HttpClient
        Assert.assertNotEquals(okhttpClientOne, okhttpClientTwo);

        // 每个系统HttpClient底层共用同一个OkHttpClient
        OkHttpClient okHttpSingletonClientOne = (OkHttpClient) ReflectionTestUtils.getField(okhttpClientOne, "okHttpClient");
        OkHttpClient okHttpSingletonClientTwo = (OkHttpClient) ReflectionTestUtils.getField(okhttpClientTwo, "okHttpClient");
        assert okHttpSingletonClientOne != null;
        assert okHttpSingletonClientTwo != null;
        Assert.assertEquals(okHttpSingletonClientOne.hashCode(), okHttpSingletonClientTwo.hashCode());

    }

    public static class HttpClientOne extends BaseHttpClient {
        @Override
        protected String baseUrl() {
            return "https://www.baidu.com/1";
        }

        @Override
        protected int readTimeout() {
            return 1;
        }

        public HttpClient getHttpClient(){
            return httpClient;
        }
    }

    public static class HttpClientTwo extends BaseHttpClient {
        @Override
        protected String baseUrl() {
            return "https://www.baidu.com/2";
        }

        @Override
        protected int readTimeout() {
            return 2;
        }

        public HttpClient getHttpClient(){
            return httpClient;
        }
    }
}
