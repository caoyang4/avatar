package com.sankuai.avatar.resource.capacity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PaaS容灾达标客户端 BO 对象
 * @author caoyang
 * @create 2022-10-11 16:28
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyPaasStandardClientBO {

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
}
