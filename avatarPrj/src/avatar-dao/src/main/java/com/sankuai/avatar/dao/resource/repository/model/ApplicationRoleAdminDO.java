package com.sankuai.avatar.dao.resource.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author caoyang
 * @create 2023-01-11 14:20
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "org_application_role_admin")
public class ApplicationRoleAdminDO {

    /**
     *  主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
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
