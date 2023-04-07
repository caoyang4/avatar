package com.sankuai.avatar.capacity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-26 17:24
 */
@Data
public class AreaZoneResponse {
    private Integer code;

    private String msg;

    private List<RegionZoneResponse> data;

}
