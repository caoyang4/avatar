package com.sankuai.avatar.capacity.dto;

import lombok.Data;

/**
 * @author caoyang
 * @create 2023-02-01 15:49
 */
@Data
public class DayuSetSiteResponse {

    private Integer code;
    private String message;
    private DayuSetSite data;

    @Data
    public static class DayuSetSite{
        private String env;
        private String name;
        private String desc;
    }
}
