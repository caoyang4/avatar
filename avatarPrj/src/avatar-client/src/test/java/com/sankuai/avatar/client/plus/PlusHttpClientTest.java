package com.sankuai.avatar.client.plus;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.plus.impl.PlusHttpClientImpl;
import com.sankuai.avatar.client.plus.model.PlusRelease;
import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;


/**
 * @author qinwei05
 * @date 2022/10/7 16:37
 */
public class PlusHttpClientTest extends TestBase {

    private PlusHttpClient plusHttpClient;

    public PlusHttpClientTest() {
        this.plusHttpClient = new PlusHttpClientImpl();
    }

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(plusHttpClient, "plusDomain", "http://plus.sankuai.com");
        logger.info("=============" + getClass().getName() + "============");
    }

    @Test
    public void getBindPlusByAppkey() {
        List<PlusRelease> plusReleaseList = plusHttpClient.getBindPlusByAppkey(testAppkey);
        logger.info(plusReleaseList.toString());
        assertEquals(plusReleaseList.get(0).getName(), testAppkey);
    }

    @Test
    public void getNotExistBindPlusByAppkey() {
        List<PlusRelease> plusReleaseList = plusHttpClient.getBindPlusByAppkey(testAppkey + "123");
        logger.info(plusReleaseList.toString());
        Assert.assertEquals(0, plusReleaseList.size());
    }

    @Test
    public void getAppliedPlusByAppkey() {
        List<PlusRelease> plusReleaseList = plusHttpClient.getAppliedPlusByAppkey(testAppkey);
        logger.info(plusReleaseList.toString());
        assertEquals(plusReleaseList.get(0).getName(), testAppkey);
    }

    @Test
    public void getNotExistAppliedPlusByAppkey() {
        List<PlusRelease> plusReleaseList = plusHttpClient.getAppliedPlusByAppkey(testAppkey + "123");
        logger.info(plusReleaseList.toString());
        Assert.assertEquals(0, plusReleaseList.size());
    }
}
