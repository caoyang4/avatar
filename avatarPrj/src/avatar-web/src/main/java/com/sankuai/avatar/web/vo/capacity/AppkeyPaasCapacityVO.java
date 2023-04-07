package com.sankuai.avatar.web.vo.capacity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.resource.capacity.constant.PaasCapacityType;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务单paas容灾等级信息
 * @author Jie.li.sh
 * @create 2022-09-26
 **/

@Data
public class AppkeyPaasCapacityVO {

    /**
     * PaaS 名称: Eagle, S3, Mafka, Squirrel, ZK, RDS, Oceanus...
     */
    private String paasName;
    /**
     * 业务 appkey
     */
    private String appkey;

    /**
     * 关联的 PaaS appkey
     */
    private String paasAppkey;

    /**
     * 容灾类型
     */
    private PaasCapacityType type;

    /**
     * 容灾实体
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
     * 当前等级
     */
    private Integer capacityLevel;

    /**
     * 达标基准
     */
    private Integer standardLevel;

    /**
     * 是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 未达标容灾配置项
     */
    private List<Map<String, String>> clientConfig;

    /**
     * 达标配置项
     */
    private List<Map<String, String>> standardConfig;

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
     * 客户端版本信息
     */
    private AppkeyPaasClientVO clientVersion;

    /**
     * 是否加白
     */
    private Boolean white;

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
     * 最新上报时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
