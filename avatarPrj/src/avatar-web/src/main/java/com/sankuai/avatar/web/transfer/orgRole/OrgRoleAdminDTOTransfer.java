package com.sankuai.avatar.web.transfer.orgRole;

import com.sankuai.avatar.resource.orgRole.bo.OrgRoleAdminBO;
import com.sankuai.avatar.web.dto.orgRole.OrgRoleAdminDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * OrgRoleAdmin DTO转换器
 * @author caoyang
 * @create 2022-11-11 14:01
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgRoleAdminDTOTransfer {

    OrgRoleAdminDTOTransfer INSTANCE = Mappers.getMapper(OrgRoleAdminDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param orgRoleAdminBO orgRoleAdminBO
     * @return {@link OrgRoleAdminDTO}
     */
    @Mapping(source = "roleUsers", target = "roleUsers", defaultValue = "")
    @Named("toDTO")
    OrgRoleAdminDTO toDTO(OrgRoleAdminBO orgRoleAdminBO);

    /**
     * 批量转化 BO -> DTO
     *
     * @param orgRoleAdminBOList BOList
     * @return {@link List}<{@link OrgRoleAdminDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<OrgRoleAdminDTO> toDTOList(List<OrgRoleAdminBO> orgRoleAdminBOList);

    /**
     * DTO -> BO
     *
     * @param orgRoleAdminDTO orgRoleAdminDTO
     * @return {@link OrgRoleAdminBO}
     */
    @Named("toBO")
    OrgRoleAdminBO toBO(OrgRoleAdminDTO orgRoleAdminDTO);

    /**
     * 批量转化 DTO -> BO
     *
     * @param orgRoleAdminDTOList DTOList
     * @return {@link List}<{@link OrgRoleAdminBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<OrgRoleAdminBO> toBOList(List<OrgRoleAdminDTO> orgRoleAdminDTOList);
}
