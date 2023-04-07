package com.sankuai.avatar.resource.host;

import com.sankuai.avatar.resource.TestBase;
import com.sankuai.avatar.resource.host.bo.HostAttributesBO;
import com.sankuai.avatar.resource.host.core.filter.Filter;
import com.sankuai.avatar.resource.host.core.filter.Handler;
import com.sankuai.avatar.resource.host.core.filter.OrderedFilter;
import com.sankuai.avatar.resource.host.request.HostQueryRequestBO;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 核心过滤测试
 *
 * @author qinwei05
 * @date 2023/02/24
 */
public class CoreFilterTest extends TestBase {

    @Test
    public void testFilter() {

        Filter<HostAttributesBO> stringFilter = (e, chain)->{
            List<HostAttributesBO> collect = e.stream().filter((host) -> host.getName().contains("b")).collect(Collectors.toList());
            return chain.filter(collect);
        };

        Filter<HostAttributesBO> stringFilter1 = (e,chain)->{
            List<HostAttributesBO> collect = e.stream().filter((host) -> host.getName().startsWith("q")).collect(Collectors.toList());
            return chain.filter(collect);
        };

        Filter<HostAttributesBO> stringFilter2 = (e,chain)->{
            List<HostAttributesBO> collect = e.stream().filter((host) -> host.getName().endsWith("n")).collect(Collectors.toList());
            return chain.filter(collect);
        };

        OrderedFilter<HostAttributesBO> stringOrderedFilter = new OrderedFilter<>(stringFilter2, 1);

        HostAttributesBO hostAttributesBO1 = HostAttributesBO.builder().name("qinbinbin").build();
        HostAttributesBO hostAttributesBO2 = HostAttributesBO.builder().name("n").build();
        HostAttributesBO hostAttributesBO3 = HostAttributesBO.builder().name("qin").build();
        HostAttributesBO hostAttributesBO4 = HostAttributesBO.builder().name("bin").build();

        List<HostAttributesBO> objects = new ArrayList<>(Arrays.asList(hostAttributesBO1, hostAttributesBO2, hostAttributesBO3, hostAttributesBO4));

        HostQueryRequestBO context = HostQueryRequestBO.builder().build();
        List<Filter<HostAttributesBO>> filters = new ArrayList<>(Arrays.asList(
                stringFilter, stringFilter1, stringOrderedFilter
        ));

        List<HostAttributesBO> result = Handler.init(filters).handle(objects, context);
        Assert.assertEquals(1, result.size());
    }
}
