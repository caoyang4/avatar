package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.Node;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/

@Getter
@Setter
public abstract class AbstractProperty<T> implements Property<T> {
    private String name;

    private String description = "";

    private String suggestion = "";

    private Boolean result;

    /**
     * 计算
     * @param node 计算节点
     * @return obj
     */
    @Override
    abstract public T execute(Node node);

}
