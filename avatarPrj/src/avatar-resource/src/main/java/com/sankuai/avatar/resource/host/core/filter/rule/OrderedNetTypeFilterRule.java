package com.sankuai.avatar.resource.host.core.filter.rule;

import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.core.filter.Filter;
import com.sankuai.avatar.resource.host.core.filter.OrderedFilter;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qinwei05
 * @date 2023/2/26 17:30
 */

public class OrderedNetTypeFilterRule {

    /**
     * 双栈网络
     */
    private static final String DUAL_STACK = "IPv4+IPv6";

    /**
     * ipv4
     */
    private static final String IPV4 = "IPv4";

    /**
     * ipv6
     */
    private static final String IPV6 = "IPv6";

    /**
     * MGW ipv4
     */
    private static final String LADDR_GROUP = "laddr_group";

    /**
     * MGW ipv6
     */
    private static final String LADDR6_GROUP = "laddr6_group";


    private OrderedNetTypeFilterRule() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 网络类型相关筛选主机(带默认优先级)
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> orderedNetTypeFilter(){
        return new OrderedFilter<>(netTypeFilter(), 4);
    }

    /**
     * 网络类型相关筛选主机（"IPv4", "IPv6", "IPv4+IPv6"）
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> netTypeFilter() {
        return (hosts, chain) -> {
            HostQueryRequestBO request = chain.getContext();
            // IPv4、IPv6、双栈网络
            List<HostAttributesBO> result;
            if (StringUtils.isNotBlank(request.getNetType())){
                if (Arrays.asList(IPV4, IPV6, DUAL_STACK).contains(request.getNetType())) {
                    result = hosts.stream()
                            .filter(host -> Objects.equals(request.getNetType(), host.getNetType()))
                            .collect(Collectors.toList());
                    return chain.filter(result);
                }
            } else if (StringUtils.isNotBlank(request.getRsNetType())) {
                if (Arrays.asList(LADDR_GROUP, LADDR6_GROUP).contains(request.getRsNetType())) {
                    Map<String, List<String>> laddrGroupMap = new HashMap<>(2);
                    laddrGroupMap.put(LADDR_GROUP, Arrays.asList(IPV4, DUAL_STACK));
                    laddrGroupMap.put(LADDR6_GROUP, Arrays.asList(IPV6, DUAL_STACK));
                    result = hosts.stream()
                            .filter(host -> laddrGroupMap.getOrDefault(request.getRsNetType(), new ArrayList<>()).contains(host.getNetType()))
                            .collect(Collectors.toList());
                    return chain.filter(result);
                }
            } else {
                return chain.filter(hosts);
            }
            return chain.filter(hosts);
        };
    }
}
