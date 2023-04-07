package com.sankuai.avatar.web.transfer.application;

import com.sankuai.avatar.common.utils.ObjectUtils;
import com.sankuai.avatar.web.constant.RoleAdminEnum;
import com.sankuai.avatar.web.dto.application.ApplicationRoleAdminDTO;
import com.sankuai.avatar.web.vo.application.ApplicationRoleAdminVO;
import com.sankuai.avatar.web.vo.application.ApplicationRoleUserAdminVO;
import com.sankuai.avatar.web.vo.application.ApplicationRoleVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-17 10:58
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ApplicationRoleAdminVOTransfer {

    ApplicationRoleAdminVOTransfer INSTANCE = Mappers.getMapper(ApplicationRoleAdminVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param dto dto
     * @return {@link ApplicationRoleAdminVO}
     */
    @Named("toVO")
    ApplicationRoleAdminVO toVO(ApplicationRoleAdminDTO dto);

    /**
     * 批量转换 DTO -> VO
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link ApplicationRoleAdminVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<ApplicationRoleAdminVO> toVOList(List<ApplicationRoleAdminDTO> dtoList);

    /**
     * VO -> DTO
     *
     * @param vo vo
     * @return {@link ApplicationRoleAdminDTO}
     */
    @Mapping(target = "id", ignore = true)
    @Named("toDTO")
    ApplicationRoleAdminDTO toDTO(ApplicationRoleAdminVO vo);

    /**
     * DTO -> VO
     *
     * @param dto dto
     * @return {@link ApplicationRoleUserAdminVO}
     */
    @Mapping(source = "opAdmin", target = "opRoleAdmin", qualifiedByName = "toOpAdmin")
    @Mapping(source = "epAdmin", target = "epRoleAdmin", qualifiedByName = "toEpAdmin")
    @Named("toRoleVO")
    ApplicationRoleUserAdminVO toRoleVO(ApplicationRoleAdminDTO dto);

    /**
     * 转OpAdmin
     *
     * @param admin admin
     * @return {@link ApplicationRoleVO}
     */
    @Named("toOpAdmin")
    default ApplicationRoleVO toOpAdmin(String admin){
        ApplicationRoleVO roleVO = new ApplicationRoleVO();
        roleVO.setRole(RoleAdminEnum.OP_ADMIN);
        roleVO.setRoleUsers(ObjectUtils.null2Empty(admin));
        roleVO.setAdmins(Collections.emptyList());
        return roleVO;
    }

    /**
     * 转EpAdmin
     *
     * @param admin admin
     * @return {@link ApplicationRoleVO}
     */
    @Named("toEpAdmin")
    default ApplicationRoleVO toEpAdmin(String admin){
        ApplicationRoleVO roleVO = new ApplicationRoleVO();
        roleVO.setRole(RoleAdminEnum.EP_ADMIN);
        roleVO.setRoleUsers(ObjectUtils.null2Empty(admin));
        roleVO.setAdmins(Collections.emptyList());
        return roleVO;
    }

}
