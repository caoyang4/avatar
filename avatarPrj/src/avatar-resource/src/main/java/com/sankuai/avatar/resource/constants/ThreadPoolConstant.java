package com.sankuai.avatar.resource.constants;

import com.sankuai.avatar.common.threadPool.ThreadPoolFactory;
import com.sankuai.avatar.common.threadPool.ThreadPoolType;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 资源共用线程池
 * @author caoyang
 * @create 2023-03-22 19:25
 */
public class ThreadPoolConstant {

    private ThreadPoolConstant(){
        throw new IllegalStateException("Utility class");
    }

    public static final ThreadPoolExecutor RESOURCE_EXECUTOR = ThreadPoolFactory.factory(ThreadPoolType.RHINO, 10, "ResourcePool");

}
