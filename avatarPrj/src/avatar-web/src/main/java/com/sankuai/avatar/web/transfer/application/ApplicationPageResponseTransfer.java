package com.sankuai.avatar.web.transfer.application;

import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.bo.ApplicationBO;
import com.sankuai.avatar.resource.application.bo.UserOwnerApplicationBO;
import com.sankuai.avatar.resource.application.request.ScQueryApplicationBO;
import com.sankuai.avatar.web.dto.application.ApplicationDTO;
import com.sankuai.avatar.web.dto.application.ScQueryApplicationDTO;
import com.sankuai.avatar.web.dto.application.UserOwnerApplicationDTO;
import com.sankuai.avatar.web.vo.application.ApplicationQueryVO;
import com.sankuai.avatar.web.vo.application.ApplicationVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 应用分页响应转换器
 *
 * @author zhangxiaoning07
 * @date 2022/11/24
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ApplicationPageResponseTransfer {
    /**
     * 实例
     */
    ApplicationPageResponseTransfer INSTANCE = Mappers.getMapper(ApplicationPageResponseTransfer.class);

    /**
     * 转换为DTO
     *
     * @param boPageResponse bo分页响应对象
     * @return {@link PageResponse}<{@link ApplicationDTO}>
     */
    PageResponse<ApplicationDTO> toDTO(PageResponse<ApplicationBO> boPageResponse);

    /**
     * 转换为VO
     *
     * @param dtoPageResponse dto分页响应对象
     * @return {@link PageResponse}<{@link ApplicationVO}>
     */
    PageResponse<ApplicationVO> toVO(PageResponse<ApplicationDTO> dtoPageResponse);

    /**
     * 转换为DTO
     *
     * @param boPageResponse bo分页响应对象
     * @return {@link PageResponse}<{@link UserOwnerApplicationDTO}>
     */
    PageResponse<UserOwnerApplicationDTO> toUserOwnerApplicationDTOPageResponse(PageResponse<UserOwnerApplicationBO> boPageResponse);

    /**
     * 转换为VO
     *
     * @param dtoPageResponse 分页响应对象
     * @return {@link PageResponse}<{@link ApplicationQueryVO}>
     */
    PageResponse<ApplicationQueryVO> toUserOwnerApplicationVOPageResponse(PageResponse<UserOwnerApplicationDTO> dtoPageResponse);

    /**
     * 转换为DTO
     *
     * @param boPageResponse bo分页响应对象
     * @return {@link PageResponse}<{@link ScQueryApplicationDTO}>
     */
    PageResponse<ScQueryApplicationDTO> toScQueryApplicationDTOPageResponse(PageResponse<ScQueryApplicationBO> boPageResponse);

    /**
     * 转换为VO
     *
     * @param dtoPageResponse 分页响应对象
     * @return {@link PageResponse}<{@link ScQueryApplicationDTO}>
     */
    PageResponse<ApplicationQueryVO> toApplicationVOPageResponse(PageResponse<ScQueryApplicationDTO> dtoPageResponse);
}
