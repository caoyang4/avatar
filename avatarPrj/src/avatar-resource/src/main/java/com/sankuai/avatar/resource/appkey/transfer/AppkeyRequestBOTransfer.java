package com.sankuai.avatar.resource.appkey.transfer;

import com.sankuai.avatar.client.soa.request.IsoltAppkeyRequest;
import com.sankuai.avatar.resource.appkey.request.IsoltAppkeyRequestBO;
import com.sankuai.avatar.resource.appkey.request.AppkeyRequestBO;
import com.sankuai.avatar.resource.appkey.request.AppkeySearchRequestBO;
import com.sankuai.avatar.resource.appkey.request.AppkeySrvsQueryRequest;
import com.sankuai.avatar.resource.appkey.request.AppkeyTreeQueryRequestBO;
import com.sankuai.avatar.dao.es.request.AppkeyQueryRequest;
import com.sankuai.avatar.dao.es.request.AppkeyTreeRequest;
import com.sankuai.avatar.dao.es.request.UserAppkeyRequest;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * @author caoyang
 * @create 2022-12-14 10:07
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyRequestBOTransfer {

    AppkeyRequestBOTransfer INSTANCE = Mappers.getMapper(AppkeyRequestBOTransfer.class);

    /**
     * request 转换
     *
     * @param requestBO requestBO
     * @return {@link AppkeyRequest}
     */
    @Named("toAppkeyRequest")
    AppkeyRequest toAppkeyRequest(AppkeyRequestBO requestBO);

    /**
     * request 转换
     *
     * @param requestBO requestBO
     * @return {@link IsoltAppkeyRequest}
     */
    @Named("toIsoltAppkeyRequest")
    IsoltAppkeyRequest toIsoltAppkeyRequest(IsoltAppkeyRequestBO requestBO);

    /**
     * request 转换
     *
     * @param requestBO requestBO
     * @return {@link AppkeyQueryRequest}
     */
    @Named("toAppkeyQueryRequest")
    AppkeyQueryRequest toAppkeyQueryRequest(AppkeySearchRequestBO requestBO);

    /**
     * request 转换
     *
     * @param requestBO requestBO
     * @return {@link AppkeyRequest}
     */
    @Named("toAppkeyRequestBO")
    AppkeyRequestBO toAppkeyRequest(AppkeySearchRequestBO requestBO);

    @Mapping(target = "srv", ignore = true)
    @Mapping(target = "orgId", ignore = true)
    @Mapping(target = "billingUnitId", ignore = true)
    @Mapping(target = "compatibleIpv6", ignore = true)
    @Mapping(target = "isLiteset", ignore = true)
    @Mapping(target = "isSet", ignore = true)
    @Named("toAppkeyRequest")
    AppkeyRequestBO toAppkeyRequest(AppkeyTreeQueryRequestBO requestBO);

    @Named("toUserAppkeyRequest")
    UserAppkeyRequest toUserAppkeyRequest(AppkeySrvsQueryRequest request);

    @Named("toTreeRequest")
    AppkeyTreeRequest toTreeRequest(AppkeyTreeQueryRequestBO request);
}
