package com.sankuai.avatar.capacity.util;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-08-08 13:11
 */
public class OpsUtilsTest {

    @Test
    public void testGetOwts() throws IOException {
        List<String> paasOwtList = OpsUtils.getPaasOwtList();
        assert paasOwtList.size() > 0;
    }

    @Test
    public void testIsAppkeyJbox() {
        String srv = "dianping.dzu.dzopen_saas.dzsaas-operation";
        boolean isJbox = OpsUtils.isAppkeyJbox(srv);
        System.out.println(isJbox);
        assert isJbox;
    }
}