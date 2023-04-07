package com.sankuai.avatar.capacity.property;

import com.google.common.collect.ImmutableSet;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Node;

import java.util.Set;

/**
 * @author caoyang
 * @create 2023-01-11 15:13
 */
public class IsSpecialSetProperty extends AbstractProperty<Boolean> {

    static final Set<String> SPECIAL_SET = ImmutableSet.of(
            "banma-exp",
            "waimai-south-experiment",
            "waimai-north-experiment"
    );

    @Override
    public Boolean execute(Node node) {
        AppKeyNode appKeyNode = (AppKeyNode) node;
        if (Boolean.TRUE.equals(appKeyNode.getSetName().isSet())) {
            return SPECIAL_SET.contains(appKeyNode.getSetName().getSetName());
        }
        return false;
    }

}
