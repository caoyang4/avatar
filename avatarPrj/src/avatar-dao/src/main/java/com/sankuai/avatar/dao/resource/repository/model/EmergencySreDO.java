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
 * @create 2023-01-17 15:26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "org_sre_emergency")
public class EmergencySreDO {

    /**
     *  主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 来源ID
     */
    private Integer sourceId;

    /**
     *  服务
     */
    private String appkey;

    /**
     *  SRE
     */
    private String opAdmin;

    /**
     *  临时新增SRE
     */
    private String attachAdmin;

    /**
     *  次数
     */
    private Integer time;

    /**
     *  状态
     */
    private String state;

    /**
     *  创建人
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
