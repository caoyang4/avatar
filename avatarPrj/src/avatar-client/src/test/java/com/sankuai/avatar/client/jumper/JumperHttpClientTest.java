package com.sankuai.avatar.client.jumper;

import com.sankuai.avatar.client.TestBase;
import com.sankuai.avatar.client.jumper.impl.JumperHttpClientImpl;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JumperHttpClientTest extends TestBase {

    /**
     * 测试Jumper跳板机用户
     */
    private static final String USER_NAME = "zhaozhifan02";

    private JumperHttpClient jumperHttpClient;

    public JumperHttpClientTest() {
        this.jumperHttpClient = new JumperHttpClientImpl();
    }

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(jumperHttpClient, "jumperDomain", "http://jumper-web.sre.test.sankuai.com");
        ReflectionTestUtils.setField(jumperHttpClient, "jumperToken", "945ZKME&DPJ*WyAd");
        logger.info("=============" + getClass().getName() + "============");
    }

    @Test(expected = SdkBusinessErrorException.class)
    public void testUserUnlockSdkBusinessErrorException() {
        jumperHttpClient.userUnlock("");
    }

    @Test
    public void testUserUnlock() {
        jumperHttpClient.userUnlock(USER_NAME);
        Assert.assertTrue(true);
    }


    @Test(expected = SdkBusinessErrorException.class)
    public void testPasswordResetSdkBusinessErrorException() {
        jumperHttpClient.passwordReset("");
    }

    @Test
    public void testPasswordReset() {
        jumperHttpClient.userUnlock(USER_NAME);
        Assert.assertTrue(true);
    }

}