package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.util.MgwUtils;
import com.sankuai.avatar.capacity.util.OctoUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.MapUtils;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class OctoHttpProviderIDCLengthProperty extends AbstractProperty<Number> {
    private String name = "octo下http协议节点机房数量";

    @Override
    public Number execute(Node node) {
        AppKeyNode appKeyNode = (AppKeyNode) node;
        // OCTO仅注册HTTP节点，或接入MGW，且调度策略为非同地域、非同机房，不再考虑同地域多机房部署
        boolean onlyHttp = MapUtils.isEmpty(node.getIdcOctoThriftProviderMap()) || MgwUtils.isMiddleWareMgw(appKeyNode);
        if (onlyHttp && OctoUtils.isNotAppkeyCityIdcStrategy(appKeyNode.getAppkey(), true)) {
            return node.getIdcHostMap().size();
        }
        return node.getIdcOctoHttpProviderMap().size();
    }
}
