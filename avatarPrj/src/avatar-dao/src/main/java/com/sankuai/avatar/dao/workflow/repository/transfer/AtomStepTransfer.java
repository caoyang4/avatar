package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.AtomStepEntity;
import com.sankuai.avatar.dao.workflow.repository.model.AtomStepDO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AtomStepTransfer {
    AtomStepTransfer INSTANCE = Mappers.getMapper(AtomStepTransfer.class);

    /**
     * AtomStepDO to AtomStepEntity
     *
     * @param atomStepDO {@link AtomStepDO}
     * @return {@link AtomStepEntity}
     */
    AtomStepEntity toEntity(AtomStepDO atomStepDO);
}
