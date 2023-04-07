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
 * PaaS达标客户端数据对象
 * @author caoyang
 * @create 2022-09-27 10:58
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "capacity_paas_standard_client")
public class AppkeyPaasStandardClientDO {

    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * PaaS 名称: Eagle, S3, Mafka, Squirrel, ZK, RDS, Oceanus...
     */
    private String paasName;

    /**
     * 客户端 groupId
     */
    private String groupId;

    /**
     * 客户端 artifactId
     */
    private String artifactId;

    /**
     * 客户端语言: Java, Python, GoLang, C++, NodeJs, C#...
     */
    private String language;

    /**
     * 达标版本号
     */
    private String standardVersion;

    /**
     * 上报人
     */
    private String updateBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
