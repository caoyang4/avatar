package com.sankuai.avatar.resource.activity.bo;

import lombok.Data;

import java.util.Date;

/**
 * 窗口期 bo 对象
 * @author caoyang
 * @create 2023-03-15 16:35
 */
@Data
public class WindowPeriodResourceBO {

    private Integer id;

    /**
     * 窗口期名称
     */
    private String name;

    /**
     * 窗口期描述
     */
    private String description;

    /**
     *   起始时间
     */
    private Date startTime;

    /**
     *   截止时间
     */
    private Date endTime;

    /**
     *  期望交付时间
     */
    private Date expectedDeliveryTime;

    /**
     *  申请人
     */
    private String createUser;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date updateTime;


}
