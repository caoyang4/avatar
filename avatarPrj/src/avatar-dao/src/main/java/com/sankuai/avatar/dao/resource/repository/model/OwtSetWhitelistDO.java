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
 * owt 下 set 的容灾白名单数据对象
 * @author caoyang
 * @create 2022-10-21 15:15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "whitelist_owt_set")
public class OwtSetWhitelistDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 白名单类型(capacity/容灾，idc/机房，auto-migration/自动迁移，utilization/资源利用率......)
     */
    private String app;

    /**
     * 加白所属 owt
     */
    private String owt;

    /**
     * 指定加白的 set
     */
    private String setName;

    /**
     * 加白原因
     */
    private String reason;

    /**
     * 申请人
     */
    private String applyBy;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 白名单生效起始时间
     */
    private Date startTime;

    /**
     * 白名单生效截止时间
     */
    private Date endTime;

}
