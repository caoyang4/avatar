package com.sankuai.avatar.workflow.core.config;

import com.sankuai.avatar.common.threadPool.ThreadPoolFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 公共任务线程池, 一些分散的任务需要异步执行, 可以使用一个公共的线程池。
 *
 * @author xk
 */
@Configuration
public class TaskThreadPoolConfig {

    /**
     * 创建任务线程池
     *
     * @return {@link ThreadPoolExecutor}
     */
    @Bean("taskThreadPool")
    public ThreadPoolExecutor taskThreadPool() {
        return ThreadPoolFactory.factory("taskThread");
    }
}
