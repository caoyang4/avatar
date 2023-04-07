package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jie.li.sh
 * @create 2020-04-06
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceUtilProperty extends AbstractProperty<Number> {
    private String name = "资源利用率";
    @Override
    public String getDescription() {
        return "资源利用率低于40%，满足set的4级容灾要求";
    }

    @Override
    public Double execute(Node node) {
        return node.getResourceUtil().getValue();
    }
}
