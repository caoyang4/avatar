package com.sankuai.avatar.resource.capacity.bo;

import lombok.Data;

/**
 * 服务资源利用率
 * @author caoyang
 * @create 2022-11-03 15:43
 */
@Data
public class AppkeyCapacityUtilizationBO {
    /**
     * 昨日利用率
     */
    private Double value;
    /**
     * 上周利用率
     */
    private Double lastWeekValue;
    /**
     * 上周主干道利用率
     */
    private Double lastWeekValueWithoutSet;
    /**
     * 年峰值利用率
     */
    private Double yearPeekValue;
}
