package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.constant.IdcRegion;
import com.sankuai.avatar.capacity.constant.RegionIdcMap;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.node.OctoProvider;
import com.sankuai.avatar.capacity.util.OctoUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class OctoThriftProviderIDCLengthProperty extends AbstractProperty<Number> {
    private String name = "octo thrift协议节点机房数量";

    @Override
    public Number execute(Node node) {
        AppKeyNode appKeyNode = (AppKeyNode) node;
        if (OctoUtils.isNotAppkeyCityIdcStrategy(appKeyNode.getAppkey(), false)) {
            return node.getIdcOctoThriftProviderMap().size();
        }
        Map<String, List<OctoProvider>> idcThriftMap = node.getIdcOctoThriftProviderMap();
        Map<IdcRegion, List<String>> idcMap = new HashMap<>();
        for (String idc : idcThriftMap.keySet()) {
            // 主机idc不在Region/AZ信息接口，不参与容灾计算
            if (Objects.isNull(RegionIdcMap.getRegion(idc))) {continue;}
            IdcRegion idcRegion = Arrays.stream(IdcRegion.values()).filter(region -> Objects.equals(region, RegionIdcMap.getRegion(idc))).findFirst().orElse(IdcRegion.OTHERS);
            List<String> idcs = idcMap.getOrDefault(idcRegion, new ArrayList<>());
            idcs.add(idc);
            idcMap.put(idcRegion, idcs);
        }
        IdcRegion region = idcMap.keySet().stream().filter(idcRegion -> idcMap.get(idcRegion).size() < 2).findFirst().orElse(null);
        boolean res = Objects.isNull(region) || IdcRegion.OTHERS.equals(region);
        if (!res) {
            String idc = idcMap.get(region).get(0);
            setDescription(String.format("Octo Thrift节点仅部署在地域:%s，机房:%s，不满足同地域多机房，建议在%s其它机房注册thrift节点", region.name(), idc, region.name()));
        }
        // 仅有OTHERS的一个机房，不参与容灾计算
        return res ? 2 : 1;
    }
}
