package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.property.HostLengthProperty;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-15
 **/
public class AppkeySetCapacityZeroCalculator extends BaseRuleCalculatorImpl {
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule("单机器, 建议扩容至少一台机器", new Condition<Number>(HostLengthProperty.class, 1))
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.ZERO;
    }
}
