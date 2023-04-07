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
 * orgRoleAdmin数据对象
 * @author caoyang
 * @create 2022-11-01 19:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "org_role_admin")
public class OrgRoleAdminDO {

    /**
     *  主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     *  部门 id
     */
    private String orgId;

    /**
     *  部门全路径名称
     */
    private String orgName;

    /**
     *  用户角色，包含两类（运维负责人：op_admin, 测试负责人：ep_admin）
     */
    private String role;

    /**
     *  用户，多个用户逗号分隔，如 huguochao,qinwei05
     */
    private String roleUsers;

    /**
     *  信息更新者
     */
    private String updateUser;

    /**
     *   全路径部门 id
     */
    private String orgPath;

    /**
     * org 关联的群id，多个群逗号分隔，如 123，345
     */
    private String groupId;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date updateTime;

}
