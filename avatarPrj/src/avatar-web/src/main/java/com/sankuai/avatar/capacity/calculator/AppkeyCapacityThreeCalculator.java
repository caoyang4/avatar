package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.property.ResourceUtilOverLoadProperty;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-09
 **/
public class AppkeyCapacityThreeCalculator extends BaseRuleCalculatorImpl {
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule(new Condition<>(ResourceUtilOverLoadProperty.class, Boolean.FALSE))
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.THREE;
    }
}
