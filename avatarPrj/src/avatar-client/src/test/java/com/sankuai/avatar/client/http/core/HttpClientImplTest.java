package com.sankuai.avatar.client.http.core;

import com.sankuai.avatar.client.http.listener.HttpEventListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {HttpTask.class})
public class HttpClientImplTest {

    @InjectMocks
    HttpClientImpl httpClientImpl;

    @Mock
    OkHttpClient okHttpClientMock;

    @Mock
    HttpTask httpTaskMock;

    @Mock
    HttpEventListener httpEventListenerMock;

    @Before
    public void before() {
        this.httpClientImpl.addHttpEventListener(httpEventListenerMock);
    }

    @Test
    public void testHttpCall() {
        // mock HttpTask.of 静态方法
        PowerMockito.mockStatic(HttpTask.class);
        PowerMockito.when(HttpTask.of(this.okHttpClientMock)).thenReturn(this.httpTaskMock);

        // mock HttpResult对象，设置两次返回结果，覆盖成功和失败分支
        HttpResult httpResultMock = Mockito.mock(HttpResult.class);
        Mockito.when(httpResultMock.isSuccessful()).thenReturn(false, true);

        // mock httpTask 对象，执行execute方法返回 httpResultMock 对象
        Mockito.when(this.httpTaskMock.execute(any(), Mockito.anyInt())).thenReturn(httpResultMock);

        // 执行被单元测试方法
        HttpRequest getHttpRequest = HttpRequest.builder().url("https://localhost").body("data").readTimeout(10).retryCount(2).build();
        httpClientImpl.httpCall(getHttpRequest, HttpMethod.GET);
        HttpRequest postHttpRequest = HttpRequest.builder().url("https://localhost").body("data").readTimeout(10).retryCount(1).build();

        httpClientImpl.httpCall(postHttpRequest, HttpMethod.POST);

        // 验证测试结果
        ArgumentCaptor<Request> argumentCaptor = ArgumentCaptor.forClass(Request.class);
        Mockito.verify(httpTaskMock, Mockito.times(2)).execute(argumentCaptor.capture(), Mockito.anyInt());
        List<Request> requests = argumentCaptor.getAllValues();
        Assert.assertEquals(requests.get(0).method(), "GET");
        Assert.assertEquals(requests.get(1).method(), "POST");

        // 验证监听器
        Mockito.verify(httpEventListenerMock, Mockito.times(2)).httpBefore(any());
        Mockito.verify(httpEventListenerMock, Mockito.times(1)).httpSuccess(any());
        Mockito.verify(httpEventListenerMock, Mockito.times(1)).httpFailure(any());
        Mockito.verify(httpEventListenerMock, Mockito.times(2)).httpAfter(any());
    }

    @Test
    public void get() {
    }

    @Test
    public void post() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void put() {
    }
}