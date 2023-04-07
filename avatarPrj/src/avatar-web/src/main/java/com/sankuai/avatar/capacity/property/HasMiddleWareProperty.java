package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.node.MiddleWare;
import com.sankuai.avatar.capacity.node.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-04-10
 **/
@Getter
@Setter
public class HasMiddleWareProperty<T> extends AbstractProperty<List<MiddleWareName>> {
    private String name = "接入中间件";
    @Override
    public List<MiddleWareName> execute(Node node) {
        return node.getMiddleWareInfoList().stream().filter(MiddleWare::getUsed).map(MiddleWare::getMiddleWareName).collect(Collectors.toList());
    }
}
