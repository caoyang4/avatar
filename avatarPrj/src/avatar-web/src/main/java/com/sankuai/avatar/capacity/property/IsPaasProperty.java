package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.rule.RuleConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class IsPaasProperty extends AbstractProperty<Boolean> {
    @Override
    public String getName() {
        return RuleConstants.PAAS_RULE;
    }
    @Override
    public Boolean execute(Node node) {
        return node.isPaas() && !node.isCalculate();
    }
}
