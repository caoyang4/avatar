package com.sankuai.avatar.capacity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author caoyang
 * @create 2022-08-22 10:56
 */

@Data
public class OpsPlusResponse {
    @SerializedName("error")
    private Object error;
    @SerializedName("plus")
    private List<Map<String, Object>> plus;
}
