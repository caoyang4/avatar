package com.sankuai.avatar.client.http.core;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.common.constant.State;
import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpTaskImplTest extends TestBase {

    @Mock
    private OkHttpClient mockOkHttpClient;

    private HttpTaskImpl httpTaskImpl;

    @Before
    public void setUp() throws Exception {
        httpTaskImpl = new HttpTaskImpl(mockOkHttpClient);
    }

    @SneakyThrows
    @Test
    public void testRetryCountExecute() {
        // Setup
        Request.Builder builder = new Request.Builder().url(mockUrl);
        final Request request = builder.build();

        // Configure OkHttpClient.newCall(...).
        final Call mockCall = mock(Call.class);
        when(mockOkHttpClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenThrow(new IOException("IOException"));

        // Run the test
        // 重试次数验证
        final HttpResult result = httpTaskImpl.execute(request, 3);

        // Verify the results
        Mockito.verify(mockOkHttpClient, times(4)).newCall(request);
        Assert.assertNotNull(result.getError());
    }

    @Test
    public void testExecute() {
        // Setup
        Request.Builder builder = new Request.Builder().url(mockUrl);
        final Request request = builder.build();

        // Configure OkHttpClient.newCall(...).
        final Call mockCall = mock(Call.class);
        when(mockOkHttpClient.newCall(any(Request.class))).thenReturn(mockCall);

        // Run the test
        final HttpResult result = httpTaskImpl.execute(request, 0);

        // Verify the results
        Mockito.verify(mockOkHttpClient, times(1)).newCall(request);
        Assert.assertEquals(result.getState(), State.SUCCESS);
    }

    @Test
    public void testCall() {
        // Setup
        Request.Builder builder = new Request.Builder().url(mockUrl);
        final Request request = builder.build();

        // Configure OkHttpClient.newCall(...).
        final Call mockCall = mock(Call.class);
        when(mockOkHttpClient.newCall(any(Request.class))).thenReturn(mockCall);

        // Run the test
        final HttpResult result = httpTaskImpl.call(request);

        // Verify the results
        Mockito.verify(mockOkHttpClient, times(1)).newCall(request);
        Assert.assertEquals(result.getState(), State.SUCCESS);
    }
}
