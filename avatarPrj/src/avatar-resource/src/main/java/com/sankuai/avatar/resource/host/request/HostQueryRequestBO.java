package com.sankuai.avatar.resource.host.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 主机查询对象
 *
 * @author qinwei05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostQueryRequestBO {

    /**
     * 环境
     */
    private String env;

    /**
     * 服务
     */
    private String appkey;

    /**
     * 服务树节点
     */
    private String srv;

    /**
     * 检索关键字
     * 支持主机名或IP单个、批量检索
     * 支持机器配置检索
     */
    private String query;

    /**
     * 批量检索类型（IP、Name）
     */
    private String batchQueryType;

    /**
     * SET（set + LiteSet）
     */
    private String set;

    /**
     * SET（set + LiteSet）
     */
    private String cell;

    /**
     * 泳道
     */
    private String swimlane;

    /**
     * 业务分组
     */
    private String grouptags;

    /**
     * 网络类型
     */
    private String netType;

    /**
     * 来源（弹性...）
     */
    private String managePlat;

    /**
     * 机房
     */
    private String idcName;

    /**
     * 类型名称（HULK、虚拟机、物理机）
     */
    private String kindName;

    /**
     * 资源池（公共、结算单元、活动、紧急资源池）
     */
    private String originType;

    /**
     * RS版本-关联查询
     */
    private String rsNetType;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 排序（正序、逆序）
     */
    private String sort;

    /**
     * 上线时间: 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 上线时间: 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /* ---------------------------- 自定义高级筛选 ---------------------------- */

    /**
     * 是否为活动资源
     */
    private Boolean activeResourcePool;

    /**
     * 是否可以替换紧急资源
     */
    private Boolean replaceUrgency;

    /**
     * 是否为可赠予的机器
     */
    private Boolean exchange;

    /**
     * 是否为弹性机器
     */
    private Boolean elastic;

    /**
     * 是否为禁用机器
     */
    private Boolean octoDisabled;

}
