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
public class AppkeyCapacityZeroCalculator extends BaseRuleCalculatorImpl {
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule("单机器，建议至少扩容一台机器", new Condition<Number>(HostLengthProperty.class, 1)),
//                new Rule(new Condition<>(RegionHostLengthProperty.class, Boolean.TRUE)),
                new Rule(new Condition<>(IsSingleHostRestartProperty.class, Boolean.FALSE)),
                new Rule(
                        new Condition<>(HasMiddleWareProperty.class, MiddleWareName.OCTO_TRIFT, CompareType.CONTAINS),
                        new Condition<Number>(OctoThriftProviderLengthProperty.class, 2, CompareType.LESS_THAN)
                ),
                new Rule(
                        new Condition<>(HasMiddleWareProperty.class, MiddleWareName.OCTO_HTTP, CompareType.CONTAINS),
                        new Condition<Number>(OctoHttpProviderLengthProperty.class, 2, CompareType.LESS_THAN)
                )
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.ZERO;
    }
}
