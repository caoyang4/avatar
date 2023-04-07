package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.constant.CompareType;
import com.sankuai.avatar.capacity.property.IsSetSingleIdcProperty;
import com.sankuai.avatar.capacity.property.ResourceUtilProperty;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-15
 **/
public class AppkeySetCapacityThreeCalculator extends BaseRuleCalculatorImpl {
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule(new Condition<>(IsSetSingleIdcProperty.class, Boolean.FALSE)),
                new Rule("多机，单机故障可自动摘除，资源利用率超过40%, 建议扩容或者优化代码等手段降低资源利用率", new Condition<>(ResourceUtilProperty.class, 0.4, CompareType.GREATER_THAN))
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.THREE;
    }
}
