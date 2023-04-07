package com.sankuai.avatar.capacity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2022-08-23 15:25
 */
@Data
public class ScMetaResponse {
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Map<String, Object>> data;
}
