package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.property.IsSetHostBalanceProperty;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-27 14:10
 */
public class AppkeySetCapacityFourCalculator extends BaseRuleCalculatorImpl{
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule(" SET间部署不均衡", new Condition<>(IsSetHostBalanceProperty.class, Boolean.FALSE))
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.FOUR;
    }
}
