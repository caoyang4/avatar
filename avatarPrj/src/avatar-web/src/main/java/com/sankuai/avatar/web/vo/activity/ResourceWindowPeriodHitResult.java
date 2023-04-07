package com.sankuai.avatar.web.vo.activity;

import lombok.Data;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2021/8/18 10:15
 * @version 1.0
 */
@Data
public class ResourceWindowPeriodHitResult {

    /**
     * 字段: 窗口期ID
     */
    private Integer windowPeriodId;

    /**
     * 字段: cname
     * 说明: 窗口期名称
     */
    private String name;

    /**
     * 字段: period
     * 说明: 窗口期
     */
    private String period;

    /**
     * 字段: cname
     */
    private String description;

    /**
     * 字段: description
     */
    private Boolean hit;
}
