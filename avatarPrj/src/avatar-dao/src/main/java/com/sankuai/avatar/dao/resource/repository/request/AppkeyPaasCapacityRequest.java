package com.sankuai.avatar.dao.resource.repository.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 服务PaaS容灾等级的条件查询对象
 * @author Jie.li.sh
 * @create 2022-09-26
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppkeyPaasCapacityRequest {

    private Integer idGtn;

    /**
     * 业务appkey
     */
    private String clientAppkey;

    /**
     * PaaS 名称: Eagle, S3, Mafka, Squirrel, ZK, RDS, Oceanus...
     */
    private String paasName;

    /**
     * paas appkey
     */
    private String paasAppkey;

    /**
     * 容灾类型: CLUSTER(集群)，TOPIC(主题)，APPKEY(服务)
     */
    private String type;

    /**
     * 容灾实体
     */
    private String typeName;

    /**
     * 容灾实体角色
     */
    private String clientRole;

    /**
     * 是否加白
     */
    private Boolean isWhite;

    /**
     * 是否为核心
     */
    private Boolean isCore;

    /**
     * 是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 是否 set 化
     */
    private Boolean isSet;

    /**
     * set名称
     */
    private String setName;

    /**
     * set类型
     */
    private String setType;

    /**
     * 上报日期
     */
    private Date updateDate;

}
