package com.sankuai.avatar.common.threadPool;

import com.dianping.rhino.Rhino;
import com.dianping.rhino.threadpool.DefaultThreadPoolProperties.Setter;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工厂, 支持默认线程池 和 rhino线程池
 *
 * @author xk
 */
public class ThreadPoolFactory {

    /**
     * 重载简化版本线程池
     *
     * @param poolName 池名称
     * @return {@link ThreadPoolExecutor}
     */
    public static ThreadPoolExecutor factory(String poolName) {
        return ThreadPoolFactory.defaultThreadPool(10, poolName);
    }

    /**
     * 线程池工厂
     *
     * @param poolType 线程池类型
     * @param poolSize 核心线程数
     * @param poolName 线程池名称
     * @return {@link ThreadPoolExecutor}
     */
    public static ThreadPoolExecutor factory(ThreadPoolType poolType, Integer poolSize, String poolName) {
        if (poolType.equals(ThreadPoolType.RHINO)) {
            return ThreadPoolFactory.rhinoThreadPool(poolSize, poolName);
        }
        return ThreadPoolFactory.defaultThreadPool(poolSize, poolName);
    }

    /**
     * JDK线程池
     */
    private static ThreadPoolExecutor defaultThreadPool(int poolSize, String poolName) {
        return  new ThreadPoolExecutor(
                poolSize,
                poolSize,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new NameThreadFactory(poolName)
        );
    }

    /**
     * rhino线程池
     */
    private static ThreadPoolExecutor rhinoThreadPool(int poolSize, String poolName) {
        Setter setter = new Setter().withCoreSize(poolSize).withThreadFactory(new NameThreadFactory(poolName));
        return Rhino.newThreadPool(poolName, setter).getExecutor();
    }
}
