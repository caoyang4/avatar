package com.sankuai.avatar.web.transfer;

import com.sankuai.avatar.capacity.dto.CapacityDTO;
import com.sankuai.avatar.web.dal.entity.CapacityDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenxinli
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface CapacityTransfer {
    CapacityTransfer INSTANCE = Mappers.getMapper(CapacityTransfer.class);


    @Mapping(target = "isWhite", expression = "java(capacityDO.isCapacityWhite())")
    @Mapping(target = "expireTime", expression = "java(capacityDO.getCapacityWhiteExpireTime())")
    @Mapping(target = "whiteReason", expression = "java(capacityDO.getCapacityWhiteReason())")
    @Mapping(source = "capacityDO.setName", target = "cellName")
    @Named("toDTO")
    CapacityDTO toDTO(CapacityDO capacityDO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<CapacityDTO> toDTOs(List<CapacityDO> capacityDOS);

    @Mapping(source = "capacityDTO.cellName", target = "setName")
    @Mapping(target = "id", ignore = true)
    @Named("toDO")
    CapacityDO toDO(CapacityDTO capacityDTO);

    @IterableMapping(qualifiedByName = "toDO")
    List<CapacityDO> toDOs(List<CapacityDTO> capacityDTOS);

}