package com.sankuai.avatar.capacity.calculator;

import com.sankuai.avatar.capacity.property.Property;
import com.sankuai.avatar.capacity.rule.Condition;
import lombok.Data;

/**
 * @author Jie.li.sh
 * @create 2020-04-16
 **/
@Data
public class ConditionResult {
    Boolean hit;
    Condition condition;
    Property property;
    /**
     * 描述
     */
    String description;
    /**
     * 建议
     */
    String suggestion;
}
