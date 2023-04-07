package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.property.IsSetHostBalanceProperty;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-15
 **/
public class AppkeySetCapacityFiveCalculator extends BaseRuleCalculatorImpl {
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule("SET间部署均衡", new Condition<>(IsSetHostBalanceProperty.class, Boolean.TRUE))
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.FIVE;
    }
}
