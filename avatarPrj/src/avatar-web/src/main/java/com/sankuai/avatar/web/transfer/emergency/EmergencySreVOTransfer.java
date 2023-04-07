package com.sankuai.avatar.web.transfer.emergency;

import com.sankuai.avatar.web.dto.emergency.EmergencySreDTO;
import com.sankuai.avatar.web.vo.emergency.EmergencySreVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-02-01 18:11
 */

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface EmergencySreVOTransfer {

    EmergencySreVOTransfer INSTANCE = Mappers.getMapper(EmergencySreVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param emergencySreDTO emergencySreDTO
     * @return {@link EmergencySreVO}
     */
    @Named("toVO")
    EmergencySreVO toVO(EmergencySreDTO emergencySreDTO);

    /**
     * 批量转换 DTO -> VO
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link EmergencySreVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<EmergencySreVO> toVOList(List<EmergencySreDTO> dtoList);

    /**
     * DTO -> BO
     *
     * @param emergencySreVO emergencySreVO
     * @return {@link EmergencySreDTO}
     */
    @Named("toDTO")
    EmergencySreDTO toDTO(EmergencySreVO emergencySreVO);

    /**
     * 批量转换VO -> DTO
     *
     * @param voList voList
     * @return {@link List}<{@link EmergencySreDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<EmergencySreDTO> toDTOList(List<EmergencySreVO> voList);

}
