package com.sankuai.avatar.client.ops.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author xk
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsAppkey {
    /**
     * Appkey名称
     */
    private String appkey;

    /**
     * 服务类型
     */
    private String category;

    /**
     * 负责人
     */
    private List<OpsOwner> owners;

    /**
     * 基础Appkey名称，一般与appkey值相同
     */
    private String baseApp;

    /**
     * 组
     */
    private String group;

    /**
     * 名字
     */
    private String name;

    /**
     * 技术团队
     */
    private int business;

    /**
     * pdl
     */
    private String pdl;

    /**
     * 级别
     */
    private int level;

    /**
     * 最大节点数目
     */
    private int maxNodeCount;

    /**
     * 关注人
     */
    private List<OpsOwner> observers;

    /**
     * 服务介绍
     */
    private String intro;

    /**
     * base
     */
    private int base;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * owt
     */
    private String owt;

    /**
     * 标签
     */
    private String tags;

    /**
     * reg限制
     */
    private int regLimit;

    /**
     * 创建时间
     */
    private int createTime;

    /**
     * 级别名称
     */
    private String levelName;
}
