package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.constant.IdcRegion;
import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.constant.RegionIdcMap;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.MiddleWare;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.util.MgwUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class RegionBjHostIDCLengthProperty extends AbstractProperty<Number> {

    @Override
    public Number execute(Node node) {
        AppKeyNode appKeyNode = (AppKeyNode) node;
        // 接入 mgw 或者 OCTO，只需满足多机房
        List<MiddleWareName> middleWareNames = node.getMiddleWareInfoList().stream().filter(MiddleWare::getUsed).map(MiddleWare::getMiddleWareName).collect(Collectors.toList());
        boolean isOcto = middleWareNames.contains(MiddleWareName.OCTO_HTTP) || middleWareNames.contains(MiddleWareName.OCTO_TRIFT);
        if (MgwUtils.isMiddleWareMgw(appKeyNode) || isOcto) {
            return node.getIdcHostMap().size();
        }
        List<String> bjIdc = new ArrayList<>();
        for (String idc: node.getIdcHostMap().keySet()) {
            if (RegionIdcMap.getRegion(idc) == IdcRegion.BJ){
                bjIdc.add(idc);
            }
        }
        return bjIdc.size();
    }
}
