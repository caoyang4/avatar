package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.constant.IdcRegion;
import com.sankuai.avatar.capacity.constant.RegionIdcMap;
import com.sankuai.avatar.capacity.node.Node;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2023-02-09 14:26
 */
public class IsOnlyOtherRegionProperty  extends AbstractProperty<Boolean> {

    private String name = "单机房，且该机房非北上地域部署服务";

    @Override
    public Boolean execute(Node node) {
        if (MapUtils.isEmpty(node.getIdcHostMap())) {
            return false;
        }
        int size = node.getIdcHostMap().size();
        if (size == 1) {
            String idc = new ArrayList<>(node.getIdcHostMap().keySet()).get(0);
            IdcRegion region = RegionIdcMap.getRegion(idc);
            setDescription(String.format("当前AppKey未在北上部署，且只有单机房:%s，容灾免达标", idc));
            return Objects.isNull(region) || Objects.equals(IdcRegion.OTHERS, region);
        }
        return false;
    }

}
