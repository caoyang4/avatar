package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.rule.RuleConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 * @create 2022-08-17 21:04
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class IsParasitedHostProperty extends AbstractProperty<Boolean>{
    @Override
    public String getName() {
        return RuleConstants.PARASITED_RULE;
    }
    @Override
    public Boolean execute(Node node) {
        return node.isParasited();
    }

}
