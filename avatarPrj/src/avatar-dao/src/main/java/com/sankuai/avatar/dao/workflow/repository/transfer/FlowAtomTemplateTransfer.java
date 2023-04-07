package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAtomTemplateEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAtomTemplateDO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowAtomTemplateTransfer {
    FlowAtomTemplateTransfer INSTANCE = Mappers.getMapper(FlowAtomTemplateTransfer.class);

    /**
     * FlowAtomTemplateDO to FlowAtomTemplateEntity
     *
     * @param flowAtomTemplateDO {@link FlowAtomTemplateDO}
     * @return {@link FlowAtomTemplateEntity}
     */
    FlowAtomTemplateEntity toEntity(FlowAtomTemplateDO flowAtomTemplateDO);

    /**
     * FlowAtomTemplateDO list to FlowAtomTemplateEntity list
     *
     * @param flowAtomTemplateDOList List<FlowAtomTemplateDO>
     * @return List<FlowAtomTemplateEntity>
     */
    @IterableMapping(qualifiedByName = "toEntity")
    List<FlowAtomTemplateEntity> toEntityList(List<FlowAtomTemplateDO> flowAtomTemplateDOList);
}
