package com.sankuai.avatar.web.transfer;

import com.sankuai.avatar.capacity.dto.CapacityDTO;
import com.sankuai.avatar.capacity.dto.UtilizationOptimizeDTO;
import com.sankuai.avatar.web.dal.entity.CapacityDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenxinli
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface CapacityDTOTransfer {
    CapacityDTOTransfer INSTANCE = Mappers.getMapper(CapacityDTOTransfer.class);

    UtilizationOptimizeDTO toUtilization(CapacityDTO capacityDTO);
}