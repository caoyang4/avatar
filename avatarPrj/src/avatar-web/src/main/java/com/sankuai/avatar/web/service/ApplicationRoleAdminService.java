package com.sankuai.avatar.web.service;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.web.dto.application.ApplicationRoleAdminDTO;
import com.sankuai.avatar.web.request.ApplicationRoleAdminPageRequest;

/**
 * @author caoyang
 * @create 2023-01-16 18:51
 */
public interface ApplicationRoleAdminService {

    /**
     * 分页查询
     *
     * @param pageRequest pageRequest
     * @return {@link PageResponse}<{@link ApplicationRoleAdminDTO}>
     */
    PageResponse<ApplicationRoleAdminDTO> getPageApplicationRoleAdmin(ApplicationRoleAdminPageRequest pageRequest);

    /**
     * 根据应用id查询
     *
     * @param applicationId 应用id
     * @return {@link ApplicationRoleAdminDTO}
     */
    ApplicationRoleAdminDTO getByApplicationId(int applicationId);

    /**
     * 根据应用名称查询
     *
     * @param applicationName 应用名称
     * @return {@link ApplicationRoleAdminDTO}
     */
    ApplicationRoleAdminDTO getByApplicationName(String applicationName);

    /**
     * 删除
     *
     * @param pk 主键
     * @return {@link Boolean}
     */
    Boolean deleteApplicationRoleAdminByPk(int pk);

    /**
     * 保存
     *
     * @param dto dto
     * @return {@link Boolean}
     */
    Boolean saveApplicationRoleAdmin(ApplicationRoleAdminDTO dto);

}
