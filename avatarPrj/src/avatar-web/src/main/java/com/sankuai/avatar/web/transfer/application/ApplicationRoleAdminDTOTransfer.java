package com.sankuai.avatar.web.transfer.application;

import com.sankuai.avatar.resource.application.bo.ApplicationRoleAdminBO;
import com.sankuai.avatar.web.dto.application.ApplicationRoleAdminDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-16 18:38
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ApplicationRoleAdminDTOTransfer {

    ApplicationRoleAdminDTOTransfer INSTANCE = Mappers.getMapper(ApplicationRoleAdminDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param bo bo
     * @return {@link ApplicationRoleAdminBO}
     */
    @Named("toDTO")
    ApplicationRoleAdminDTO toDTO(ApplicationRoleAdminBO bo);

    /**
     * 批量转换BO -> DTO
     *
     * @param boList boList
     * @return {@link List}<{@link ApplicationRoleAdminDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<ApplicationRoleAdminDTO> toDTOList(List<ApplicationRoleAdminBO> boList);

    /**
     * DTO -> BO
     *
     * @param dto dto
     * @return {@link ApplicationRoleAdminBO}
     */
    @Named("toBO")
    ApplicationRoleAdminBO toBO(ApplicationRoleAdminDTO dto);

    /**
     * 批量转换DTO -> BO
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link ApplicationRoleAdminBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<ApplicationRoleAdminBO> toBOList(List<ApplicationRoleAdminDTO> dtoList);

}
