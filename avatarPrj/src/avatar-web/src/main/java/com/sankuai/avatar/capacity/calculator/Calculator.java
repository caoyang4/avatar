package com.sankuai.avatar.capacity.calculator;

import com.sankuai.avatar.capacity.rule.Rule;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-07
 **/
public interface Calculator {
    /**
     * @return 获取rule
     */
    List<Rule> getRuleList();
    /**
     * 不同等级的计算方式
     * @param calculatorContext 计算对象
     * @return 计算结果
     */
    CalculatorResult calculate(CalculatorContext calculatorContext) throws Exception;

    /**
     * 下一层计算
     * @param calculator next
     */
    void setNextCalculator(Calculator calculator);

    /**
     * 获取下一层计算
     * @return next calculator
     */
    Calculator getNextCalculator();
}
