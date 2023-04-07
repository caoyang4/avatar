package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.rule.RuleConstants;

/**
 * @author caoyang
 * @create 2022-08-22 11:14
 */
public class IsJboxProperty extends AbstractProperty{
    @Override
    public String getName(){
        return RuleConstants.JBOX_RULE;
    }
    @Override
    public Object execute(Node node) {
        return node.isJbox();
    }
}
