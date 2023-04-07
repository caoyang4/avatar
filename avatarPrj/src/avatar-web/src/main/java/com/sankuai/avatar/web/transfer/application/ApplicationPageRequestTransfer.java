package com.sankuai.avatar.web.transfer.application;

import com.sankuai.avatar.resource.application.request.ApplicationPageRequestBO;
import com.sankuai.avatar.resource.application.request.ApplicationRoleAdminRequestBO;
import com.sankuai.avatar.web.request.ApplicationRoleAdminPageRequest;
import com.sankuai.avatar.web.request.application.ApplicationPageRequestDTO;
import com.sankuai.avatar.web.request.application.ApplicationPageRequestVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 应用分页请求转换器
 *
 * @author zhangxiaoning07
 * @date 2022/11/24
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ApplicationPageRequestTransfer {

    /**
     * 转换器实例
     */
    ApplicationPageRequestTransfer INSTANCE = Mappers.getMapper(ApplicationPageRequestTransfer.class);

    /**
     * 转换为BO
     *
     * @param pageRequestDTO 页面请求dto
     * @return {@link ApplicationPageRequestBO}
     */
    ApplicationPageRequestBO toBO(ApplicationPageRequestDTO pageRequestDTO);

    /**
     * 转换为DTO
     *
     * @param pageRequestVO 页面请求VO
     * @return {@link ApplicationPageRequestDTO}
     */
    ApplicationPageRequestDTO toDTO(ApplicationPageRequestVO pageRequestVO);

    /**
     * 转换为BO
     *
     * @param request request
     * @return {@link ApplicationRoleAdminRequestBO}
     */
    ApplicationRoleAdminRequestBO toRequestBO(ApplicationRoleAdminPageRequest request);
}
