package com.sankuai.avatar.resource.host.core.filter.rule;

import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.core.filter.Filter;
import com.sankuai.avatar.resource.host.core.filter.OrderedFilter;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author qinwei05
 * @date 2023/2/26 17:30
 */

public class OrderedQueryFilterRule {

    /**
     * ip查询类型
     */
    private static final String IP_QUERY_TYPE = "ip";

    /**
     * 主机名查询类型
     */
    private static final String NAME_QUERY_TYPE = "name";


    private OrderedQueryFilterRule() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 关键词筛选主机(带默认优先级)
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> orderedQueryFilter(){
        return new OrderedFilter<>(queryFilter(), 2);
    }

    /**
     * 关键词筛选主机
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> queryFilter() {
        return (hosts, chain) -> {
            HostQueryRequestBO request = chain.getContext();
            if (StringUtils.isBlank(request.getQuery())) {
                return chain.filter(hosts);
            }

            List<HostAttributesBO> result;

            if (Objects.equals(request.getBatchQueryType(), IP_QUERY_TYPE)) {
                // IP批量检索
                result = hosts.stream()
                        .filter(host -> Arrays.asList(request.getQuery().split(",")).contains(host.getIpLan()))
                        .collect(Collectors.toList());

            } else if (Objects.equals(request.getBatchQueryType(), NAME_QUERY_TYPE)) {
                // 主机名批量检索
                result = hosts.stream()
                        .filter(host -> Arrays.asList(request.getQuery().split(",")).contains(host.getName()))
                        .collect(Collectors.toList());
            } else {
                // 单个IP、Name、SN或机器配置检索
                Matcher matcher = extract(request.getQuery());
                if (matcher != null) {
                    String cpuCount = matcher.group(1);
                    String memSize = matcher.group(2);
                    result = hosts.stream()
                            .filter(host -> Objects.equals(cpuCount, String.valueOf(host.getCpuCount())))
                            .filter(host -> Objects.equals(memSize, String.valueOf(host.getMemSize())))
                            .collect(Collectors.toList());
                } else {
                    String query = String.valueOf(request.getQuery()).trim();
                    result = hosts.stream()
                            .filter(host -> host.getName().trim().contains(query)
                                    || host.getIpLan().trim().contains(query)
                                    || host.getIpLanV6().trim().contains(query)
                                    || host.getSn().trim().contains(query))
                            .collect(Collectors.toList());
                }
            }
            return chain.filter(result);
        };
    }

    /**
     * 提取符合正则表达式（X核XG）的字符串
     *
     * @param query 查询
     * @return {@link List}<{@link String}>
     */
    private static Matcher extract(String query) {
        if (StringUtils.isBlank(query)) {
            Pattern pattern = Pattern.compile("\\d+核/\\d+G");
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                return matcher;
            }
        }
        return null;
    }
}
