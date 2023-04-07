package com.sankuai.avatar.capacity.core;

import com.sankuai.avatar.capacity.calculator.Calculator;
import com.sankuai.avatar.capacity.calculator.CalculatorContext;
import com.sankuai.avatar.capacity.calculator.CalculatorResult;
import com.sankuai.avatar.capacity.node.Node;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-17
 **/
@Slf4j
@Getter
@Service
public abstract class AbstractCalculate implements Calculate {
    protected Calculator calculator = registerCalculator();

    private Calculator registerCalculator() {
        List<Calculator> calculatorList = getCalculatorList();
        Calculator nextCalculator = null;
        Calculator currentCalculator = null;
        Iterator<Calculator> calculatorIterator = calculatorList.listIterator();
        while (calculatorIterator.hasNext()) {
            if (currentCalculator == null) {
                currentCalculator = calculatorIterator.next();
                continue;
            }
            nextCalculator = calculatorIterator.next();
            currentCalculator.setNextCalculator(nextCalculator);
            currentCalculator = nextCalculator;
        }
        return calculatorList.get(0);
    }

    /**
     * 初始化计算列表
     * @return list
     */
    protected abstract List<Calculator> getCalculatorList();


    public CalculatorResult calculateAppkeyNode(Node node) throws Exception {
        CalculatorContext calculatorContext = new CalculatorContext(node);
        return calculator.calculate(calculatorContext);
    }
}
