package com.sankuai.avatar.web.dto.capacity;

import lombok.Data;

/**
 * 容灾资源利用率
 * @author caoyang
 * @create 2022-11-04 17:42
 */
@Data
public class AppkeyCapacityUtilizationDTO {
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
