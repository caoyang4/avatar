package com.sankuai.avatar.resource.host.core.filter;

import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;

import java.util.List;

/**
 * 过滤链
 * @author qinwei05
 */
public interface HostFilterChain<T> {

    /**
     * 过滤集合中的元素
     *
     * @param elements 元素集
     */
    List<T> filter(List<T> elements);

    /**
     * 获取上下文
     * @return 上下文
     */
    HostQueryRequestBO getContext();
}

