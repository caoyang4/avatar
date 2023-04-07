package com.sankuai.avatar.dao.resource.repository.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务PaaS达标客户端容灾信息的条件查询对象
 * @author caoyang
 * @create 2022-09-27 11:35
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyPaasStandardClientRequest {

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
