package com.sankuai.avatar.web.vo.activity;

import lombok.Data;

/**
 * @author caoyang
 * @create 2023-03-15 19:51
 */
@Data
public class WindowPeriodHitVO {

    /**
     * 窗口期ID
     */
    private Integer windowPeriodId;

    /**
     * 窗口期名称
     */
    private String name;

    /**
     * 窗口期+时间范围
     */
    private String period;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否命中
     */
    private Boolean hit;

}
