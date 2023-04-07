package com.sankuai.avatar.capacity.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MqUtilsTest {

    @Test
    public void isAccessToMQ() {
        boolean flag = MqUtils.isAccessToMQ("avatar-workflow-web");
        System.out.println(flag);
        assert !flag;
    }

    @Test
    public void testIsAccessToMQProducer() {
        boolean flag = MqUtils.isAccessToMQProducer("com.sankuai.bms.unigate");
        System.out.println(flag);
        assert flag;
    }
}