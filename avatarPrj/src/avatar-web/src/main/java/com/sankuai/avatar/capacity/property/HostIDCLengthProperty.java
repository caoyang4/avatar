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
public class HostIDCLengthProperty extends AbstractProperty<Number> {
    private String name = "主机机房数量";

    private String suggestion = "当前AppKey不满足同地域多机房部署，建议在其它机房扩容至少一台机器";

    @Override
    public Number execute(Node node) {
        if (node.getIdcHostMap() == null) {
            return 0;
        }
        return node.getIdcHostMap().size();
    }
}
