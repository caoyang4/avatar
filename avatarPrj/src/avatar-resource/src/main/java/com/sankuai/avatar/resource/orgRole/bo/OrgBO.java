package com.sankuai.avatar.resource.orgRole.bo;

import lombok.Data;

/**
 * @author caoyang
 * @create 2022-11-11 16:11
 */
@Data
public class OrgBO {
    /**
     * 组织ID
     */
    private String orgId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织状态， 1 生效， 0 失效
     */
    private Integer status;

    /**
     * 组织ID链
     */
    private String orgPath;

    /**
     * 组织名称链
     */
    private String orgNamePath;

    /**
     * 所属BG 组织ID
     */
    private String bgId;

    /**
     * 所属BG 组织名称
     */
    private String bgName;
}
