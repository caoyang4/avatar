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
public class IsSingleHostRestartProperty extends AbstractProperty<Boolean> {
    @Override
    public String getName() {
        return RuleConstants.SINGLE_RESTART_RULE;
    }
    @Override
    public Boolean execute(Node node) {
        return node.isSingleHostRestart();
    }
}
