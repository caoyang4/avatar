package com.sankuai.avatar.resource.mcm.bo;

import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
public class PeriodConfigBO {
    /**
     * 配置起止日期
     */
    private Object weekTime;

    /**
     * 解禁开始时间
     */
    private String startDate;

    /**
     * 解禁结束时间
     */
    private String endDate;
}
