package com.sankuai.avatar.resource.jumper;

import com.sankuai.avatar.client.jumper.JumperHttpClient;
import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.jumper.impl.JumperResourceImpl;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class JumperResourceTest extends TestBase {

    /**
     * 测试Jumper跳板机用户
     */
    private static final String USER_NAME = "zhaozhifan02";

    @Mock
    private JumperHttpClient jumperHttpClient;

    private JumperResource jumperResource;

    @Override
    @Before
    public void setUp() {
        jumperResource = new JumperResourceImpl(jumperHttpClient);
    }

    @Test
    public void testUserUnlock() {
        doNothing().when(jumperHttpClient).userUnlock(anyString());
        jumperResource.userUnlock(USER_NAME);
        verify(jumperHttpClient).userUnlock(anyString());
    }

    @Test
    public void testUserUnlockWithEmptyUser() {
        jumperResource.userUnlock("");
        verify(jumperHttpClient, never()).userUnlock(anyString());
    }

    @Test
    public void passwordReset() {
        doNothing().when(jumperHttpClient).passwordReset(anyString());
        jumperResource.passwordReset(USER_NAME);
        verify(jumperHttpClient).passwordReset(anyString());
    }

    @Test
    public void passwordResetWithEmptyUser() {
        jumperResource.passwordReset("");
        verify(jumperHttpClient, never()).passwordReset(anyString());
    }
}