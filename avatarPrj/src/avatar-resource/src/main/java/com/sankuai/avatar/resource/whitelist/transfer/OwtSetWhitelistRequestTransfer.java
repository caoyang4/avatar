package com.sankuai.avatar.resource.whitelist.transfer;

import com.sankuai.avatar.dao.resource.repository.request.OwtSetWhitelistRequest;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.resource.whitelist.request.OwtSetWhitelistRequestBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

/**
 * owt-set 请求对象转换
 * @author caoyang
 * @create 2022-10-27 20:08
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OwtSetWhitelistRequestTransfer {
    OwtSetWhitelistRequestTransfer INSTANCE = Mappers.getMapper(OwtSetWhitelistRequestTransfer.class);

    /**
     * BO请求对象转换
     *
     * @param owtSetWhitelistRequestBO BO
     * @return {@link OwtSetWhitelistRequest}
     */
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteApp")
    @Named("toOwtSetWhitelistRequest")
    OwtSetWhitelistRequest toOwtSetWhitelistRequest(OwtSetWhitelistRequestBO owtSetWhitelistRequestBO);

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
