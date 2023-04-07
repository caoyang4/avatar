package com.sankuai.avatar.resource.capacity.transfer;

import com.sankuai.avatar.resource.capacity.request.AppkeyCapacityRequestBO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyCapacityRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * AppkeyCapacityRequest转换器
 * @author caoyang
 * @create 2022-11-03 18:46
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyCapacityRequestTransfer {
    AppkeyCapacityRequestTransfer INSTANCE = Mappers.getMapper(AppkeyCapacityRequestTransfer.class);

    /**
     * 转换为AppkeyCapacityRequest
     *
     * @param appkeyCapacityRequestBO BO
     * @return {@link AppkeyCapacityRequest}
     */
    @Named("toAppkeyCapacityRequest")
    AppkeyCapacityRequest toAppkeyCapacityRequest(AppkeyCapacityRequestBO appkeyCapacityRequestBO);
}
