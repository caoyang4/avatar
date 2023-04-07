package com.sankuai.avatar.web.vo.activity;

import lombok.Builder;
import lombok.Data;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2021/8/18 10:15
 * @version 1.0
 */
@Data
@Builder
public class RegionSum {
    /**
     * 字段: CPU
     */
    private Integer cpu;

    /**
     * 字段: memory
     */
    private Integer memory;

    /**
     * 字段: city
     */
    private String city;

    /**
     * 字段: disk
     */
    private Integer disk;
}
