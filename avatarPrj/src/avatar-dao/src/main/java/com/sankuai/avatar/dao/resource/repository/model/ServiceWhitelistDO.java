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
 * 服务白名单运营信息数据对象
 * @author caoyang
 * @create 2022-10-21 15:14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "whitelist_appkey")
public class ServiceWhitelistDO {
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
     * 加白原因
     */
    private String reason;

    /**
     * 加白 appkey
     */
    private String appkey;

    /**
     * 加白 appkey 所属应用
     */
    private String application;

    /**
     * 加白 appkey 所属 org
     */
    private String orgIds;

    /**
     * 指定加白的 set
     */
    private String setName;

    /**
     * 加白申请人
     */
    private String inputUser;

    /**
     * 白名单添加时间
     */
    private Date addTime;

    /**
     * 白名单生效截止时间
     */
    private Date endTime;


}
