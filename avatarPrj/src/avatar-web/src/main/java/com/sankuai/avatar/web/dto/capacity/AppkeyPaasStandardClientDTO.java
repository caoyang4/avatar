package com.sankuai.avatar.web.dto.capacity;

import lombok.Data;

/**
 * paas 容灾达标客户端信息 dto
 * @author caoyang
 * @create 2022-10-31 10:11
 */
@Data
public class AppkeyPaasStandardClientDTO {
    /**
     * paas 名称
     */
    private String paasName;

    /**
     * 客户端语言
     */
    private String language;

    /**
     * 客户端groupId
     */
    private String groupId;

    /**
     *
     */
    private String artifactId;

    /**
     * 达标版本号
     */
    private String standardVersion;

    /**
     * 上报人
     */
    private String updateBy;
}
