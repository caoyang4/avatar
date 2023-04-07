package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.constant.IdcRegion;
import com.sankuai.avatar.capacity.constant.RegionIdcMap;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Host;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.util.HulkUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class HostIDCBalanceProperty extends AbstractProperty<Boolean> {
    private String name = "机房均衡评估";

    /**
     * max - min <= int( avg / 10) + 1，即当服务的 max 于 min 的差值，不大于 (avg /10) + 1差值区间，则认为机房均衡
     * @param node 计算节点
     * @return boolean
     */
    @Override
    public Boolean execute(Node node) {
        boolean isHit;
        for (Map<String, List<Host>> idcHostMap : getRegionIdcMapList(node)) {
            List<Integer> idcSizeList = idcHostMap.values().stream().map(List::size).collect(Collectors.toList());
            int maxIdcCount = Collections.max(idcSizeList);
            int minIdcCount = Collections.min(idcSizeList);
            double avgIdcCount = idcHostMap.values().stream().map(List::size).mapToDouble(val -> val).average().orElse(0.0);
            String description = String.format("(最大机房的机器数量 %d - 最小机房的机器数量 %d) <= 机房平均机器数量 %.2f / 10 + 1",
                    maxIdcCount, minIdcCount, avgIdcCount);
            isHit = (maxIdcCount-minIdcCount) <= (int)(avgIdcCount/10)+1;
            if (!isHit) {
                AppKeyNode appKeyNode = (AppKeyNode) node;
                String elasticMsg = HulkUtils.getNoElasticMsg(appKeyNode.getAppkey(), appKeyNode.getSetName().getSetName());
                if (StringUtils.isNotEmpty(elasticMsg)) {
                    elasticMsg = "弹性信息：" + elasticMsg + "且";
                }
                String tips = elasticMsg + description + "，不满足机房均衡判断，判断容灾等级为4";
                setSuggestion(tips);
                setDescription(tips);
                return false;
            }
        }
        setDescription("各地域满足机房均衡判断，判断容灾等级为5");
        return true;
    }

    private List<Map<String, List<Host>>> getRegionIdcMapList(Node node){
        Map<String, List<Host>> idcHostMap = node.getIdcHostMap();
        List<Map<String, List<Host>>> regionIdcMapList = new ArrayList<>();
        for (IdcRegion region : IdcRegion.values()) {
            Map<String, List<Host>> map = idcHostMap.keySet().stream().filter(
                    idc -> region.equals(RegionIdcMap.getRegion(idc))
                    ).collect(Collectors.toMap(String::valueOf, idcHostMap::get));
            if (MapUtils.isNotEmpty(map)) {
                regionIdcMapList.add(map);
            }
        }
        return regionIdcMapList;
    }
}
