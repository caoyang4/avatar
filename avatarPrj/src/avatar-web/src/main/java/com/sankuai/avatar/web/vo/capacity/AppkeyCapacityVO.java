package com.sankuai.avatar.web.vo.capacity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sankuai.avatar.resource.capacity.constant.UtilizationStandardType;
import com.sankuai.avatar.web.dto.capacity.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 服务容灾 VO
 * @author caoyang
 * @create 2022-11-04 19:57
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppkeyCapacityVO {
    /**
     * 服务名称
     */
    private String appkey;

    /**
     * set 名称：包含（set/liteset）
     */
    private String cellName;

    /**
     * 服务容灾等级，限定[0,5]
     */
    private Integer capacityLevel;

    /**
     * 容灾达标等级，限定[0,5]
     */
    private Integer standardLevel;

    /**
     * 容灾是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 达标原因
     */
    private String standardReason;

    /**
     * 达标建议
     */
    private String standardTips;

    /**
     * 服务资源利用率
     * {"value":0.1,"lastWeekValue":0.1,"yearPeekValue":0.3,"lastWeekValueWithoutSet":0.1}
     * value表示昨日利用率
     * lastWeekValue表示上周利用率
     * yearPeekValue表示年峰值利用率
     * lastWeekValueWithoutSet上周主干道利用率
     */
    private AppkeyCapacityUtilizationDTO utilization;

    /**
     * 资源利用率达标信息
     */
    private UtilizationStandardType utilizationStandard;

    /**
     * 白名单列表
     * [{"whiteApp":"BJ_READONLY","cName":"北京侧只读","endTime":"2200-12-31","reason":"xxx"}]
     */
    private List<AppkeyCapacityWhiteDTO> whiteList;
    // TODO 待确认后再下线
    private List<AppkeyCapacityWhiteDTO> whitelists;

    /**
     * 服务接入的中间件信息
     * [{"middleWareName":"OCEANUS_HTTP","used":false},{"middleWareName":"MQ","used":false}]
     */
    private List<AppkeyCapacityMiddleWareDTO> middleWareList;

    /**
     * 服务的机房机器信息
     */
    private List<AppkeyCapacityHostDTO> hostList;
    // TODO 待确认后再下线
    private List<AppkeyCapacityHostDTO> hosts;

    /**
     * octo http 节点
     */
    private List<AppkeyCapacityOctoProviderDTO> octoHttpProviderList;

    /**
     * octo thrift 节点
     */
    private List<AppkeyCapacityOctoProviderDTO> octoThriftProviderList;

    /**
     * 组件接入信息：弹性伸缩、plus发布、nest
     * [{"name":"elastic","cName":"弹性伸缩","access":false},
     * {"name":"plus","cName":"是否plus发布过","access":true},
     * {"name":"nest","cName":"是否为nest服务","access":false}]
     */
    private List<AppkeyCapacityAccessComponentDTO> accessComponentList;

    /**
     * 是否是 paas 服务
     */
    private Boolean isPaas;

    /**
     * 是否单机可重启
     */
    private Boolean canSingleHostRestart;

    /**
     * 是否是有状态服务
     */
    private Boolean stateful;

    /**
     * org编号路径
     */
    private String orgPath;

    /**
     * org 展示路径名称
     */
    private String orgDisplayName;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否加白
     */
    private Boolean isWhite;

    /**
     * 容灾加白原因
     */
    private String whiteReason;

    /**
     * 是否 set 化
     */
    private Boolean set;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;
}
