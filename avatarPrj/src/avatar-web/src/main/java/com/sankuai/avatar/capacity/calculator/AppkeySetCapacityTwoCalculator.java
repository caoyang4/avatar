package com.sankuai.avatar.capacity.calculator;

import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-27 14:09
 */
public class AppkeySetCapacityTwoCalculator extends BaseRuleCalculatorImpl{
    @Override
    public List<Rule> getRuleList() {
        return null;
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.TWO;
    }
}
