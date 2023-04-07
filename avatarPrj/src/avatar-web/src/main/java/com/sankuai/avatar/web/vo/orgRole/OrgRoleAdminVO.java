package com.sankuai.avatar.web.vo.orgRole;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-11 13:49
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgRoleAdminVO {
    /**
     *  部门 id
     */
    @NotBlank(message = "orgId不能为空")
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
     * 部门大象群
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<DxGroupVO> groups;

    /**
     * 是否删除子组织节点
     */
    private Boolean deleteAllChild;
}
