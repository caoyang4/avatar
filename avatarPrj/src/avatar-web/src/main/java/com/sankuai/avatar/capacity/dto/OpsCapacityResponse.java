package com.sankuai.avatar.capacity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

/**
 * ops容灾达标等级
 * @author chenxinli
 */
@Data
public class OpsCapacityResponse {
    @SerializedName("error")
    private OpsErrorResponse error;

    @SerializedName("data")
    private Map<String, ?> data;
}
