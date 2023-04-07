package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowDisplayEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowDisplayDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayUpdateRequest;
import org.mapstruct.Mapper;
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
public interface FlowDisplayTransfer {
    FlowDisplayTransfer INSTANCE = Mappers.getMapper(FlowDisplayTransfer.class);

    /**
     * FlowDisplayAddRequest to  FlowDisplayDO
     *
     * @param request {@link FlowDisplayAddRequest}
     * @return {@link FlowDisplayDO}
     */
    FlowDisplayDO addRequestToDO(FlowDisplayAddRequest request);

    /**
     * FlowDisplayUpdateRequest to FlowDisplayDO
     *
     * @param request {@link FlowDisplayUpdateRequest}
     * @return {@link FlowDisplayDO}
     */
    FlowDisplayDO updateRequestToDO(FlowDisplayUpdateRequest request);

    /**
     * FlowDisplayDO to  FlowDisplayEntity
     *
     * @param flowDisplayDO {@link FlowDisplayDO}
     * @return {@link FlowDisplayEntity}
     */
    FlowDisplayEntity doToEntity(FlowDisplayDO flowDisplayDO);
}
