package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class OctoHttpProviderLengthProperty extends AbstractProperty<Number> {
    private String name = "octo下http协议节点数量";

    @Override
    public Number execute(Node node) {
        return node.getOctoHttpProviderList().size();
    }
}
