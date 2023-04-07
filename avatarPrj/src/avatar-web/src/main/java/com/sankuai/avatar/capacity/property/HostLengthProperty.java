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
public class HostLengthProperty extends AbstractProperty<Number> {
    private String name = "主机数量";

    @Override
    public Number execute(Node node) {
        int size = node.getHostList().size();
        if (size == 0) {
            setDescription("该服务下机器数量为0，avatar不予计算容灾等级");
        }
        return size;
    }
}
