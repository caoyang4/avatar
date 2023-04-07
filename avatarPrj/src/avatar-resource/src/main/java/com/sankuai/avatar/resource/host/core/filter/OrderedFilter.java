package com.sankuai.avatar.resource.host.core.filter;

import lombok.Getter;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * 有序过滤器
 * @author qinwei05
 */
@Getter
public class OrderedFilter<T> implements Filter<T>, Ordered {

    /**
     * 有序过滤器
     */
    private final Filter<T> delegate;

    /**
     * 顺序
     */
    private final int order;

    public OrderedFilter(Filter<T> delegate, int order) {
        this.delegate = delegate;
        this.order = order;
    }

    @Override
    public List<T> filter(List<T> elements, HostFilterChain<T> chain) {
        return this.delegate.filter(elements, chain);
    }
}

