package com.sankuai.avatar.dao.cache.model;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-12-01 19:20
 */
@Data
public class CapacitySummary {

    /**
     * 实际整体容灾等级
     */
    private Integer capacityLevel;

    /**
     * 达标整体容灾等级
     */
    private Integer standardCapacityLevel;

    /**
     * 达标建议
     */
    private String standardTips;

    /**
     * 整体是否达标
     */
    private Boolean isCapacityStandard;

}
