package com.sankuai.avatar.resource.host.core.filter.rule;

import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.core.filter.Filter;
import com.sankuai.avatar.resource.host.core.filter.OrderedFilter;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qinwei05
 * @date 2023/2/26 17:30
 */

public class OrderedOriginTypeFilterRule {

    private OrderedOriginTypeFilterRule() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 资源池相关筛选主机(带默认优先级)
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> orderedOriginTypeFilter(){
        return new OrderedFilter<>(originTypeFilter(), 3);
    }

    /**
     * 资源池相关筛选主机
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> originTypeFilter() {
        return (hosts, chain) -> {
            HostQueryRequestBO request = chain.getContext();
            // 指定资源池过滤
            if (StringUtils.isNotBlank(request.getOriginType())){
                List<HostAttributesBO> result = hosts.stream()
                        .filter(host -> Objects.equals(request.getOriginType(), host.getOriginType()))
                        .collect(Collectors.toList());
                return chain.filter(result);
            }
            // 特殊资源池过滤逻辑
            List<String> keyWords;
            if (Boolean.TRUE.equals(request.getElastic()) || Boolean.TRUE.equals(request.getExchange())) {
                keyWords = Arrays.asList("结算单元池", "通用资源池", "专属资源池", "紧急资源池", "活动资源池");
            } else if (Boolean.TRUE.equals(request.getReplaceUrgency())) {
                keyWords = Arrays.asList("结算单元池", "通用资源池");
            } else if (Boolean.TRUE.equals(request.getActiveResourcePool())) {
                keyWords = Collections.singletonList("活动资源池");
            } else {
                keyWords = new ArrayList<>();
            }
            if (CollectionUtils.isEmpty(keyWords)) {
                return chain.filter(hosts);
            }
            List<HostAttributesBO> result = hosts.stream()
                    .filter(host -> keyWords.contains(host.getOriginType()))
                    .collect(Collectors.toList());
            return chain.filter(result);
        };
    }
}
