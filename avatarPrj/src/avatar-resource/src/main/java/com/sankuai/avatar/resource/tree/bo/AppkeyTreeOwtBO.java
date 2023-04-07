package com.sankuai.avatar.resource.tree.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * 服务树Owt对象BO
 *
 * @author zhangxiaoning07
 * @create 2022-10-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyTreeOwtBO {
    /**
     * ID
     */
    private Integer id;

    /**
     * Owt的key
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     * Owt的说明
     */
    private String comment;

    /**
     * 类型，有business, resource, paas三种类型
     */
    private String kind;

    /**
     * nginx集群
     */
    private String nginxCluster;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * bg
     */
    private String businessGroup;

    /**
     * sre大象群
     */
    private String sreXmGroup;

    /**
     * org
     */
    private List<OwtOrgBO> org;

    /**
     * RD负责人
     */
    private List<String> rdAdmin;

    /**
     * 运维负责人
     */
    private List<String> opAdmin;

    /**
     * 测试负责人
     */
    private List<String> epAdmin;

}

