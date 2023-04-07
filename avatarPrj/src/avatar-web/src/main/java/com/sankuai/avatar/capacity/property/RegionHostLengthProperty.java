package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.constant.IdcRegion;
import com.sankuai.avatar.capacity.node.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxinli
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegionHostLengthProperty extends AbstractProperty<Boolean> {

    /**
     * 计算
     *
     * @param node 计算节点
     * @return obj
     */
    @Override
    public Boolean execute(Node node) {
        List<IdcRegion> idcRegionList = new ArrayList<>();
        idcRegionList.add(IdcRegion.BJ);
        idcRegionList.add(IdcRegion.SH);
        for (IdcRegion region: idcRegionList) {
            int count = (int) node.getHostList().stream().filter(p->p.getRegion() == region).count();
            if (count == 1){
                setSuggestion(String.format("服务在%s侧的机器数量为%s, 建议在%s的其它机房扩容", region, count, region));
                setName(String.format("%s侧单点", region));
                return true;
            }
        }
        return false;
    }
}
