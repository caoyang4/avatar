package com.sankuai.avatar.web.transfer.orgRole;

import com.sankuai.avatar.web.dto.orgRole.DxGroupDTO;
import com.sankuai.avatar.web.vo.orgRole.DxGroupVO;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * DxGroupVO 转换器
 * @author caoyang
 * @create 2022-11-11 14:23
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface DxGroupVOTransfer {
    DxGroupVOTransfer INSTANCE = Mappers.getMapper(DxGroupVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param dxGroupDTO dxGroupDTO
     * @return {@link DxGroupVO}
     */
    @Mapping(source = "groupId", target = "groupId", qualifiedByName = "formatDxGroup")
    @Named("toVO")
    DxGroupVO toVO(DxGroupDTO dxGroupDTO);

    /**
     * 批量转换DTO -> VO
     *
     * @param dxGroupDTOList DTOList
     * @return {@link List}<{@link DxGroupVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<DxGroupVO> toVOList(List<DxGroupDTO> dxGroupDTOList);

    /**
     * VO -> DTO
     *
     * @param dxGroupVO dxGroupVO
     * @return {@link DxGroupDTO}
     */
    @Mapping(target = "updateUser", ignore = true)
    @Named("toDTO")
    DxGroupDTO toDTO(DxGroupVO dxGroupVO);

    /**
     * 批量转换VO -> DTO
     *
     * @param dxGroupVOList VOList
     * @return {@link List}<{@link DxGroupDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<DxGroupDTO> toDTOList(List<DxGroupVO> dxGroupVOList);

    /**
     * 格式dx群
     *
     * @param groupId 群id
     * @return {@link String}
     */
    @Named("formatDxGroup")
    default String formatDxGroup(String groupId){
        return StringUtils.isNotEmpty(groupId)
                ? String.format("https://x.sankuai.com/chat/%s?type=groupchat", groupId)
                : "";
    }
}
