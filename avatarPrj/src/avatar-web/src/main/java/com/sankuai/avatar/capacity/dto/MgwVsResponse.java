package com.sankuai.avatar.capacity.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2022-09-19 11:50
 */
@Data
public class MgwVsResponse {
    private String status;
    private MgwVsData data;

    @Data
    public static class MgwVsData{
        List<MgwVs> items;
    }
    @Data
    public static class MgwVs{
        private String vip;
        private String vport;
        private String protocol;
    }
}
