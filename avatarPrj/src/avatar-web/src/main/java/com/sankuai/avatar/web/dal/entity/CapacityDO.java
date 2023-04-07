package com.sankuai.avatar.web.dal.entity;

import com.sankuai.avatar.capacity.constant.WhiteApp;
import com.sankuai.avatar.capacity.node.*;
import com.sankuai.avatar.capacity.util.GsonUtils;
import com.sankuai.avatar.web.handler.capacity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 *   表名: appkey_capacity
 * @author chenxinli
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "capacity_appkey")
public class CapacityDO {
    /**
     *   字段: id
     */
    @Id
    private Integer id;

    private String orgPath;

    private String orgDisplayName;

    private Boolean stateful;
    /**
     *   字段: appkey
     */
    private String appkey;

    /**
     *   字段: set_name
     */
    private String setName;

    /**
     *   字段: capacity_level
     */
    private Integer capacityLevel;

    /**
     *   字段: standard_level
     */
    private Integer standardLevel;

    /**
     *   字段: is_capacity_standard
     */
    private Boolean isCapacityStandard;

    /**
     *   字段: resource_util
     */

    @ColumnType(typeHandler = ResourceUtilTypeHandler.class)
    private ResourceUtil resourceUtil;

    /**
     *   字段: utilization_standard
     */
    private String utilizationStandard;

    /**
     *   字段: whitelists
     */
    @ColumnType(typeHandler = WhiteListTypeHandler.class)
    private List<WhiteInfo> whitelists;

    /**
     *   字段: middleware
     */
    @ColumnType(typeHandler = MiddlewareTypeHandler.class)
    private List<MiddleWare> middleware;

    /**
     *   字段: access_component
     */
    @ColumnType(typeHandler = AccessComponentTypeHandler.class)
    private List<AccessComponent> accessComponent;

    /**
     *   字段: update_by
     */
    private String updateBy;

    /**
     *   字段: update_time
     */
    private Date updateTime;

    /**
     *   字段: is_paas
     */
    private Boolean isPaas;

    /**
     *   字段: can_single_host_restart
     */
    private Boolean canSingleHostRestart;
    /**
     *   字段: standard_reason
     */
    private String standardReason;

    /**
     *   字段: standard_tips
     */
    private String standardTips;

    /**
     *   字段: hosts
     */
    @ColumnType(typeHandler = HostTypeHandler.class)
    private List<Host> hosts;

    /**
     *   字段: octo_http_provider
     */
    @ColumnType(typeHandler = OctoNodeTypeHandler.class)
    private List<OctoProvider> octoHttpProvider;

    /**
     *   字段: octo_thrift_provider
     */
    @ColumnType(typeHandler = OctoNodeTypeHandler.class)
    private List<OctoProvider> octoThriftProvider;

    private List<WhiteInfo> getWhiteInfos(){
        try {
            String whiteInfo = GsonUtils.serialization(whitelists);
            WhiteInfo[] whiteInfos = GsonUtils.deserialization(whiteInfo, WhiteInfo[].class);
            return Arrays.asList(whiteInfos);
        } catch (Exception e){
        }
        return new ArrayList<>();
    }

    public boolean isCapacityWhite(){
        List<WhiteInfo> whiteInfos = getWhiteInfos();
        for (WhiteInfo info : whiteInfos) {
            if (WhiteApp.CAPACITY.equals(info.getWhiteApp())) {
                return true;
            }
        }
        return false;
    }

    public Date getCapacityWhiteExpireTime(){
        List<WhiteInfo> whiteInfos = getWhiteInfos();
        for (WhiteInfo info : whiteInfos) {
            if (WhiteApp.CAPACITY.equals(info.getWhiteApp())) {
                return info.getEndTime();
            }
        }
        return null;
    }

    public String getCapacityWhiteReason(){
        List<WhiteInfo> whiteInfos = getWhiteInfos();
        for (WhiteInfo info : whiteInfos) {
            if (WhiteApp.CAPACITY.equals(info.getWhiteApp())) {
                return info.getReason();
            }
        }
        return "";
    }

}