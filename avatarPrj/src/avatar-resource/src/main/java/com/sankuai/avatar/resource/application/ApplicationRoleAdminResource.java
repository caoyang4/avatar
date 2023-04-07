package com.sankuai.avatar.resource.application;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.bo.ApplicationRoleAdminBO;
import com.sankuai.avatar.resource.application.request.ApplicationRoleAdminRequestBO;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-11 17:17
 */
public interface ApplicationRoleAdminResource {

    /**
     * 查询
     *
     * @param requestBO requestBO
     * @return {@link PageResponse}<{@link ApplicationRoleAdminBO}>
     */
    PageResponse<ApplicationRoleAdminBO> queryPage(ApplicationRoleAdminRequestBO requestBO);

    /**
     * 查询
     *
     * @param requestBO requestBO
     * @return {@link List}<{@link ApplicationRoleAdminBO}>
     */
    List<ApplicationRoleAdminBO> query(ApplicationRoleAdminRequestBO requestBO);

    /**
     * 保存
     *
     * @param applicationRoleAdminBO applicationRoleAdminBO
     * @return {@link Boolean}
     */
    Boolean save(ApplicationRoleAdminBO applicationRoleAdminBO);

    /**
     * 删除
     *
     * @param requestBO requestBO
     * @return {@link Boolean}
     */
    Boolean deleteByCondition(ApplicationRoleAdminRequestBO requestBO);

}
