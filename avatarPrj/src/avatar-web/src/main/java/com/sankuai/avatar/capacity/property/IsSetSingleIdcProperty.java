package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.util.DayuUtils;

/**
 * @author caoyang
 * @create 2022-12-27 14:16
 */
public class IsSetSingleIdcProperty extends AbstractProperty<Boolean>{

    @Override
    public String getName() {
        return "SET内闭环部署，仅部署单个机房";
    }

    @Override
    public Boolean execute(Node node) {
        AppKeyNode appKeyNode = (AppKeyNode) node;
        String idc = DayuUtils.getSetIdc(appKeyNode.getSetName().getSetName());
        if (node.getIdcHostMap().keySet().size() > 1 || !node.getIdcHostMap().containsKey(idc)) {
            setDescription(String.format("该set链路应仅部署在对应机房: %s，不满足SET内闭环部署", idc));
            return false;
        }
        return true;
    }
}
