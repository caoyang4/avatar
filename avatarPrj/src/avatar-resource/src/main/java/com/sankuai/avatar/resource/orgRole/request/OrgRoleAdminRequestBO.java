package com.sankuai.avatar.resource.orgRole.request;

import com.sankuai.avatar.common.vo.PageRequest;
import com.sankuai.avatar.resource.orgRole.constant.OrgRoleType;
import lombok.*;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-10 15:25
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrgRoleAdminRequestBO extends PageRequest {
    /**
     * 主键列表
     */
    private List<Integer> ids;
    /**
     *  部门 id 列表，支持批量查询
     */
    private List<String> orgIds;
    /**
     *  用户角色，包含两类（运维负责人：op_admin, 测试负责人：ep_admin）
     */
    private OrgRoleType role;

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
