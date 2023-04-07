package com.sankuai.avatar.capacity.property;

import com.google.common.collect.Lists;
import com.sankuai.avatar.capacity.constant.WhiteApp;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.node.WhiteInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-04-06
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class WhiteListProperty extends AbstractProperty<List<WhiteApp>> {
    private String name = "运营白名单";

    @Override
    public List<WhiteApp> execute(Node node) {
        if (node.getWhiteInfoList() == null){
            return Lists.newArrayList();
        }
        return node.getWhiteInfoList().stream().map(WhiteInfo::getWhiteApp).collect(Collectors.toList());
    }
}
