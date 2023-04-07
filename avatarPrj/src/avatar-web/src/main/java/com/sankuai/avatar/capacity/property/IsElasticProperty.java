package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.rule.RuleConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jie.li.sh
 * @create 2020-04-17
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class IsElasticProperty extends AbstractProperty<Boolean> {
    @Override
    public String getName() {
        return RuleConstants.ELASTIC_RULE;
    }
    @Override
    public Boolean execute(Node node) {
        return node.isElastic();
    }
}
