package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditNodeEntity;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowAuditRecordEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAuditNodeDO;
import com.sankuai.avatar.dao.workflow.repository.model.FlowAuditRecordDO;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditRecordAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAuditRecordUpdateRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 流程审核操作记录转换器
 *
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowAuditRecordTransfer {
    FlowAuditRecordTransfer INSTANCE = Mappers.getMapper(FlowAuditRecordTransfer.class);

    /**
     * FlowAuditChainNodeAddRequest to FlowAuditChainNodeDO
     *
     * @param request {@link FlowAuditRecordAddRequest}
     * @return {@link FlowAuditNodeDO}
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    FlowAuditRecordDO addRequestToDO(FlowAuditRecordAddRequest request);

    /**
     * FlowAuditChainNodeUpdateRequest to FlowAuditChainNodeDO
     *
     * @param request {@link FlowAuditRecordUpdateRequest}
     * @return {@link FlowAuditNodeDO}
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    FlowAuditRecordDO updateRequestToDO(FlowAuditRecordUpdateRequest request);

    /**
     * FlowAuditChainNodeDO to FlowAuditChainNodeEntity
     *
     * @param flowAuditChainDO {@link FlowAuditRecordDO}
     * @return {@link FlowAuditNodeEntity}
     */
    FlowAuditRecordEntity doToEntity(FlowAuditRecordDO flowAuditChainDO);

    /**
     * List<FlowAuditChainNodeDO> to  List<FlowAuditChainNodeEntity>
     *
     * @param flowAuditChainDOList List<FlowAuditChainNodeDO>
     * @return List<FlowAuditChainNodeEntity>
     */
    @IterableMapping(qualifiedByName = "doToEntity")
    List<FlowAuditRecordEntity> toEntityList(List<FlowAuditRecordDO> flowAuditChainDOList);

}
