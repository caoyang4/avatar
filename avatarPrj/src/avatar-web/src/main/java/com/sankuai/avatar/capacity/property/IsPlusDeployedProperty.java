package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jie.li.sh
 * @create 2020-04-17
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class IsPlusDeployedProperty extends AbstractProperty<Boolean> {
    @Override
    public Boolean execute(Node node) {
        return node.isPlusDeployed();
    }
}
