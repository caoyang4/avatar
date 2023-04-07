package com.sankuai.avatar.capacity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author chenxinli
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UtilizationOptimizeDTO extends CapacityDTO {
    private Boolean isNeedOptimize;
    private Boolean isUniqueConfig;
    private Double cpuPerCount;
    private Double memPerCount;
    private Integer cpuSumCount;
    private Integer memSumCount;
    private Integer reduceHostCount;
    private Integer reduceCpuCount;
    private Integer reduceMemCount;
    private String optimizeMsg;


}
