package com.sankuai.avatar.web.transfer.appkey;

import com.sankuai.avatar.resource.appkey.request.AppkeySrvsQueryRequest;
import com.sankuai.avatar.resource.appkey.request.IsoltAppkeyRequestBO;
import com.sankuai.avatar.resource.appkey.request.AppkeyTreeQueryRequestBO;
import com.sankuai.avatar.web.request.AppkeyTreeQueryRequest;
import com.sankuai.avatar.web.request.IsoltAppkeyPageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * @author caoyang
 * @create 2023-01-12 11:59
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyQueryRequestTransfer {

    AppkeyQueryRequestTransfer INSTANCE = Mappers.getMapper(AppkeyQueryRequestTransfer.class);

    /**
     * 转换srv查询请求
     *
     * @param request 请求
     * @return {@link AppkeySrvsQueryRequest}
     */
    @Named("toAppkeySrvsQueryRequest")
    AppkeySrvsQueryRequest toAppkeySrvsQueryRequest(AppkeyTreeQueryRequest request);

    /**
     * 转换sc演练服务查询请求
     *
     * @param request 请求
     * @return {@link AppkeySrvsQueryRequest}
     */
    @Named("toIsoltAppkeyRequestBO")
    IsoltAppkeyRequestBO toIsoltAppkeyRequestBO(IsoltAppkeyPageRequest request);

    @Mapping(source = "query", target = "appkey")
    @Named("toTreeRequest")
    AppkeyTreeQueryRequestBO toTreeRequest(AppkeyTreeQueryRequest request);

}
