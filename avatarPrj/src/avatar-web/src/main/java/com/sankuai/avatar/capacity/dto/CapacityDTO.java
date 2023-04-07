package com.sankuai.avatar.capacity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sankuai.avatar.capacity.constant.CapacityCoreConstant;
import com.sankuai.avatar.capacity.node.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author chenxinli
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapacityDTO {

    private String appkey;

    private String orgPath;

    private String orgDisplayName;

    private Boolean stateful;

    private String cellName;

    private Integer capacityLevel;

    private Integer standardLevel;

    private Boolean isCapacityStandard;

    private ResourceUtil resourceUtil;

    private String utilizationStandard;

    private List<WhiteInfo> whitelists;

    private List<MiddleWare> middleware;

    private List<AccessComponent> accessComponent;

    private String updateBy;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Boolean isPaas;

    private Boolean canSingleHostRestart;

    private String standardReason;

    private String standardTips;

    private List<Host> hosts;

    private List<OctoProvider> octoHttpProvider;

    private List<OctoProvider> octoThriftProvider;

    private Boolean isWhite;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date expireTime;

    private String whiteReason;

    public Boolean isSet(){
        return !CapacityCoreConstant.DEFAULT_CELL.equals(cellName);
    }

}
