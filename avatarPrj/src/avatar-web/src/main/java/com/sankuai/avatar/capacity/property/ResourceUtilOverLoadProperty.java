package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.util.DomUtils;
import com.sankuai.avatar.capacity.util.HulkUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-04-05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceUtilOverLoadProperty extends AbstractProperty<Boolean> {
    private String name = "资源利用率阈值";
    @Override
    public Boolean execute(Node node) {
        int idcCount = node.getIdcHostMap().size();
        double dangerPercent = 0.8;
        double resourceUtil = ((double)(idcCount-1))/idcCount * dangerPercent;
        String desc = String.format("当前资源利用率: %.2f，期望利用率：%.2f [机房数-1(%d) / 当前机房总数（%d）* 危险水位(%s)]", node.getResourceUtil().getValue(), resourceUtil, idcCount-1, idcCount, dangerPercent);
        boolean hit = node.getResourceUtil().getValue() <= resourceUtil;
        AppKeyNode appKeyNode = (AppKeyNode) node;
        if (!hit) {
            desc = "弹性信息：" + HulkUtils.getNoElasticMsg(appKeyNode.getAppkey(),appKeyNode.getSetName().getSetName())
                    + "且资源利用率过高，" + desc + "，可通过扩容、优化代码等措施降低";
        }
        setDescription(desc);
        setSuggestion(String.format("当前资源利用率: %.2f过高, 期望值: %.2f, 可通过扩容、优化代码等措施降低资源利用率", node.getResourceUtil().getValue(), resourceUtil));
        return hit || !isWeekOverLoad(appKeyNode.getAppkey(), resourceUtil);
    }

    private boolean isWeekOverLoad(String appkey, double resourceUtil){
        // 最近一周内3天利用率
        Map<String, Double> map = DomUtils.getWeekAppKeyUtilization(appkey);
        if (MapUtils.isEmpty(map) || map.size() < 3) {
            return false;
        }
        int count = 0;
        for (Double value : map.values()) {
            if (value > resourceUtil) {
                count++;
            }
        }
        return count >= 3;
    }
}
