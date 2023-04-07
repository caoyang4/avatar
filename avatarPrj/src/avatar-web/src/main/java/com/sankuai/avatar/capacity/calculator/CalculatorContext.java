package com.sankuai.avatar.capacity.calculator;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.node.Node;
import lombok.Data;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-16
 **/
@Data
public class CalculatorContext {
    Node node;
    List<CalculatorResult> calculatorResultLog;

    public CalculatorContext(Node node) {
        this.node = node;
        this.calculatorResultLog = Lists.newArrayList();
    }
}
