package com.sankuai.avatar.resource.orgRole.bo;

import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import lombok.Data;

import java.util.Date;

/**
 * 部门角色管理 BO
 * @author caoyang
 * @create 2022-11-10 15:15
 */
@Data
public class OrgRoleAdminBO {

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
    private OrgRoleType role;

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
