package com.sankuai.avatar.client.rocket;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.http.listener.HttpEventListener;
import com.sankuai.avatar.client.rocket.impl.RocketHttpClientImpl;
import com.sankuai.avatar.client.rocket.model.HostQueryRequest;
import com.sankuai.avatar.client.rocket.model.RocketHost;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RocketHttpClientTest extends TestBase {

    private RocketHttpClientImpl rocketHttpClientImplUnderTest;

    @Before
    public void setUp() throws Exception {
        rocketHttpClientImplUnderTest = new RocketHttpClientImpl();
        ReflectionTestUtils.setField(rocketHttpClientImplUnderTest, "rocketDomain", "http://rocket.tbd.test.sankuai.com");
        logger.info("=============" + getClass().getName() + "============");
    }

    @Test
    public void testHttpEventListener() {
        // Setup
        // Run the test
        final List<HttpEventListener> result = rocketHttpClientImplUnderTest.httpEventListener();

        // Verify the results
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void testBaseUrl() {
        assertThat(rocketHttpClientImplUnderTest.baseUrl()).isEqualTo("http://rocket.tbd.test.sankuai.com");
    }

    @Test
    public void testReadTimeout() {
        assertThat(rocketHttpClientImplUnderTest.readTimeout()).isEqualTo(10);
    }

    @Test
    public void testRetryCount() {
        assertThat(rocketHttpClientImplUnderTest.retryCount()).isEqualTo(2);
    }

    @Test
    public void testOctoAuth() {
        assertThat(rocketHttpClientImplUnderTest.octoAuth()).isEqualTo("com.sankuai.rocket.host.instance");
    }

    @Test
    public void testGetAppkeyHosts() {
        // Run the test
        final List<RocketHost> result = rocketHttpClientImplUnderTest.getAppkeyHosts(testAppkey).getData();
        logger.info(result.toString());
        // Verify the results
        Assert.assertTrue(result.size() >= 0);
    }

    @Test
    public void testGetNotExistAppkeyHosts() {
        // Setup
        // Run the test
        final List<RocketHost> result = rocketHttpClientImplUnderTest.getAppkeyHosts(testAppkey+"123").getData();
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testGetAppkeyHostsByQueryRequest() {
        // Setup
        final HostQueryRequest hostQueryRequest = HostQueryRequest.builder().appkey(testAppkey).build();

        // Run the test
        final List<RocketHost> result = rocketHttpClientImplUnderTest.getAppkeyHostsByQueryRequest(hostQueryRequest).getData();
        logger.info(result.toString());
        // Verify the results
        Assert.assertTrue(result.size() >= 0);
    }

    @Test
    public void testGetNotExistAppkeyHostsByQueryRequest() {
        // Setup
        final HostQueryRequest hostQueryRequest = HostQueryRequest.builder().appkey(testAppkey+"123").build();

        // Run the test
        final List<RocketHost> result = rocketHttpClientImplUnderTest.getAppkeyHostsByQueryRequest(hostQueryRequest).getData();
        logger.info(result.toString());
        // Verify the results
        Assert.assertEquals(0, result.size());
    }
}
