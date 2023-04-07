package com.sankuai.avatar.resource.capacity.transfer;

import com.sankuai.avatar.resource.capacity.request.AppkeyPaasStandardClientRequestBO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyPaasStandardClientRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/** AppkeyPaasStandardClientRequest 请求对象转换器
 * @author caoyang
 * @create 2022-10-11 16:53
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyPaasStandardClientRequestTransfer {
    AppkeyPaasStandardClientRequestTransfer INSTANCE = Mappers.getMapper(AppkeyPaasStandardClientRequestTransfer.class);

    /**
     * AppkeyPaasStandardClientRequestRO -> AppkeyPaasStandardClientRequest
     * @param appkeyPaasStandardClientRequestBO RO对象
     * @return DO 查询对象
     */
    @Named("toAppkeyPaasStandardClientRequest")
    AppkeyPaasStandardClientRequest toAppkeyPaasStandardClientRequest(AppkeyPaasStandardClientRequestBO appkeyPaasStandardClientRequestBO);
}
