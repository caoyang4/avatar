package com.sankuai.avatar.web.vo.application;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.sankuai.avatar.web.constant.RoleAdminEnum;
import com.sankuai.avatar.web.vo.user.DxUserVO;
import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-17 10:40
 */
@Data
public class ApplicationRoleVO {

    /**
     *   角色
     */
    private RoleAdminEnum role;

    /**
     *   角色负责人
     */
    private String roleUsers;

    /**
     * 运维负责人
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<DxUserVO> admins;

}
