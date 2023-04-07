package com.sankuai.avatar.resource.host.core.filter.rule;

import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.core.filter.Filter;
import com.sankuai.avatar.resource.host.core.filter.OrderedFilter;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author qinwei05
 * @date 2023/2/26 17:30
 */

public class OrderedBaseFilterRule {

    private OrderedBaseFilterRule() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 资源池相关筛选主机(带默认优先级)
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> orderedBaseFilter(){
        return new OrderedFilter<>(baseFilter(), 1);
    }

    /**
     * 资源池相关筛选主机
     * 在每个条件判断中，我们首先判断请求参数是否为空，如果不为空，则判断是否满足该条件
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> baseFilter() {
        return (hosts, chain) -> {
            HostQueryRequestBO request = chain.getContext();

            List<HostAttributesBO> result = hosts.stream()
                    .filter(host -> isMatch(host, request))
                    .collect(Collectors.toList());
            return chain.filter(result);
        };
    }

    /**
     * 是否匹配条件
     *
     * @param host    主机
     * @param request 请求
     * @return boolean
     */
    private static boolean isMatch(HostAttributesBO host, HostQueryRequestBO request){
        boolean match = true;
        // 主机环境ENV过滤
        if (StringUtils.isNotBlank(request.getEnv())) {
            match = Objects.equals(host.getEnv(), request.getEnv());
        }
        // 主机SET过滤
        if (StringUtils.isNotBlank(request.getSet())) {
            match &= Objects.equals(host.getCell(), request.getSet());
        }
        if (StringUtils.isNotBlank(request.getCell())) {
            match &= Objects.equals(host.getCell(), request.getCell());
        }
        // 主机泳道过滤
        if (StringUtils.isNotBlank(request.getSwimlane())) {
            match &= Objects.equals(host.getSwimlane(), request.getSwimlane());
        }
        // 主机业务分组过滤
        if (StringUtils.isNotBlank(request.getGrouptags())) {
            match &= Objects.equals(host.getGrouptags(), request.getGrouptags());
        }
        // 主机上线时间区间过滤
        if (Objects.nonNull(host.getCtime())) {
            if (Objects.nonNull(request.getStartTime())) {
                match &= host.getCtime().after(request.getStartTime());
            }
            if (Objects.nonNull(request.getEndTime())) {
                match &= host.getCtime().before(request.getEndTime());
            }
        }
        return match;
    }
}
