package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowEventEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowEventDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowEventUpdateRequest;
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
public interface FlowEventTransfer {
    FlowEventTransfer INSTANCE = Mappers.getMapper(FlowEventTransfer.class);

    /**
     * FlowEventAddRequest to  FlowEventDO
     *
     * @param request {@link FlowEventAddRequest}
     * @return {@link FlowEventDO}
     */
    FlowEventDO addRequestToDO(FlowEventAddRequest request);

    /**
     * FlowEventUpdateRequest to FlowEventDO
     *
     * @param request {@link FlowEventUpdateRequest}
     * @return {@link FlowEventDO}
     */
    FlowEventDO updateRequestToDO(FlowEventUpdateRequest request);

    /**
     * FlowEventDO to  FlowEventEntity
     *
     * @param flowEventDO {@link FlowEventDO}
     * @return {@link FlowEventEntity}
     */
    FlowEventEntity doToEntity(FlowEventDO flowEventDO);
}
