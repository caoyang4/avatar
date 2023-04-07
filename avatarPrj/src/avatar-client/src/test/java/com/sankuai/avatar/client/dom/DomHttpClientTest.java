package com.sankuai.avatar.client.dom;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.dom.impl.DomHttpClientImpl;
import com.sankuai.avatar.client.dom.model.AppkeyResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


@Slf4j
public class DomHttpClientTest extends TestBase {

    DomHttpClient domHttpClient;

    public DomHttpClientTest() {
        this.domHttpClient = new DomHttpClientImpl();
    }

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(domHttpClient, "domApiDomain", "http://dom-api.sankuai.com");
        ReflectionTestUtils.setField(domHttpClient, "domApiToken", "Token 73b96bd4-3483-42e5-9d6c-904c0a538461");
        logger.info("=============" + getClass().getName() + "============");
    }

    @Test
    public void testGetAppkeyResourceUtil() {
        AppkeyResourceUtil appkeyResourceUtil = domHttpClient.getAppkeyResourceUtil(testAppkey);
        logger.info(appkeyResourceUtil.toString());
        Assert.assertTrue(appkeyResourceUtil.getResourceUtil().getLastWeekValue() > 0);
    }

    @Test
    public void testGetNotExistAppkeyResourceUtil() {
        AppkeyResourceUtil appkeyResourceUtil = domHttpClient.getAppkeyResourceUtil(testAppkey + "1234");
        logger.info(appkeyResourceUtil.toString());
        Assert.assertNull(appkeyResourceUtil.getAppkey());
    }
}