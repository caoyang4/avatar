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
 * @author caoyang
 * @create 2023-03-15 15:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "resource_host_period")
public class ResourceWindowPeriodDO {

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
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
