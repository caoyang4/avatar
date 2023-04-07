package com.sankuai.avatar.web.transfer.orgRole;

import com.sankuai.avatar.resource.orgRole.bo.DxGroupBO;
import com.sankuai.avatar.web.dto.orgRole.DxGroupDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * DxGroupDTO 转换器
 * @author caoyang
 * @create 2022-11-11 14:17
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface DxGroupDTOTransfer {

    DxGroupDTOTransfer INSTANCE = Mappers.getMapper(DxGroupDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param dxGroupBO dxGroupBO
     * @return {@link DxGroupDTO}
     */
    @Named("toDTO")
    DxGroupDTO toDTO(DxGroupBO dxGroupBO);

    /**
     * 批量抓换BO -> DTO
     *
     * @param dxGroupBOList BOList
     * @return {@link List}<{@link DxGroupDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<DxGroupDTO> toDTOList(List<DxGroupBO> dxGroupBOList);

    /**
     * DTO -> BO
     *
     * @param dxGroupDTO dxGroupDTO
     * @return {@link DxGroupBO}
     */
    @Named("toBO")
    DxGroupBO toBO(DxGroupDTO dxGroupDTO);

    /**
     * 批量转换DTO -> BO
     *
     * @param dxGroupDTOList DTOList
     * @return {@link List}<{@link DxGroupBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<DxGroupBO> toBOList(List<DxGroupDTO> dxGroupDTOList);
}
