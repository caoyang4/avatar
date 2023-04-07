package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.model.FlowAtomRecordDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomRecordAddRequest;
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
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowAtomRecordTransfer {
    FlowAtomRecordTransfer INSTANCE = Mappers.getMapper(FlowAtomRecordTransfer.class);

    /**
     * FlowAtomRecordAddRequest to FlowAtomRecordDO
     *
     * @param request {@link FlowAtomRecordAddRequest}
     * @return {@link FlowAtomRecordDO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "result.input", target = "input")
    @Mapping(source = "result.output", target = "output")
    @Mapping(source = "result.exception", target = "exception")
    @Mapping(source = "result.duration", target = "duration")
    @Mapping(source = "result.startTime", target = "startTime")
    @Mapping(source = "result.endTime", target = "endTime")
    FlowAtomRecordDO addRequestToDO(FlowAtomRecordAddRequest request);
}
