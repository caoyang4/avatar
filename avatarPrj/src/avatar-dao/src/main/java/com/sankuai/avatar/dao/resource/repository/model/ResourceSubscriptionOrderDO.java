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
 * 机器到货订阅订单
 * @author caoyang
 * @create 2023-02-10 14:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "resource_host_subscription_order")
public class ResourceSubscriptionOrderDO {

    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 流程id
     */
    private Integer flowId;

    /**
     * 流程uuid
     */
    private String flowUuid;

    /**
     * appkey
     */
    private String appkey;

    /**
     * 环境
     */
    private String env;

    /**
     * 机器配置
     */
    private String hostConfig;

    /**
     * 申请数量
     */
    private Integer count;

    /**
     * appkey初始机房机器数目
     */
    private Integer initCount;

    /**
     * 状态
     */
    private String status;

    /**
     * 区域
     */
    private String region;

    /**
     * 机房
     */
    private String idc;

    /**
     * 结算单元
     */
    private String unit;

    /**
     * 订阅者
     */
    private String subscriber;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 申请原因
     */
    private String reason;

    private String defaultReason;

    /**
     * 到期时间
     */
    private Date expireTime;

    /**
     *   开始时间
     */
    private Date startTime;

    /**
     *   截止时间
     */
    private Date endTime;

    /**
     *   创建时间
     */
    private Date createTime;

    /**
     *   更新时间
     */
    private Date updateTime;

}
