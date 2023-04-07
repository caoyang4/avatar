package com.sankuai.avatar.capacity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2022-07-13 14:36
 */
@Data
public class HulkServiceStrategyResponse {
    @SerializedName("code")
    private Integer code;
    @SerializedName("data")
    private Strategy data;

    @Data
    public static class Strategy{
        Map<String, ?> serviceInfo;
        List<Map<String, ?>> groupWithTagsStrategiesPairs;
    }
}
