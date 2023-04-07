package com.sankuai.avatar.resource.host.core.filter;

import java.util.List;

/**
 * 过滤器标准
 * @author wellqin
 * @param <T>
 */
public interface Filter<T> {
    /**
     * 过滤器标准定义
     * @param elements  elements
     * @param chain chain
     * @return java.util.Collection<T>
     */
    List<T> filter(List<T> elements, HostFilterChain<T> chain);
}

