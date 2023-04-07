package com.sankuai.avatar.web.vo.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author caoyang
 * @create 2023-03-15 17:30
 */
@Data
public class WindowPeriodResourceVO {

    private Integer id;

    /**
     * 窗口期名称
     */
    @NotBlank(message = "活动名称不能为空")
    private String name;

    /**
     * 窗口期名称 + 时间范围
     */
    private String period;

    /**
     * 窗口期描述
     */
    private String description;

    /**
     *   起始时间
     */
    @NotNull(message = "请指定起始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     *   截止时间
     */
    @NotNull(message = "请指定截止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     *  期望交付时间
     */
    @NotNull(message = "请指定期望交付时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expectedDeliveryTime;

    /**
     *  申请人
     */
    @NotBlank(message = "请指定申请人")
    private String createUser;

    /**
     *  创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     *  更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 是否命中窗口期
     */
    private Boolean hit;

    /**
     * 窗口期是否有订单
     */
    private Boolean hasOrder;

}
