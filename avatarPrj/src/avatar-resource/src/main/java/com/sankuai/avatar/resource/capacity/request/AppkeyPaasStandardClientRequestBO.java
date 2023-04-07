package com.sankuai.avatar.resource.capacity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  服务应用的paas容灾达标客户端条件查询对象
 * @author caoyang
 * @create 2022-10-11 16:40
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyPaasStandardClientRequestBO {

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
}
