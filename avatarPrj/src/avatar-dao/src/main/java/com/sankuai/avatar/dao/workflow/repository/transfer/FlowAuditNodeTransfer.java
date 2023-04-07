package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditNodeEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAuditNodeDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditNodeUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataUpdateRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 流程审核节点转换器
 *
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowAuditNodeTransfer {
    FlowAuditNodeTransfer INSTANCE = Mappers.getMapper(FlowAuditNodeTransfer.class);

    /**
     * FlowAuditChainNodeAddRequest to FlowAuditChainNodeDO
     *
     * @param request {@link FlowAuditNodeAddRequest}
     * @return {@link FlowAuditNodeDO}
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    FlowAuditNodeDO addRequestToDO(FlowAuditNodeAddRequest request);

    /**
     * FlowAuditChainNodeUpdateRequest to FlowAuditChainNodeDO
     *
     * @param request {@link FlowDataUpdateRequest}
     * @return {@link FlowAuditNodeDO}
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    FlowAuditNodeDO updateRequestToDO(FlowAuditNodeUpdateRequest request);

    /**
     * FlowAuditChainNodeDO to FlowAuditChainNodeEntity
     *
     * @param flowAuditNodeDO {@link FlowAuditNodeDO}
     * @return {@link FlowAuditNodeEntity}
     */
    FlowAuditNodeEntity doToEntity(FlowAuditNodeDO flowAuditNodeDO);

    /**
     * List<FlowAuditChainNodeDO> to  List<FlowAuditChainNodeEntity>
     *
     * @param flowAuditNodeDOList List<FlowAuditChainNodeDO>
     * @return List<FlowAuditChainNodeEntity>
     */
    @IterableMapping(qualifiedByName = "doToEntity")
    List<FlowAuditNodeEntity> toEntityList(List<FlowAuditNodeDO> flowAuditNodeDOList);
}
