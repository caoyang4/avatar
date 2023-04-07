package com.sankuai.avatar.web.transfer.emergency;

import com.sankuai.avatar.web.dto.emergency.EmergencyResourceDTO;
import com.sankuai.avatar.web.vo.emergency.EmergencyResourceVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-30 17:18
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface EmergencyResourceVOTransfer {

    EmergencyResourceVOTransfer INSTANCE = Mappers.getMapper(EmergencyResourceVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param dto dto
     * @return {@link EmergencyResourceVO}
     */
    @Named("toVO")
    EmergencyResourceVO toVO(EmergencyResourceDTO dto);

    /**
     * 批量转换 DTO -> VO
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link EmergencyResourceVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<EmergencyResourceVO> toVOList(List<EmergencyResourceDTO> dtoList);

    /**
     * VO -> DTO
     *
     * @param vo vo
     * @return {@link EmergencyResourceDTO}
     */
    @Mapping(target = "id", ignore = true)
    @Named("toDTO")
    EmergencyResourceDTO toDTO(EmergencyResourceVO vo);

    /**
     * 批量转换 VO -> DTO
     *
     * @param voList voList
     * @return {@link List}<{@link EmergencyResourceDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<EmergencyResourceDTO> toDTOList(List<EmergencyResourceVO> voList);


}
