package com.sankuai.avatar.resource.host.core.filter;

import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import lombok.Data;

import java.util.*;

/**
 * 过滤链
 * @author qinwei05
 */
@Data
public class HostFilterChainImpl<T> implements HostFilterChain<T> {

    /**
     * 过滤器
     */
    private final List<Filter<T>> filters;

    /**
     * 记录过滤器的位置，当前执行到哪一个过滤器
     */
    private final int index;

    /**
     * 上下文
     */
    private final HostQueryRequestBO context;

    /**
     * 执行过滤操作
     */
    public List<T> handle(List<T> elements) {
        return new HostFilterChainImpl<>(filters).filter(elements);
    }

    /**
     * 执行过滤操作（带上下文）
     */
    public List<T> handle(List<T> elements, HostQueryRequestBO context) {
        return new HostFilterChainImpl<>(filters, context).filter(elements);
    }

    public HostFilterChainImpl(List<Filter<T>> filters) {
        this.filters = filters;
        this.index = 0;
        this.context = new HostQueryRequestBO();
    }

    public HostFilterChainImpl(List<Filter<T>> filters, HostQueryRequestBO context) {
        this.filters = filters;
        this.index = 0;
        this.context = context;
    }

    /**
     * 主机过滤器链
     *
     * @param parent 上一个节点
     * @param index  当前节点
     */
    public HostFilterChainImpl(HostFilterChainImpl<T> parent, int index) {
        // this主要就是第一个对象中全部的过滤器，就是把全量付给下一个
        this.filters = parent.getFilters();
        this.index = index;
        this.context = parent.getContext();
    }

    /**
     * 编排好过滤器的执行顺序
     *
     * @param elements 元素集
     * @return {@link List}<{@link T}>
     */
    @Override
    public List<T> filter(List<T> elements) {
        if (this.index < filters.size()) {
            Filter<T> filter = filters.get(this.index);
            HostFilterChainImpl<T> chain = new HostFilterChainImpl<>(this, this.index + 1);
            return filter.filter(elements, chain);
        }
        else {
            return elements;
        }
    }

}
