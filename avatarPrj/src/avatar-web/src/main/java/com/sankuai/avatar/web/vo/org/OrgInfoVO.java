package com.sankuai.avatar.web.vo.org;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.sankuai.avatar.sdk.entity.servicecatalog.OrgInfo;
import com.sankuai.avatar.sdk.entity.servicecatalog.User;
import com.sankuai.avatar.web.vo.orgRole.DxGroupVO;
import com.sankuai.avatar.web.vo.orgRole.OrgRoleAdminTreeVO;
import com.sankuai.avatar.web.vo.user.DxUserVO;
import lombok.Data;

import java.util.List;

/**
 * @author Jie.li.sh
 **/
@Data
public class OrgInfoVO {
    /**
     * 服务数
     */
    Integer appKeyCount;
    /**
     * 应用数
     */
    Integer applicationCount;
    /**
     * 负责人
     */
    User leader;
    /**
     * 运维负责人
     */

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


    public OrgInfoVO(OrgInfo orgInfo) {
        this.appKeyCount = orgInfo.getAppKeyCount();
        this.applicationCount = orgInfo.getApplicationCount();
        this.leader = orgInfo.getLeader();
    }
}
