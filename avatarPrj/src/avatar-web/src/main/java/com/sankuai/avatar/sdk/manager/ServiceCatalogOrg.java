package com.sankuai.avatar.sdk.manager;

import com.sankuai.avatar.sdk.entity.servicecatalog.Org;
import com.sankuai.avatar.sdk.entity.servicecatalog.OrgInfo;

import java.util.List;

public interface ServiceCatalogOrg {
    /**
     * 根据mis返回对应的组织架构树
     * @param mis 用户名
     * @return List<Org>
     */
    List<Org> listUserOrg(String mis) throws Exception;
    /**
     * 获取组织架构概略信息
     * @return info
     */
    OrgInfo getOrgInfo(String orgIds) throws Exception;

    List<Org> getAllOrg() throws Exception;
}
