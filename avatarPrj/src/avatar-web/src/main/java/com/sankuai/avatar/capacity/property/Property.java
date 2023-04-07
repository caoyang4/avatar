package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.Node;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
public interface Property<T> {
    /**
     * 属性名称
     * @return str
     */
    String getName();

    /**
     * 描述
     * @return str
     */
    String getDescription();

    /**
     * 属性实现建议
     * @return str
     */
    String getSuggestion();

    /**
     *  触发计算返回结果
     * @param node 计算节点
     * @return <T>
     */
    T execute(Node node);
}
