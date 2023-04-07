package com.sankuai.avatar.resource.emergency.transfer;

import com.sankuai.avatar.dao.resource.repository.request.EmergencyResourceRequest;
import com.sankuai.avatar.resource.emergency.constant.OperationType;
import com.sankuai.avatar.resource.emergency.request.EmergencyResourceRequestBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

/**
 * 紧急资源类型转换器
 * @author caoyang
 * @create 2022-12-02 21:38
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface EmergencyResourceRequestTransfer {

    EmergencyResourceRequestTransfer INSTANCE = Mappers.getMapper(EmergencyResourceRequestTransfer.class);

    /**
     * bo -> do
     *
     * @param bo bo
     * @return {@link EmergencyResourceRequest}
     */
    @Mapping(source = "operationType", target = "operationType", qualifiedByName = "toOperationType")
    @Named("toEmergencyResourceRequest")
    EmergencyResourceRequest toEmergencyResourceRequest(EmergencyResourceRequestBO bo);

    /**
     * 操作类型枚举转字符串
     *
     * @param type 类型
     * @return {@link String}
     */
    default String toOperationType(OperationType type){
        return Objects.nonNull(type) ? type.name() : null;
    }
}
