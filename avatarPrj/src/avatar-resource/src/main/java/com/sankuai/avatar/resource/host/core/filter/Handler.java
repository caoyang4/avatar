package com.sankuai.avatar.resource.host.core.filter;

import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import org.springframework.core.Ordered;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 过滤链执行器
 * @author qinwei05
 */
public class Handler<T> {

    /**
     * 过滤器
     */
    private final List<Filter<T>> filters;

    public Handler(List<Filter<T>> filters) {
        this.filters = this.loadFilters(filters);
    }

    /**
     * 初始化动作：载入过滤器
     *
     * @param filters 过滤器
     * @return {@link List}<{@link Filter}<{@link T}>>
     */
    private List<Filter<T>> loadFilters(List<Filter<T>> filters) {
        return IntStream.range(0, filters.size())
                .mapToObj(index -> createOrderedFilter(filters.get(index), index))
                .sorted(Comparator.comparingInt(OrderedFilter::getOrder))
                .collect(Collectors.toList());
    }

    /**
     * 创建带优先级过滤器
     *
     * @param filter 过滤器
     * @param index  下标
     * @return {@link OrderedFilter}<{@link T}>
     */
    private OrderedFilter<T> createOrderedFilter(Filter<T> filter, int index) {
        if (filter instanceof Ordered) {
            int order = ((Ordered) filter).getOrder();
            return new OrderedFilter<>(filter, order);
        } else {
            return new OrderedFilter<>(filter, index);
        }
    }

    /**
     * 初始化
     *
     * @param filters 过滤器
     * @return {@link Handler}<{@link T}>
     */
    public static <T> Handler<T> init(List<Filter<T>> filters) {
        // 过滤链路执行入口,初始化链路
        return new Handler<>(filters);
    }

    /**
     * 执行过滤操作
     *
     * @param elements 元素
     * @param context  上下文
     * @return {@link List}<{@link T}>
     */
    public List<T> handle(List<T> elements, HostQueryRequestBO context) {
        return new HostFilterChainImpl<>(filters, context).filter(elements);
    }

}

