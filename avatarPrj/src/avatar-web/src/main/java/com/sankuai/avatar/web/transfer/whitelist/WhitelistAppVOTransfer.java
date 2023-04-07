package com.sankuai.avatar.web.transfer.whitelist;

import com.sankuai.avatar.web.dto.whitelist.WhitelistAppDTO;
import com.sankuai.avatar.web.vo.whitelist.WhitelistAppVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-10 14:31
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface WhitelistAppVOTransfer {

    WhitelistAppVOTransfer INSTANCE = Mappers.getMapper(WhitelistAppVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param whitelistAppDTO whitelistAppDTO
     * @return {@link WhitelistAppVO}
     */
    @Named("toVO")
    WhitelistAppVO toVO(WhitelistAppDTO whitelistAppDTO);

    /**
     * 批量转换DTO -> VO
     *
     * @param whitelistAppDTOList whitelistAppDTOList
     * @return {@link List}<{@link WhitelistAppVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<WhitelistAppVO> toVOList(List<WhitelistAppDTO> whitelistAppDTOList);
}
