package com.sankuai.avatar.resource.host.core.filter.rule;

import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.core.filter.Filter;
import com.sankuai.avatar.resource.host.core.filter.OrderedFilter;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author qinwei05
 * @date 2023/2/26 17:30
 */

public class OrderedSortFilterRule {

    private OrderedSortFilterRule() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 主机排序(带默认优先级)
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> orderedSortFilter(){
        return new OrderedFilter<>(queryFilter(), 99);
    }

    /**
     * 主机排序
     *
     * @return {@link Filter}<{@link HostAttributesBO}>
     */
    public static Filter<HostAttributesBO> queryFilter() {
        return (hosts, chain) -> {
            HostQueryRequestBO request = chain.getContext();
            List<HostAttributesBO> result = sortHostAttributesBOList(hosts, request.getSort(), request.getSortBy());
            return chain.filter(result);
        };
    }

    /**
     * 主机列表排序（默认 OR 自定义）
     *
     * @param hostList 主机列表
     * @param sort     排序(逆序、顺序)
     * @param sortBy   排序字段
     * @return {@link List}<{@link HostAttributesBO}>
     */
    private static List<HostAttributesBO> sortHostAttributesBOList(List<HostAttributesBO> hostList, String sort, String sortBy){
        boolean isReverseOrder = Objects.equals("desc", sort);
        // 默认按照机房-主机名排序
        Comparator<HostAttributesBO> comparator = Comparator.comparing(HostAttributesBO::getIdc).thenComparing(HostAttributesBO::getName);

        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(sortBy)) {
            comparator = getComparator(sortBy, isReverseOrder);
        }
        return hostList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * 自定义比较器
     *
     * @param sortField    排序字段
     * @param reverseOrder 逆序、顺序
     * @return {@link Comparator}<{@link HostAttributesBO}>
     */
    private static Comparator<HostAttributesBO> getComparator(String sortField, boolean reverseOrder) {
        Comparator<HostAttributesBO> comparator = Comparator.comparing(host -> {
            try {
                Method method = HostAttributesBO.class.getMethod("get" + StringUtils.capitalize(sortField));
                return (Comparable) method.invoke(host);
            } catch (Exception e) {
                e.printStackTrace();
                // 如果无法获取指定属性值, 返回null表示这个对象不参与比较排序。
                return null;
            }
        });

        return reverseOrder ? comparator.reversed() : comparator;
    }
}
