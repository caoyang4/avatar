package com.sankuai.avatar.resource.whitelist.transfer;

import com.sankuai.avatar.dao.resource.repository.request.ServiceWhitelistRequest;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.ServiceWhitelistRequestBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

/**
 * 服务白名单请求对象转换
 * @author caoyang
 * @create 2022-10-27 20:12
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ServiceWhitelistRequestTransfer {
    ServiceWhitelistRequestTransfer INSTANCE = Mappers.getMapper(ServiceWhitelistRequestTransfer.class);

    /**
     * BO对象转换
     *
     * @param serviceWhitelistRequestBO BO
     * @return {@link ServiceWhitelistRequest}
     */
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteApp")
    @Named("toServiceWhitelistRequest")
    ServiceWhitelistRequest toServiceWhitelistRequest(ServiceWhitelistRequestBO serviceWhitelistRequestBO);

    /**
     * 白名单类型枚举转字符串
     *
     * @param type 类型
     * @return {@link String}
     */
    @Named("toWhiteApp")
    default String toWhiteApp(WhiteType type){
        return Objects.nonNull(type) ? type.getWhiteType() : null;
    }
}
