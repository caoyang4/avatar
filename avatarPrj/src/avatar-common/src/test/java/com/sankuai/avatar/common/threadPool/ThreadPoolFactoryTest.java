package com.sankuai.avatar.common.threadPool;

import org.junit.Test;
import org.junit.Assert;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolFactoryTest {

    @Test
    public void testDefaultThreadPool() {
        // Setup
        ThreadPoolExecutor threadPool = ThreadPoolFactory.factory(ThreadPoolType.DEFAULT,5, "default");

        // Verify the results
        assert threadPool != null;
        Object prefix = ReflectionTestUtils.getField(threadPool.getThreadFactory(), "prefix");
        Assert.assertEquals(prefix, "avatar-default-thread-");
        Assert.assertEquals(threadPool.getCorePoolSize(), 5);
    }

    @Test
    public void testRhinoThreadPool() {
        // Setup
        ThreadPoolExecutor threadPool = ThreadPoolFactory.factory(ThreadPoolType.RHINO,5, "rhino");

        // Verify the results
        assert threadPool != null;
        Object prefix = ReflectionTestUtils.getField(threadPool.getThreadFactory(), "prefix");
        Assert.assertEquals(prefix, "avatar-rhino-thread-");
        Assert.assertEquals(threadPool.getCorePoolSize(), 5);
    }
}
