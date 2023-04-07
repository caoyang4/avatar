package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowProcessEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowProcessDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowProcessAddRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowProcessTransfer {
    FlowProcessTransfer INSTANCE = Mappers.getMapper(FlowProcessTransfer.class);

    /**
     * FlowProcessAddRequest to  FlowProcessDO
     *
     * @param request {@link FlowProcessAddRequest}
     * @return {@link FlowProcessDO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    FlowProcessDO addRequestToDO(FlowProcessAddRequest request);

    FlowProcessDO toDO(FlowProcessEntity flowProcessEntity);

    /**
     * FlowProcessDO to  FlowProcessEntity
     *
     * @param flowProcessDO {@link FlowProcessDO}
     * @return {@link FlowProcessEntity}
     */
    FlowProcessEntity toEntity(FlowProcessDO flowProcessDO);

    /**
     * List<FlowProcessDO> to  List<FlowProcessEntity>
     *
     * @param flowProcessDOList List<FlowProcessDO>
     * @return List<FlowProcessEntity>
     */
    @IterableMapping(qualifiedByName = "toEntity")
    List<FlowProcessEntity> toEntityList(List<FlowProcessDO> flowProcessDOList);
}
