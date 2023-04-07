package com.sankuai.avatar.resource.application.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-01-11 17:26
 */
@Data
public class ApplicationRoleAdminBO {

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
