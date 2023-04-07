package com.sankuai.avatar.resource.host.core.filter.rule;

import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.common.utils.SpringContextUtil;
import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.core.filter.Filter;
import com.sankuai.avatar.resource.host.core.filter.OrderedFilter;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import com.sankuai.avatar.resource.octo.OctoResource;
import com.sankuai.avatar.resource.octo.model.OctoNodeStatusProviderBO;
import com.sankuai.avatar.resource.octo.request.OctoNodeStatusQueryRequestBO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qinwei05
 * @date 2023/2/26 17:30
 */

public class OrderedOctoDisabledFilterRule {

    private OrderedOctoDisabledFilterRule() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * OCTO禁用节点相关筛选主机(带默认优先级)
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> orderedOctoDisabledFilter(){
        return new OrderedFilter<>(octoDisabledFilter(), 5);
    }

    /**
     * OCTO禁用节点相关筛选主机
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> octoDisabledFilter() {
        return (hosts, chain) -> {
            HostQueryRequestBO request = chain.getContext();
            if (Boolean.FALSE.equals(request.getOctoDisabled()) || request.getOctoDisabled() == null){
                return chain.filter(hosts);
            }
            // 获取全部的http、thrift类型禁用节点
            Set<String> octoDisabledNodeIpSet = getAppkeyOctoDisabledNodeIpSet(request.getAppkey(), request.getEnv());
            // 主机列表中筛选出禁用节点
            List<HostAttributesBO> result = hosts.stream()
                    .filter(host -> octoDisabledNodeIpSet.contains(host.getIpLan()))
                    .collect(Collectors.toList());

            return chain.filter(result);
        };
    }

    /**
     * 得到appkey octo禁用节点ip
     *
     * @param appkey appkey
     * @param env    env
     * @return {@link List}<{@link String}>
     */
    private static Set<String> getAppkeyOctoDisabledNodeIpSet(String appkey, String env) {
        List<String> ipList = new ArrayList<>();
        OctoResource octoResource = SpringContextUtil.getBean(OctoResource.class);
        OctoNodeStatusQueryRequestBO queryRequest = OctoNodeStatusQueryRequestBO.builder()
                .appkey(appkey)
                // -1, 查出全部禁用节点（不分页）
                .pageSize(-1)
                .status(4)
                .build();
        // 1为http类型，2为thrift类型
        for (Integer type: Arrays.asList(1, 2)){
            queryRequest.setType(type);
            List<String> octoNodeIpList = octoResource.getAppkeyOctoNodeStatus(
                    queryRequest, EnvEnum.valueOf(env.toUpperCase())).getProviders()
                    .stream()
                    .map(OctoNodeStatusProviderBO::getIp)
                    .collect(Collectors.toList());
            ipList.addAll(octoNodeIpList);
        }
        return new HashSet<>(ipList);
    }
}
