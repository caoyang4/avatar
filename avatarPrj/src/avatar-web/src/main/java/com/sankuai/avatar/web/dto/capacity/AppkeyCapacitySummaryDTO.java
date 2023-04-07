package com.sankuai.avatar.web.dto.capacity;

import lombok.Data;

/**
 * paas 容灾汇总 dto
 * @author caoyang
 * @create 2022-10-14 19:09
 */
@Data
public class AppkeyCapacitySummaryDTO {
    /**
     * paas 实际整体容灾等级
     */
    private Integer capacityLevel;

    /**
     * paas 达标整体容灾等级
     */
    private Integer standardCapacityLevel;

    /**
     * 达标建议
     */
    private String standardTips;

    /**
     * paas容灾是否达标
     */
    private Boolean isCapacityStandard;
}
