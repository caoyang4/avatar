package com.sankuai.avatar.resource.activity.bo;

import com.sankuai.avatar.resource.activity.constant.OrderStateType;
import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-02-13 11:24
 */
@Data
public class ResourceSubscriptionOrderBO {

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
    private OrderHostBO hostConfig;

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
    private OrderStateType status;

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
