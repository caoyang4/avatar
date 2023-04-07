package com.sankuai.avatar.web.vo.capacity;

import lombok.Data;

/**
 * appkey所依赖的 paas 客户端信息
 * @author caoyang
 * @create 2022-10-11 20:35
 */
@Data
public class AppkeyPaasClientVO {

    /**
     * 业务 appkey
     */
    private String appkey;

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
     * 当前所使用的客户端版本
     */
    private String version;

    /**
     * 客户端达标版本
     */
    private String standardVersion;

    /**
     * 是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 客户端信息版本描述
     */
    private String clientDesc;
}
