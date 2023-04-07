package com.sankuai.avatar.capacity.dto;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-12-26 16:04
 */
@Data
public class RegionZoneResponse {
    private String zone;
    /**
     * AZ中文名称
     */
    private String zoneName;
    /**
     * AZ状态
     */
    private Boolean zoneState;
    /**
     * AZ租户
     */
    private String zoneTenant;
    /**
     * AZ类型
     */
    private String zoneType;

    private String region;
    /**
     * Region名称
     */
    private String regionName;

    private String zoneAlias;
    /**
     * Region所在国家标识
     */
    private String country;
    /**
     * Region所在国家名称
     */
    private String countryName;
    /**
     * 所属物理AZ（若自身为物理AZ则置空）
     */
    private String physicalZone;
    /**
     * 网段（若自身为物理AZ则不展示网段）
     */
    private String networkSegment;

}
