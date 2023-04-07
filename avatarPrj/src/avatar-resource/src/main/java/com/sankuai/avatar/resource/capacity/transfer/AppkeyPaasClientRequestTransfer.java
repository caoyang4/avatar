package com.sankuai.avatar.resource.capacity.transfer;

import com.sankuai.avatar.resource.capacity.request.AppkeyPaasClientRequestBO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasClientRequest;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * AppkeyPaasClientRequest 请求对象转换器
 * @author caoyang
 * @create 2022-10-11 16:45
 */

@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyPaasClientRequestTransfer {
    AppkeyPaasClientRequestTransfer INSTANCE = Mappers.getMapper(AppkeyPaasClientRequestTransfer.class);

    /**
     * AppkeyPaasClientRequestBO -> AppkeyPaasClientRequest
     * @param appkeyPaasClientRequestBO BO对象
     * @return DO请求查询对象
     */
    @Mapping(source = "appkey", target = "clientAppkey")
    @Named("toAppkeyPaasClientRequest")
    AppkeyPaasClientRequest toAppkeyPaasClientRequest(AppkeyPaasClientRequestBO appkeyPaasClientRequestBO);
}
