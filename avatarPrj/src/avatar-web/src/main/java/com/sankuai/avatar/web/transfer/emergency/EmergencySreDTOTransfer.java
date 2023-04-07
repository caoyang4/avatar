package com.sankuai.avatar.web.transfer.emergency;

import com.sankuai.avatar.resource.emergency.bo.EmergencySreBO;
import com.sankuai.avatar.resource.emergency.request.EmergencySreRequestBO;
import com.sankuai.avatar.web.dto.emergency.EmergencySreDTO;
import com.sankuai.avatar.web.request.EmergencySrePageRequest;
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
public interface EmergencySreDTOTransfer {

    EmergencySreDTOTransfer INSTANCE = Mappers.getMapper(EmergencySreDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param emergencySreBO emergencySreBO
     * @return {@link EmergencySreDTO}
     */
    @Named("toDTO")
    EmergencySreDTO toDTO(EmergencySreBO emergencySreBO);

    /**
     * 批量转换BO -> DTO
     *
     * @param boList boList
     * @return {@link List}<{@link EmergencySreDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<EmergencySreDTO> toDTOList(List<EmergencySreBO> boList);

    /**
     * DTO -> BO
     *
     * @param emergencySreDTO emergencySreDTO
     * @return {@link EmergencySreBO}
     */
    @Named("toBO")
    EmergencySreBO toBO(EmergencySreDTO emergencySreDTO);

    /**
     * 批量转换DTO -> BO
     *
     * @param dtoList dtoList
     * @return {@link List}<{@link EmergencySreBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<EmergencySreBO> toBOList(List<EmergencySreDTO> dtoList);

    @Named("toRequestBO")
    EmergencySreRequestBO toRequestBO(EmergencySrePageRequest request);

}
