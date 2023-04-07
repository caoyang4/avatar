package com.sankuai.avatar.workflow.core.execute.transfer;

import com.sankuai.avatar.workflow.core.execute.atom.AtomContext;
import com.sankuai.avatar.workflow.core.execute.atom.AtomStatus;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowAtomContextEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomContextUpdateRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * AtomContext对象转换
 *
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AtomContextTransfer {
    AtomContextTransfer INSTANCE = Mappers.getMapper(AtomContextTransfer.class);

    /**
     * FlowAtomContextEntity to AtomContext
     *
     * @param entity {@link FlowAtomContextEntity}
     * @return {@link AtomContext}
     */
    @Mapping(target = "atomTemplate", ignore = true)
    @Mapping(target = "name", source = "atomName")
    @Mapping(source = "status", target = "atomStatus", qualifiedByName = "toAtomStatus")
    AtomContext entityToAtomContext(FlowAtomContextEntity entity);

    /**
     * FlowAtomContextEntity list to AtomContext list
     *
     * @param entities List<FlowAtomContextEntity>
     * @return List<AtomContext>
     */
    @IterableMapping(qualifiedByName = "entityToAtomContext")
    List<AtomContext> entitiesToAtomContextList(List<FlowAtomContextEntity> entities);

    /**
     * AtomContext to  FlowAtomContextAddRequest
     *
     * @param atomContext {@link AtomContext}
     * @return FlowAtomContextAddRequest
     */
    @Mapping(source = "atomStatus", target = "status", qualifiedByName = "toAtomStatus")
    @Mapping(source = "name", target = "atomName", qualifiedByName = "toAtomStatus")
    FlowAtomContextUpdateRequest toUpdateRequest(AtomContext atomContext);

    /**
     * status string to AtomStatus
     *
     * @param status 状态
     * @return {@link AtomStatus}
     */
    @Named("toAtomStatus")
    static AtomStatus toAtomStatus(String status) {
        return AtomStatus.getByStatusValue(status);
    }
}
