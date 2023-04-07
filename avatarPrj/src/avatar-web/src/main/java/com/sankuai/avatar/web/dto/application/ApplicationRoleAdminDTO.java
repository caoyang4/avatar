package com.sankuai.avatar.web.dto.application;

import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-01-16 18:37
 */
@Data
public class ApplicationRoleAdminDTO {

    private Integer id;

    /**
     *  应用 id
     */
    private Integer applicationId;

    /**
     *  应用名称
     */
    private String applicationName;

    /**
     * 应用中文名称
     */
    private String applicationCnName;

    /**
     *  测试负责人
     */
    private String epAdmin;

    /**
     *  运维负责人
     */
    private String opAdmin;

    /**
     *  更新时间
     */
    private Date updateTime;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     *  更新人
     */
    private String updateUser;

    /**
     *  org 路径
     */
    private String orgPath;

    /**
     *  org 路径名称
     */
    private String orgNamePath;

}
