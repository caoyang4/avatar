package com.sankuai.avatar.web.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 * @create 2022-11-11 13:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrgRoleAdminPageRequest extends PageRequest {

    /**
     * 部门编号
     */
    private String orgId;
    /**
     *  用户角色，包含两类（运维负责人：op_admin, 测试负责人：ep_admin）
     */
    private String role;

    /**
     * 角色 mis
     */
    private String roleUser;

    /**
     *  部门全路径名称: 模糊匹配
     */
    private String orgName;

    /**
     *   全路径部门 id: 模糊匹配
     */
    private String orgPath;
}
