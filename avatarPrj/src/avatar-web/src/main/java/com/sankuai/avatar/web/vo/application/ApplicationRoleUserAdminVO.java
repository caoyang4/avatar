package com.sankuai.avatar.web.vo.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author caoyang
 * @create 2023-01-17 10:38
 */
@Data
public class ApplicationRoleUserAdminVO {

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
    private ApplicationRoleVO epRoleAdmin;

    /**
     *  运维负责人
     */
    private ApplicationRoleVO opRoleAdmin;

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

    /**
     *  更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     *  创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
