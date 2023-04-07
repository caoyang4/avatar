package com.sankuai.avatar.client.ops.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2022-12-01 11:59
 */
@Builder
@Data
public class OpsCapacity {
    @SerializedName("capacity")
    private Integer capacity;
    @SerializedName("capacity_reason")
    private String reason;
    @SerializedName("capacity_update_at")
    private Date updateTime;
    @SerializedName("capacity_update_by")
    private String updateBy;
}
