package com.sankuai.avatar.client.mcm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author zhaozhifan02
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeriodConfig {
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
