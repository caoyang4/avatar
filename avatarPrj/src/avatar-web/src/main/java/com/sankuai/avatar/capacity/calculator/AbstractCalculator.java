package com.sankuai.avatar.capacity.calculator;

import com.sankuai.avatar.capacity.rule.Rule;
import lombok.Data;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-09
 **/

@Data
public abstract class AbstractCalculator implements Calculator {
    private List<Rule> ruleList;
    private Calculator nextCalculator;

    /**
     * 各容灾等级的计算方式
     * @param calculatorContext 计算对象，传递上下文
     * @return 计算结果
     */
    @Override
    public abstract CalculatorResult calculate(CalculatorContext calculatorContext) throws Exception;

    @Override
    public void setNextCalculator(Calculator calculator) {
        this.nextCalculator = calculator;
    }
}
