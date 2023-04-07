package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.constant.CompareType;
import com.sankuai.avatar.capacity.constant.WhiteApp;
import com.sankuai.avatar.capacity.property.AutoMigrationProperty;
import com.sankuai.avatar.capacity.property.HostLengthProperty;
import com.sankuai.avatar.capacity.property.WhiteListProperty;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-09
 **/
public class AppkeyCapacityOneCalculator extends BaseRuleCalculatorImpl {
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule(
                        new Condition<>(HostLengthProperty.class, 1, CompareType.GREATER_THAN),
                        new Condition<>(AutoMigrationProperty.class, false)
                )
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.ONE;
    }
}
