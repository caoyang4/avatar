package com.sankuai.avatar.web.transfer.whitelist;

import com.sankuai.avatar.resource.whitelist.bo.ServiceWhitelistBO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
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
public interface ServiceWhitelistDTOTransfer {

    ServiceWhitelistDTOTransfer INSTANCE = Mappers.getMapper(ServiceWhitelistDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param serviceWhitelistBO serviceWhitelistBO
     * @return {@link ServiceWhitelistDTO}
     */
    @Named("toDTO")
    ServiceWhitelistDTO toDTO(ServiceWhitelistBO serviceWhitelistBO);

    /**
     * 批量转换BO -> DTO
     *
     * @param serviceWhitelistBOList serviceWhitelistBOList
     * @return {@link List}<{@link ServiceWhitelistDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTOList")
    @Named("toDTOList")
    List<ServiceWhitelistDTO> toDTOList(List<ServiceWhitelistBO> serviceWhitelistBOList);

    /**
     * DTO -> BO
     *
     * @param serviceWhitelistDTO serviceWhitelistDTO
     * @return {@link ServiceWhitelistBO}
     */
    @Named("toDTO")
    ServiceWhitelistBO toBO(ServiceWhitelistDTO serviceWhitelistDTO);

    /**
     * 批量转换DTO -> BO
     *
     * @param serviceWhitelistDTOList serviceWhitelistDTOList
     * @return {@link List}<{@link ServiceWhitelistBO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<ServiceWhitelistBO> toBOList(List<ServiceWhitelistDTO> serviceWhitelistDTOList);
}
