package com.sankuai.avatar.resource.tree.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * OPS服务树节点对象
 *
 * @author qinwei05
 * @date 2022/12/08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyTreeSrvBO {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 描述信息
     */
    private String comment;

    /**
     * 服务名字
     */
    private String name;

    /**
     * srv key
     */
    private String key;

    /**
     * soaapp：应用名
     */
    private String soaapp;

    /**
     * 模块
     */
    private String module;

    /**
     * soasrv
     */
    private String soasrv;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 类别
     */
    private String category;

    /**
     * 模块名
     */
    private String soamod;

    /**
     * 预装软件
     */
    private String containerType;

    /**
     * 服务类型：语言/组件
     */
    private String serviceType;

    /**
     * 服务状态
     */
    private Boolean stateful;

    /**
     * 有状态原因
     */
    private String statefulReason;

    /**
     * 兼容ipv6
     */
    private Boolean compatibleIpv6;

    /**
     * 容灾等级等级设置原因
     */
    private String capacityReason;

    /**
     * 容灾等级更新时间
     */
    private Date capacityUpdateAt;

    /**
     * 容灾等级更新人
     */
    private String capacityUpdateBy;

    /**
     * 容灾等级
     */
    private Integer capacity;

    /**
     * 服务隔离环境标识
     */
    private String tenant;

    /**
     * rd负责人
     */
    private List<String> rdAdmin;

    /**
     * 测试负责人
     */
    private List<String> epAdmin;

    /**
     * SRE负责人
     */
    private List<String> opAdmin;

    /**
     * 服务是否包含set主机
     */
    private Boolean isSet;

    /**
     * 服务是否包含liteset主机
     */
    private Boolean isLiteset;

    /**
     * 创建时间
     */
    private Date createAt;
}
