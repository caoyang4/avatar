package com.sankuai.avatar.common.threadPool;

import lombok.Getter;

/**
 *  阿凡达 线程池类型
 *
 * @author kui.xu
 */
public enum ThreadPoolType {

    /**
     * rhino 线程池
     */
    RHINO("rhino"),

    /**
     * JDK 默认线程池
     */
    DEFAULT("default");

    ThreadPoolType(String name) {
        this.name = name;
    }

    @Getter
    private final String name;
}