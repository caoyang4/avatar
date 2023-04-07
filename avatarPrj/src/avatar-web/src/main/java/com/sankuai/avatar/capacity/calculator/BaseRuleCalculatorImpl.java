package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.rule.Rule;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-04-09
 **/
public abstract class BaseRuleCalculatorImpl extends AbstractCalculator {
    /**
     * 匹配规则
     * @return 返回规则list
     */
    @Override
    public abstract List<Rule> getRuleList();

    /**
     * 容灾等级
     * @return 返回容灾等级
     */
    public abstract CapacityLevel getCapacityLevel();

    private void before(CalculatorContext calculatorContext) {};

    private void after(CalculatorContext calculatorContext) {};

    private CalculatorResult process(Node node) throws Exception {
        CalculatorResult calculatorResult = new CalculatorResult();
        calculatorResult.setHit(false);
        calculatorResult.setCapacityLevel(getCapacityLevel());
        List<RuleResult> ruleResultList = Lists.newArrayList();
        List<Rule> ruleList = getRuleList();
        if (ruleList == null) {
            return calculatorResult;
        }
        for (Rule rule: ruleList) {
            try {
                RuleResult ruleResult = rule.hit(node);
                ruleResultList.add(ruleResult);
                if (ruleResult.isHit()) {
                    calculatorResult.setHit(true);
                    calculatorResult.setCapacityLevel(getCapacityLevel());
                    calculatorResult.setHitRuleResult(ruleResult);
                    String suggestion = ruleResult.getConditionList().stream().filter(conditionResult -> StringUtils.isNotBlank(conditionResult.getSuggestion())).map(ConditionResult::getSuggestion).collect(Collectors.joining(";"));
                    calculatorResult.setSuggestion(StringUtils.isNotBlank(suggestion) ? suggestion : "");
                    String desc = ruleResult.getConditionList().stream().map(ConditionResult::getDescription).collect(Collectors.joining(";"));
                    String description = StringUtils.isNotBlank(rule.getReason()) ? rule.getReason() : StringUtils.isNotBlank(desc) ? desc : "";
                    calculatorResult.setDescription(description);
                    calculatorResult.setReason(rule.getReason((AppKeyNode) node));
                    calculatorResult.setAppKeyNode((AppKeyNode) node);
                    if (StringUtils.isEmpty(rule.getReason())){
                        calculatorResult.setReason(description);
                    }
                    break;
                } else {

                }
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        calculatorResult.setRuleResultList(ruleResultList);
        return calculatorResult;
    };

    @Override
    public final CalculatorResult calculate(CalculatorContext calculatorContext) throws Exception {
        Node node = calculatorContext.getNode();
        before(calculatorContext);
        CalculatorResult calculatorResult = process(node);
        after(calculatorContext);
        assert calculatorResult != null;
        if (calculatorResult.getHit().equals(Boolean.TRUE)) {
            return calculatorResult;
        }
        Calculator nextCalculator = getNextCalculator();
        if (nextCalculator == null) {
            return null;
        }
        calculatorContext.getCalculatorResultLog().add(calculatorResult);
        return getNextCalculator().calculate(calculatorContext);
    }
}
