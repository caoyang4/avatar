package com.sankuai.avatar.client;

import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseHttpClientTest {

    private BaseHttpClient baseHttpClient;

    @Before
    public void setUp() throws Exception {
        baseHttpClient = new BaseHttpClient() {
            @Override
            protected String baseUrl() {
                return "http://localhost";
            }
        };
    }

    @Test
    public void testHttpHeader() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("Content-Type", "application/json;charset=UTF-8");

        // Run the test
        final Map<String, String> result = baseHttpClient.httpHeader();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testOctoAuth() {
        assertThat(baseHttpClient.octoAuth()).isEqualTo("");
    }

    @Test
    public void testTokenAuth() {
        // Setup
        final Map<String, String> expectedResult = new HashMap<>();

        // Run the test
        final Map<String, String> result = baseHttpClient.tokenAuth();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testReadTimeout() {
        assertThat(baseHttpClient.readTimeout()).isEqualTo(10);
    }

    @Test
    public void testRetryCount() {
        assertThat(baseHttpClient.retryCount()).isEqualTo(0);
    }

    @Test
    public void testHttpEventListener() {
        // Setup
        // Run the test
        final List<HttpEventListener> result = baseHttpClient.httpEventListener();

        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);

    }

    @Test
    public void testBuildUrl1() {
        String url = "/api/v0.2/appkeys/{0}";
        assertThat(baseHttpClient.buildUrl(url, "appkey"))
                .isEqualTo("http://localhost/api/v0.2/appkeys/appkey");
    }

    @Test
    public void testBuildUrl2() {
        // Setup
        String url = "/api/v0.2/hosts/{hostname}";
        Map<String, String> pathParams = ImmutableMap.of("hostname", "hostname");

        // Run the test
        final String fullUrl = baseHttpClient.buildUrl(url, pathParams);

        // Verify the results
        assertThat(fullUrl).isEqualTo("http://localhost/api/v0.2/hosts/hostname");
    }

    @Test
    public void testBuildUrl3() {
        // Setup
        String url = "/api/v0.2/hosts/{hostname}";
        Map<String, String> pathParams = ImmutableMap.of("hostname", "hostname");
        Map<String, String> urlParams = ImmutableMap.of("ip", "10.10.101.01");

        // Run the test
        final String fullUrl = baseHttpClient.buildUrl(url, pathParams, urlParams);

        // Verify the results
        assertThat(fullUrl).isEqualTo("http://localhost/api/v0.2/hosts/hostname?ip=10.10.101.01");
    }
}
