package com.sankuai.avatar.resource.capacity.transfer;

import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasCapacityRequest;
import com.sankuai.avatar.resource.capacity.request.AppkeyPaasCapacityRequestBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * AppkeyPaasCapacityRequest 请求对象转换器
 * @author caoyang
 * @create 2022-09-28 19:39
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyPaasCapacityRequestTransfer {
    AppkeyPaasCapacityRequestTransfer INSTANCE = Mappers.getMapper(AppkeyPaasCapacityRequestTransfer.class);

    /**
     * AppkeyPaasCapacityRequestBO -> AppkeyPaasCapacityRequest
     * @param appkeyPaasCapacityRequestBO BO
     * @return AppkeyPaasCapacityRequest
     */
    @Mapping(source = "type", target = "type", qualifiedByName = "java(appkeyPaasCapacityBO.getType().getCapacityType())")
    @Mapping(source = "appkey", target = "clientAppkey")
    @Named("toAppkeyPaasCapacityRequest")
    AppkeyPaasCapacityRequest toAppkeyPaasCapacityRequest(AppkeyPaasCapacityRequestBO appkeyPaasCapacityRequestBO);

}
