package com.sankuai.avatar.dao.resource.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 业务容灾等级数据对象
 * @author caoyang
 * @create 2022-11-03 13:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "capacity_appkey")
public class AppkeyCapacityDO {

    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 服务名称
     */
    private String appkey;

    /**
     * set 名称：包含（set/liteset）
     */
    private String setName;

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
    private String resourceUtil;

    /**
     * 资源利用率达标信息
     */
    private String utilizationStandard;

    /**
     * 白名单列表
     * [{"whiteApp":"BJ_READONLY","cName":"北京侧只读","endTime":"2200-12-31","reason":"xxx"}]
     */
    private String whitelists;

    /**
     * 服务接入的中间件信息
     * [{"middleWareName":"OCEANUS_HTTP","used":false},{"middleWareName":"MQ","used":false}]
     */
    private String middleware;

    /**
     * 服务的机房机器信息
     */
    private String hosts;

    /**
     * http 节点
     */
    private String octoHttpProvider;

    /**
     * thrift 节点
     */
    private String octoThriftProvider;

    /**
     * 组件接入信息：弹性伸缩、plus发布、nest
     * [{"name":"elastic","cName":"弹性伸缩","access":false},
     * {"name":"plus","cName":"是否plus发布过","access":true},
     * {"name":"nest","cName":"是否为nest服务","access":false}]
     */
    private String accessComponent;

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
     * 更新时间
     */
    private Date updateTime;

}
