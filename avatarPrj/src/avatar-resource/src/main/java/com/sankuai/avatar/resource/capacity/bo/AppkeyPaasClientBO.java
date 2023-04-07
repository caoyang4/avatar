package com.sankuai.avatar.resource.capacity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * PaaS客户端 BO 对象
 * @author caoyang
 * @create 2022-10-11 16:27
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyPaasClientBO {

    private Integer id;

    /**
     * 使用不达标客户端的业务appkey
     */
    private String clientAppkey;

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
     * 业务 appkey 使用的客户端版本号
     */
    private String version;

    /**
     * 达标版本号
     */
    private String standardVersion;

    /**
     * 是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 上报人
     */
    private String updateBy;

    /**
     * 上报日期
     */
    private Date updateDate;
}
