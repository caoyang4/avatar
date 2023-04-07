package com.sankuai.avatar.web.vo.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.resource.activity.constant.OrderStateType;
import com.sankuai.avatar.web.dto.activity.OrderHostDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author caoyang
 * @create 2023-02-13 14:43
 */
@Data
public class ResourceSubscriptionOrderVO {

    private Integer id;

    /**
     * 流程id
     */
    @NotNull
    private Integer flowId;

    /**
     * 流程uuid
     */
    @NotBlank(message = "flowUuid不能为空")
    private String flowUuid;

    /**
     * appkey
     */
    @NotBlank(message = "appkey不能为空")
    private String appkey;

    /**
     * 环境
     */
    private String env;

    /**
     * 机器配置
     */
    private OrderHostDTO hostConfig;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date expireTime;

    /**
     *   开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;

    /**
     *   截止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    /**
     *   创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     *   更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

}
