package com.sankuai.avatar.web.vo.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动资源下载导出对象
 * @author caoyang
 * @create 2023-03-09 11:03
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityResourceExportVO {

    /**
     * 服务名称
     */
    private String appkey;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动描述
     */
    private String description;

    /**
     * CPU
     */
    private Integer cpu;

    /**
     * 内存
     */
    private Integer memory;

    /**
     * 城市
     */
    private String city;

    /**
     * 磁盘
     */
    private Integer disk;

    /**
     * 机房
     */
    private String idcs;

    /**
     * 数量
     */
    private Integer count;

    /**
     *   期望时间
     */
    private String expectedTime;

    /**
     *   交付时间
     */
    private String deliverTime;

    /**
     *   交付数量
     */
    private Integer deliverCount;

    /**
     *   归还时间
     */
    private String returnTime;

    /**
     *   活动状态
     */
    private String status;

    /**
     *   活动申请人
     */
    private String createUser;

}
