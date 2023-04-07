package com.sankuai.avatar.capacity.constant;

import com.google.common.collect.ImmutableMap;
import com.sankuai.avatar.capacity.dto.RegionZoneResponse;
import com.sankuai.avatar.capacity.util.TerraUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author chenxinli
 */
public class RegionIdcMap {
    public static final Map<IdcRegion, ArrayList> regionIdcMap = ImmutableMap.of(
            IdcRegion.BJ, new ArrayList<>(Arrays.asList(
                    "dx", "yf", "cq", "gh", "xr", "rz", "wj", "tx_bj", "tx_bj3", "tx_bj7", "zf", "mt", "hh", "sjz", "zw")),
            IdcRegion.SH, new ArrayList<>(Arrays.asList(
                    "nh", "gq", "yp", "jd", "yy", "tx_sh", "xh", "tx_sh4", "wr", "pj", "hj"))
    );


    public static IdcRegion getRegion(String idc) {
        if (RegionIdcMap.regionIdcMap.get(IdcRegion.BJ).contains(idc)) {
            return IdcRegion.BJ;
        } else if (RegionIdcMap.regionIdcMap.get(IdcRegion.SH).contains(idc)) {
            return IdcRegion.SH;
        } else {
            RegionZoneResponse zoneResponse = getRegionZoneResponse(idc);
            // idc不在Region/AZ信息接口，不参与容灾计算
            if (Objects.isNull(zoneResponse) || StringUtils.isEmpty(zoneResponse.getRegion())) {
                return null;
            }
            if (Objects.equals("beijing", zoneResponse.getRegion().toLowerCase())) {
                return IdcRegion.BJ;
            }
            if (Objects.equals("shanghai", zoneResponse.getRegion().toLowerCase())) {
                return IdcRegion.SH;
            }
            return IdcRegion.OTHERS;
        }
    }

    private static RegionZoneResponse getRegionZoneResponse(String idc){
        List<RegionZoneResponse> regionZone = TerraUtils.getRegionZone();
        for (RegionZoneResponse zone : regionZone) {
            if (StringUtils.isNotEmpty(zone.getZone()) && Objects.equals(idc, zone.getZone().toLowerCase())) {
                return zone;
            }
            if (StringUtils.isNotEmpty(zone.getZoneAlias()) && Arrays.asList(zone.getZoneAlias().split(",")).contains(idc)) {
               return zone;
            }
        }
        return null;
    }
}
