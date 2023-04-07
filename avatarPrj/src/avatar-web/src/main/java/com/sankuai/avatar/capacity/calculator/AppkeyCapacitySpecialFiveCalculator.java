package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.property.IsElasticProperty;
import com.sankuai.avatar.capacity.property.IsNestProperty;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-17
 **/
public class AppkeyCapacitySpecialFiveCalculator extends BaseRuleCalculatorImpl {
    /**
     * 0 -> 5
     * @return fast pass 5
     */
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule("该服务已接入Nest，判定容灾等级为5", new Condition<>(IsNestProperty.class, Boolean.TRUE))
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.FIVE;
    }
}
