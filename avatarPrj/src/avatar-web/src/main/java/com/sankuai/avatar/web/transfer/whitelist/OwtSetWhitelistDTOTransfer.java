package com.sankuai.avatar.web.transfer.whitelist;

import com.sankuai.avatar.resource.whitelist.bo.OwtSetWhitelistBO;
import com.sankuai.avatar.web.dto.whitelist.OwtSetWhitelistDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-10 14:29
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OwtSetWhitelistDTOTransfer {

    OwtSetWhitelistDTOTransfer INSTANCE = Mappers.getMapper(OwtSetWhitelistDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param owtSetWhitelistBO owtSetWhitelistBO
     * @return {@link OwtSetWhitelistDTO}
     */
    @Named("toDTO")
    OwtSetWhitelistDTO toDTO(OwtSetWhitelistBO owtSetWhitelistBO);

    /**
     * 批量转换BO -> DTO
     *
     * @param owtSetWhitelistBOList owtSetWhitelistBOList
     * @return {@link List}<{@link OwtSetWhitelistDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTOList")
    @Named("toDTO")
    List<OwtSetWhitelistDTO> toDTOList(List<OwtSetWhitelistBO> owtSetWhitelistBOList);

    /**
     * DTO -> BO
     *
     * @param owtSetWhitelistDTO owtSetWhitelistDTO
     * @return {@link OwtSetWhitelistBO}
     */
    @Named("toBO")
    OwtSetWhitelistBO toBO(OwtSetWhitelistDTO owtSetWhitelistDTO);

    /**
     * 批量转换DTO -> BO
     *
     * @param owtSetWhitelistDTOList owtSetWhitelistDTOList
     * @return {@link List}<{@link OwtSetWhitelistBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<OwtSetWhitelistBO> toBOList(List<OwtSetWhitelistDTO> owtSetWhitelistDTOList);
}
