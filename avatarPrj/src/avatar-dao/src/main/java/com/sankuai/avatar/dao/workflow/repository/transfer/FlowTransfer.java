package com.sankuai.avatar.dao.workflow.repository.transfer;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowEntity;
import com.sankuai.avatar.dao.workflow.repository.model.FlowDO;
import com.sankuai.avatar.dao.workflow.repository.model.FlowTemplateTask;
import com.sankuai.avatar.dao.workflow.repository.request.FlowPublicDataUpdateRequest;
import com.sankuai.avatar.dao.workflow.repository.utils.PicklerDataUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowTransfer {
    FlowTransfer INSTANCE = Mappers.getMapper(FlowTransfer.class);

    @Mapping(target = "tasks", ignore = true)
    @Mapping(source = "config", target = "config", qualifiedByName = "toEntityData")
    @Mapping(source = "input", target = "input", qualifiedByName = "toEntityData")
    @Mapping(target = "logs", ignore = true)
    @Named("toEntity")
    FlowEntity toEntity(FlowDO flowDO);

    /**
     * to entity
     *
     * @param flowEntity {@link FlowEntity}
     * @return entity
     */
    @Mapping(target = "input", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "logs", ignore = true)
    @Mapping(target = "config", ignore = true)
    @Mapping(target = "publicData", ignore = true)
    FlowDO toDO(FlowEntity flowEntity);

    @IterableMapping(qualifiedByName = "toEntity")
    @Named("toEntityList")
    List<FlowEntity> toEntityList(List<FlowDO> doList);

    /**
     * FlowPublicDataUpdateRequest to FlowDO
     *
     * @param request {@link FlowPublicDataUpdateRequest}
     * @return {@link FlowDO}
     */
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "config", ignore = true)
    @Mapping(target = "logs", ignore = true)
    @Mapping(source = "flowId", target = "id")
    @Mapping(source = "publicData", target = "publicData", qualifiedByName = "toPicklerData")
    FlowDO publicDataUpdateRequestToDO(FlowPublicDataUpdateRequest request);


    @Named("toPicklerData")
    static byte[] getPicklerData(Object obj) {
        Map<String, Object> map = JsonUtil.beanToMap(obj);
        return PicklerDataUtils.picklerData(map);
    }

    @Named("toPicklerTaskData")
    static byte[] getPicklerTaskData(List<FlowTemplateTask> tasks) {
        List<Map<String, Object>> taskMapList = new java.util.ArrayList<>(Collections.emptyList());
        for (FlowTemplateTask task : tasks) {
            Map<String, Object> map = JsonUtil.beanToMap(task);
            taskMapList.add(map);
        }
        return PicklerDataUtils.picklerData(taskMapList);
    }

    @Named("toEntityData")
    default Object toEntityData(byte[] data){
        return PicklerDataUtils.unPicklerData(data, Object.class);
    }

}
