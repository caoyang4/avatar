package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.property.*;
import com.sankuai.avatar.capacity.rule.Condition;
import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * 容灾等级：不计算
 * @author Jie.li.sh
 * @create 2020-04-09
 **/
public class AppkeyCapacityNoCalculator extends BaseRuleCalculatorImpl {
    @Override
    public List<Rule> getRuleList() {
        return Lists.newArrayList(
                new Rule(new Condition<>(IsSpecialOwtProperty.class, Boolean.TRUE)),
                new Rule("该服务属于Jbox服务，avatar不予计算容灾等级", new Condition<>(IsJboxProperty.class, Boolean.TRUE)),
                new Rule("该服务属于宿主机服务，avatar不予计算容灾等级", new Condition<>(IsParasitedHostProperty.class, Boolean.TRUE)),
                new Rule("该服务属于paas服务，avatar不予计算容灾等级", new Condition<>(IsPaasProperty.class, Boolean.TRUE)),
                new Rule(new Condition<Number>(HostLengthProperty.class, 0)),
                new Rule(new Condition<>(HostUnknownProperty.class, Boolean.TRUE)),
                new Rule(new Condition<>(IsOnlyOtherRegionProperty.class, Boolean.TRUE)),
                new Rule(new Condition<>(HostUndeployProperty.class, Boolean.TRUE))
        );
    }

    @Override
    public CapacityLevel getCapacityLevel() {
        return CapacityLevel.NO;
    }
}
