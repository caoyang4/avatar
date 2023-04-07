package com.sankuai.avatar.capacity.calculator;

import com.sankuai.avatar.capacity.constant.CapacityLevel;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-09
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorResult {
    Boolean isCalculate;
    CapacityLevel capacityLevel;
    Boolean hit;
    RuleResult hitRuleResult;
    List<RuleResult> ruleResultList;
    CalculatorContext calculatorContext;
    /**
     * 判断标准
     */
    String reason;
    /**
     * 匹配项结果描述
     */
    String description;
    /**
     * 建议
     */
    String suggestion;
    /**
     * appKeyNode
     */
    AppKeyNode appKeyNode;

    public boolean isHit() {
        return getHit().equals(true);
    }
}
