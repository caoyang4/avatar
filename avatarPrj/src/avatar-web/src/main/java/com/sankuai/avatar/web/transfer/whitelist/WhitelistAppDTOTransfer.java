package com.sankuai.avatar.web.transfer.whitelist;

import com.sankuai.avatar.resource.whitelist.bo.WhitelistAppBO;
import com.sankuai.avatar.web.dto.whitelist.WhitelistAppDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-10 14:30
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface WhitelistAppDTOTransfer {

    WhitelistAppDTOTransfer INSTANCE = Mappers.getMapper(WhitelistAppDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param whitelistAppBO whitelistAppBO
     * @return {@link WhitelistAppDTO}
     */
    @Named("toDTO")
    WhitelistAppDTO toDTO(WhitelistAppBO whitelistAppBO);

    /**
     * 批量转换BO -> DTO
     *
     * @param whitelistAppBOList whitelistAppBOList
     * @return {@link List}<{@link WhitelistAppDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<WhitelistAppDTO> toDTOList(List<WhitelistAppBO> whitelistAppBOList);

}
