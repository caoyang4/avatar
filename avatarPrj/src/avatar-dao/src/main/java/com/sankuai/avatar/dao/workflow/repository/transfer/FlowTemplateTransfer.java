package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowTemplateEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowTemplateTransfer {
    FlowTemplateTransfer INSTANCE = Mappers.getMapper(FlowTemplateTransfer.class);

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "config", ignore = true)
    FlowTemplateEntity toEntity(FlowTemplateDO flowTemplateDO);
}
