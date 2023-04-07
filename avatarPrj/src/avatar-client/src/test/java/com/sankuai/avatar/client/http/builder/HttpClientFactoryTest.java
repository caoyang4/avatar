package com.sankuai.avatar.client.http.builder;

import com.sankuai.avatar.client.http.core.HttpClient;
import okhttp3.OkHttpClient;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class HttpClientFactoryTest {

    @Test
    public void testFactory() {
        // Setup
        // Run the test
        final HttpClient httpClient = HttpClientFactory.factory(0, 3, 10, 10);
        OkHttpClient okHttpSingletonClient = (OkHttpClient) ReflectionTestUtils.getField(httpClient, "okHttpClient");

        // Verify the results
        assert okHttpSingletonClient != null;
    }
}
