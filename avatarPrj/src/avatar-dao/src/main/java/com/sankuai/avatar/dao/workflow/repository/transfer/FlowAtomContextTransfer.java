package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAtomContextEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAtomContextDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextUpdateRequest;
import org.mapstruct.*;
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
public interface FlowAtomContextTransfer {
    FlowAtomContextTransfer INSTANCE = Mappers.getMapper(FlowAtomContextTransfer.class);

    /**
     * FlowAtomContextDO to FlowAtomContextEntity
     *
     * @param flowAtomContextDO {@link FlowAtomContextDO}
     * @return {@link FlowAtomContextEntity}
     */
    FlowAtomContextEntity toEntity(FlowAtomContextDO flowAtomContextDO);

    /**
     * FlowAtomContextDO list to FlowAtomContextEntity list
     *
     * @param flowAtomContextDOList List<FlowAtomContextDO>
     * @return List<FlowAtomContextEntity>
     */
    @IterableMapping(qualifiedByName = "toEntity")
    List<FlowAtomContextEntity> toEntityList(List<FlowAtomContextDO> flowAtomContextDOList);


    /**
     * FlowAtomContextAddRequest to  FlowAtomContextDO
     *
     * @param request {@link FlowAtomContextAddRequest}
     * @return {@link FlowAtomContextDO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    FlowAtomContextDO addRequestToDO(FlowAtomContextAddRequest request);

    /**
     * FlowAtomContextUpdateRequest to  FlowAtomContextDO
     *
     * @param request {@link FlowAtomContextUpdateRequest}
     * @return {@link FlowAtomContextDO}
     */
    @Mapping(target = "retryTimes", ignore = true)
    @Mapping(target = "timeout", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    FlowAtomContextDO updateRequestToDO(FlowAtomContextUpdateRequest request);
}
