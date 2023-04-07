package com.sankuai.avatar.web.constant;

import com.sankuai.avatar.common.threadPool.ThreadPoolFactory;
import com.sankuai.avatar.common.threadPool.ThreadPoolType;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * web共用线程池
 * @author caoyang
 * @create 2023-03-22 19:26
 */
public class ThreadPoolConstant {
    private ThreadPoolConstant(){
        throw new IllegalStateException("Utility class");
    }

    public static final ThreadPoolExecutor WEB_EXECUTOR = ThreadPoolFactory.factory(ThreadPoolType.RHINO, 10, "ResourcePool");

}
