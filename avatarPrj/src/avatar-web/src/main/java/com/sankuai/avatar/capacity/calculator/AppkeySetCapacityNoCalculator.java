package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.property.IsSpecialOwtProperty;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-11 15:12
 */
public class AppkeySetCapacityNoCalculator extends BaseRuleCalculatorImpl{

    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule(new Condition<>(IsSpecialOwtProperty.class, Boolean.TRUE))
//                new Rule("avatar不予计算该set链路的容灾等级", new Condition<>(IsSpecialSetProperty.class, Boolean.TRUE))
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.NO;
    }
}
