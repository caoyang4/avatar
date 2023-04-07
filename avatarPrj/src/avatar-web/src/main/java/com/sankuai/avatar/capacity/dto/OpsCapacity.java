package com.sankuai.avatar.capacity.dto;

import com.google.gson.annotations.SerializedName;
import com.sankuai.avatar.web.vo.capacity.AppkeyPaasCapacityReportVO;
import lombok.Data;

import java.util.Date;

/**
 * @author shujian
 * @date 2020/2/20
 * 容灾等级结构:
 * cpt 容灾等级 0 - 5 (0代表无容灾等级)1
 * reason 判定容灾等级的原因
 * update_at,  计算时间
 */
@Data
public class OpsCapacity {
    @SerializedName("capacity")
    private Integer capacity;
    @SerializedName("capacity_reason")
    private String reason;
    @SerializedName("capacity_update_at")
    private String update_at;
    @SerializedName("capacity_update_by")
    private String updateBy;

    public static OpsCapacity getFromCapacityDTO(CapacityDTO capacityDTO) {
        OpsCapacity opsCapacity = new OpsCapacity();
        opsCapacity.setUpdateBy(capacityDTO.getUpdateBy());
        String updateTime = capacityDTO.getUpdateTime() != null ? capacityDTO.getUpdateTime().toString() : new Date().toString();
        opsCapacity.setUpdate_at(updateTime);
        opsCapacity.setReason(capacityDTO.getStandardReason());
        opsCapacity.setCapacity(capacityDTO.getCapacityLevel());
        return opsCapacity;
    }

    public static OpsCapacity getFromCapacityPaasReport(AppkeyPaasCapacityReportVO capacityReportVO) {
        OpsCapacity opsCapacity = new OpsCapacity();
        opsCapacity.setUpdateBy(capacityReportVO.getUpdateBy());
        opsCapacity.setUpdate_at(new Date().toString());
        opsCapacity.setReason(capacityReportVO.getStandardReason());
        opsCapacity.setCapacity(capacityReportVO.getCapacityLevel());
        return opsCapacity;
    }
}
