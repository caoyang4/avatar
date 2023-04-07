package com.sankuai.avatar.web.util;

import org.junit.Test;

import java.util.List;

/**
 * Created by shujian on 2020/2/17.
 *
 */
public class OpsUtilsTest {

    @Test
    public void testGetOwts() throws Exception{
        List<String> owt = OpsUtils.getPaasOwts();
        assert owt.size() > 0;
    }

    @Test
    public void testGetAppkeySrv() throws Exception{
        List<String> srv = OpsUtils.getAppkeySrv("com.sankuai.avatar.develop");
        assert srv.size() > 0;
    }
}
