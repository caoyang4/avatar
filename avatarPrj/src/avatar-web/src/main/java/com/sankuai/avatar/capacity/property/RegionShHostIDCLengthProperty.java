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
public class RegionShHostIDCLengthProperty extends AbstractProperty<Number> {

    @Override
    public String getSuggestion() {
        return "建议在上海侧其它机房扩容";
    }

    @Override
    public String getName() {
        return "上海侧机房数";
    }

    @Override
    public Number execute(Node node) {
        AppKeyNode appKeyNode = (AppKeyNode) node;
        // 接入 mgw 或 octo，只需满足多机房
        List<MiddleWareName> middleWareNames = node.getMiddleWareInfoList().stream().filter(MiddleWare::getUsed).map(MiddleWare::getMiddleWareName).collect(Collectors.toList());
        boolean isOcto = middleWareNames.contains(MiddleWareName.OCTO_HTTP) || middleWareNames.contains(MiddleWareName.OCTO_TRIFT);
        if (MgwUtils.isMiddleWareMgw(appKeyNode) || isOcto) {
            return node.getIdcHostMap().size();
        }
        List<String> shIdc = new ArrayList<>();
        for (String idc: node.getIdcHostMap().keySet()) {
            if (RegionIdcMap.getRegion(idc) == IdcRegion.SH){
                shIdc.add(idc);
            }
        }
        return shIdc.size();
    }
}
