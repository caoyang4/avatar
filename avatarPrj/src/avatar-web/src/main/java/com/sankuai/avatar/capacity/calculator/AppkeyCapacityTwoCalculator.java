package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.constant.CompareType;
import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.property.*;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-09
 **/
public class AppkeyCapacityTwoCalculator extends BaseRuleCalculatorImpl {
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule(
                        "多机但是单机房部署，建议在其它同地域机房扩容至少一台机器",
                        new Condition<>(HostIDCLengthProperty.class, 1)
                ),
                new Rule(
                        "多机部署但北京侧单机房部署，建议在北京侧其它机房部署机器",
                        new Condition<>(RegionBjHostIDCLengthProperty.class, 1)
                ),
                new Rule(
                        "多机部署但上海侧单机房部署，建议在上海侧其它机房部署机器",
                        new Condition<>(RegionShHostIDCLengthProperty.class, 1)
                ),
                new Rule(
                        "Octo Thrift节点不满足同地域多机房，建议在其它机房注册thrift节点",
                        new Condition<>(HasMiddleWareProperty.class, MiddleWareName.OCTO_TRIFT, CompareType.CONTAINS),
                        new Condition<>(OctoThriftProviderIDCLengthProperty.class, 1)
                ),
                new Rule(
                        "Octo http节点不满足同地域多机房，建议在其它机房注册http节点",
                        new Condition<>(HasMiddleWareProperty.class, MiddleWareName.OCTO_HTTP, CompareType.CONTAINS),
                        new Condition<>(OctoHttpProviderIDCLengthProperty.class, 1)
                )
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.TWO;
    }
}
