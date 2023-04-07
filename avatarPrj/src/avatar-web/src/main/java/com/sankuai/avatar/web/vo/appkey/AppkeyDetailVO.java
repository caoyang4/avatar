package com.sankuai.avatar.web.vo.appkey;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * appkey详情对象
 *
 * @author qinwei05
 * @create 2022-12-24 14:15
 * @date 2023/01/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyDetailVO {

    /**
     * 服务名称
     */
    private String appkey;

    /**
     * 服务树节点KEY（Srv）
     */
    private String key;

    /**
     * 名字
     */
    private String name;

    /**
     * 描述信息
     */
    private String comment;

    /**
     * 应用名
     */
    private String soaapp;

    /**
     * 模块名
     */
    private String soamod;

    /**
     * 服务名
     */
    private String soasrv;

    /**
     * 主机数
     */
    private Integer hostCount;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 兼容ipv6
     */
    private Boolean compatibleIpv6;

    /**
     * 服务树
     */
    private Tree tree;

    /**
     * 预装软件
     */
    private String containerType;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 是否存在Liteset机器
     */
    private Boolean isLiteset;

    /**
     * 是否存在SET机器
     */
    private Boolean isSet;

    /**
     * 是否有状态
     */
    private Boolean stateful;

    /**
     * 有状态原因
     */
    private String statefulReason;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 框架
     */
    private List<String> frameworks;

    /**
     * 类别
     */
    private List<String> categories;

    /**
     * 结算单元ID
     */
    private Integer billingUnitId;

    /**
     * 结算单元名称
     */
    private String billingUnitName;

    /**
     * 外购服务
     */
    private String isBoughtExternal;

    /**
     * 租户
     */
    private String tenant;

    /**
     * 团队
     */
    private TeamVO team;


    /**
     * 服务Owner
     */
    private UserVO admin;

    /**
     * 研发负责人
     */
    private List<UserVO> rdAdmin;

    /**
     * 测试负责人
     */
    private List<UserVO> epAdmin;

    /**
     * 运维负责人
     */
    private List<UserVO> opAdmin;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createAt;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserVO {
        private String orgName;
        private String avatarUrl;
        private String loginName;
        private String name;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tree {
        private TreeData owt;
        private TreeData pdl;
        /**
         * 技术团队
         */
        private String bg;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreeData {
        /**
         * 树节点名称
         */
        private String key;
        /**
         * 树节点中文名称
         */
        private String name;
    }
}
