package com.sankuai.avatar.web.vo.orgRole;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.sankuai.avatar.web.vo.user.DxUserVO;
import lombok.Data;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-13 16:30
 */
@Data
public class OrgRoleAdminDetailVO {

    /**
     * 运维负责人
     */
    private List<DxUserVO> opAdmins;

    /**
     * 运维负责人配置数据
     */
    private OrgRoleAdminTreeVO opRoleAdmin;

    /**
     * 测试负责人
     */
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<DxUserVO> epAdmins;

    /**
     * 测试负责人配置数据
     */
    private OrgRoleAdminTreeVO epRoleAdmin;

    /**
     * org群信息
     */
    private List<DxGroupVO> dxGroups;

}
