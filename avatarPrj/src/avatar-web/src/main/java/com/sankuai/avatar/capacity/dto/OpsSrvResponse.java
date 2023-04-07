package com.sankuai.avatar.capacity.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by shujian on 2020/2/20.
 * ops srv 数据
 */
@Data
public class OpsSrvResponse {
    @SerializedName("error")
    private OpsErrorResponse error;
    @SerializedName("srvs")
    private List<Map<String, ?>> srvs;
}
