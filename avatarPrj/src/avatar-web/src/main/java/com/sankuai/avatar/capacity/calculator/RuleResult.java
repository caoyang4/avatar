package com.sankuai.avatar.capacity.calculator;

import lombok.Data;

import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-04-16
 **/
@Data
public class RuleResult {
    String description;
    Boolean hit;
    List<ConditionResult> conditionList;
    public Boolean isHit() {
        return getHit().equals(true);
    }
}
