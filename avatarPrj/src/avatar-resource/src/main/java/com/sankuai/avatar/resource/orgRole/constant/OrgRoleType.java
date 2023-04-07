package com.sankuai.avatar.resource.orgRole.constant;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sankuai.avatar.resource.orgRole.serializer.OrgRoleTypeSerializer;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * org角色类型
 * @author caoyang
 * @create 2022-11-10 15:26
 */
@Getter
@JsonSerialize(using = OrgRoleTypeSerializer.class)
public enum OrgRoleType {
    /**
     * 部门运维负责人
     */
    OP_ADMIN("op_admin"),
    /**
     * 部门测试负责人
     */
    EP_ADMIN("ep_admin");

    private final String roleType;

    OrgRoleType(String role) {
        this.roleType = role;
    }

    public static OrgRoleType getInstance(String role){
        if (StringUtils.isEmpty(role)) {
            return null;
        }
        return Arrays.stream(OrgRoleType.values()).filter(orgRole -> Objects.equals(orgRole.getRoleType(),role)).findFirst().orElse(null);
    }
}
