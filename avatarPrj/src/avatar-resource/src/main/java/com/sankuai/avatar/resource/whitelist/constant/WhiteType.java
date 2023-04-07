package com.sankuai.avatar.resource.whitelist.constant;

import lombok.Getter;

/**
 * 白名单类型
 * @author caoyang
 * @create 2022-10-27 18:37
 */
@Getter
public enum WhiteType {
    /**
     * 容灾:容灾等级白名单，添加后将不再关注该服务容灾等级信息
     */
    CAPACITY("capacity", "容灾等级", "容灾等级白名单，添加后将不再关注该服务容灾等级信息"),
    /**
     * 机房:服务机房白名单，添加后允许服务仅申请单个机房机器，(注:机房白名单与容灾等级无关)
     */
    IDC("idc", "多机房", "服务机房白名单，添加后允许服务仅申请单个机房机器。(多机房白名单与容灾等级无关)"),
    /**
     * 自动迁移:添加后认为服务可以自动迁移，会影响容灾等级1->2的判断
     */
    AUTO_MIGRATE("auto-migration", "自动迁移", "添加后认为服务可以自动迁移，会影响容灾等级1->2的判断"),
    /**
     * 资源利用率:申请加白后，将不参与资源利用率运营，如扩容限制、未达标推送通知等规则
     */
    UTILIZATION("utilization", "资源利用率", "申请加白后，将不参与资源利用率运营，如扩容限制、未达标推送通知等规则"),
    /**
     * 北京侧只读:服务在北、上两地双中心部署，北京侧只提供读接口
     */
    BJ_READONLY("bj-readonly", "北京侧只读", "服务在北、上两地双中心部署，北京侧只提供读接口"),
    /**
     * staging单机限制解除:st环境仅可申请单台机，添加白名单后，将允许申请多台机器
     */
    ST_SINGLE_RELIEVE("st-single-relieve", "staging单机限制解除", "st环境仅可申请单台机，添加白名单后，将允许申请多台机器"),
    /**
     * 非核心演习:非核心服务演习白名单，演习系统会忽略加入白名单的服务
     */
    SERVICE_DRILL("service-drill", "非核心演习", "非核心服务演习白名单，演习系统会忽略加入白名单的服务"),
    /**
     * 高峰期封禁:服务高峰期封禁白名单，添加后不受高峰期影响
     */
    ALTERATION("alteration", "高峰期封禁", "服务高峰期封禁白名单，添加后不受高峰期影响");


    /**
     * 白名单类型
     */
    private final String whiteType;
    /**
     * 白名单中文名称
     */
    private final String cname;

    /**
     * 白名单描述
     */
    private final String desc;

    WhiteType(String whiteType,String cname, String desc) {
        this.whiteType = whiteType;
        this.cname = cname;
        this.desc = desc;
    }
}
