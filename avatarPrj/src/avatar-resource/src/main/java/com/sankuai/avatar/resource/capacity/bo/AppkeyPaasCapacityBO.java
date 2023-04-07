package com.sankuai.avatar.resource.capacity.bo;

import com.sankuai.avatar.resource.capacity.constant.PaasCapacityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * PaaS容灾等级 BO 对象
 * @author Jie.li.sh
 * @create 2022-09-26
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyPaasCapacityBO {

    private Integer id;

    /**
     * 业务 appkey
     */
    private String clientAppkey;

    /**
     * PaaS 名称: Eagle, S3, Mafka, Squirrel, ZK, RDS, Oceanus...
     */
    private String paasName;

    /**
     * 是否是核心服务
     */
    private Boolean isCore;

    /**
     * 容灾类型: CLUSTER(集群)，TOPIC(主题)，APPKEY(服务)
     */
    private PaasCapacityType type;

    /**
     * 容灾实体名称
     */
    private String typeName;

    /**
     * 容灾实体角色，对于mafka而言，分为producer/consumer
     */
    private String clientRole;

    /**
     * 容灾实体名称说明
     */
    private String typeComment;

    /**
     * 关联的 PaaS appkey
     */
    private String paasAppkey;

    /**
     * 容灾等级
     */
    private Integer capacityLevel;

    /**
     * 达标等级
     */
    private Integer standardLevel;

    /**
     * 是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 容灾配置: [{"producer.cluster.dispatch.type":"默认分配"}]
     */
    private List<Map<String,String>> clientConfig;

    /**
     * 达标配置: [{"producer.cluster.dispatch.type":"同地域集群优先"},{"producer.cluster.dispatch.type":"全部集群"}]
     * 相对于容灾配置的达标数据
     */
    private List<Map<String,String>> standardConfig;

    /**
     * 配置是否达标
     */
    private Boolean isConfigStandard;

    /**
     * 达标原因
     */
    private String standardReason;

    /**
     * 达标建议
     */
    private String standardTips;

    /**
     * 是否加白
     */
    private Boolean isWhite;

    /**
     * 加白原因
     */
    private String whiteReason;

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
     * 负责人
     */
    private String owner;

    /**
     * 上报人
     */
    private String updateBy;

    /**
     * 上报日期
     */
    private Date updateDate;

    /**
     * 最新上报时间
     */
    private Date updateTime;

}
