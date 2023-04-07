package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.property.HostIDCBalanceProperty;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-09
 **/
public class AppkeyCapacityFourCalculator extends BaseRuleCalculatorImpl {
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule(new Condition<>(HostIDCBalanceProperty.class, Boolean.FALSE))
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.FOUR;
    }
}
