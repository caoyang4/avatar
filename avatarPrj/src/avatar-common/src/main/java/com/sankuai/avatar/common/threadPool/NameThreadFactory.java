package com.sankuai.avatar.common.threadPool;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂, 会设置线程名称以便识别
 *
 * @author xk
 */
public class NameThreadFactory implements ThreadFactory {
    private final AtomicInteger threadId = new AtomicInteger(0);
    private final String prefix;
    private final boolean daemon;

    public NameThreadFactory(String prefix) {
        this(prefix, true);
    }

    public NameThreadFactory(String prefix, boolean daemon) {
        this.prefix = String.format("avatar-%s-thread-", prefix);
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(@Nonnull Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(this.prefix + this.threadId.incrementAndGet());
        thread.setDaemon(this.daemon);
        return thread;
    }

}
