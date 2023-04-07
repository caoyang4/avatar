package com.sankuai.avatar.web.vo.capacity;

import lombok.Data;

/**
 * appkey的整体容灾简要信息
 * @author caoyang
 * @create 2022-10-12 16:18
 */
@Data
public class AppkeyCapacitySummaryVO {

    /**
     * 自身实际整体容灾等级
     */
    private Integer capacityLevel;

    /**
     * 自身整体达标容灾等级
     */
    private Integer standardCapacityLevel;

    /**
     * 依赖 paas 实际整体容灾等级
     */
    private Integer paasCapacityLevel;

    /**
     * 依赖 paas 达标整体容灾等级
     */
    private Integer paasStandardCapacityLevel;

    /**
     * 达标建议
     */
    private String standardTips;

    /**
     * 是否是 paas
     */
    private Boolean isPaas;

    /**
     * 自身容灾是否达标
     */
    private Boolean isStandard;

    /**
     * paas容灾是否达标
     */
    private Boolean isPaasStandard;
}
