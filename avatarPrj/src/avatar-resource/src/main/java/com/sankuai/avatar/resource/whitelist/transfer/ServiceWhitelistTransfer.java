package com.sankuai.avatar.resource.whitelist.transfer;

import com.sankuai.avatar.dao.resource.repository.model.ServiceWhitelistDO;
import com.sankuai.avatar.resource.whitelist.bo.ServiceWhitelistBO;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * ServiceWhitelist DO - BO 转换器
 * @author caoyang
 * @create 2022-10-27 19:16
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface ServiceWhitelistTransfer {

    ServiceWhitelistTransfer INSTANCE = Mappers.getMapper(ServiceWhitelistTransfer.class);

    /**
     * BO -> DO
     *
     * @param serviceWhitelistBO BO
     * @return {@link ServiceWhitelistDO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addTime", ignore = true)
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteApp")
    @Named("toDO")
    ServiceWhitelistDO toDO(ServiceWhitelistBO serviceWhitelistBO);

    /**
     * 批量转换 BO -> DO
     *
     * @param serviceWhitelistBOList BOList
     * @return {@link List}<{@link ServiceWhitelistDO}>
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<ServiceWhitelistDO> toDOList(List<ServiceWhitelistBO> serviceWhitelistBOList);

    /**
     * DO -> BO
     *
     * @param serviceWhitelistDO DO
     * @return {@link ServiceWhitelistBO}
     */
    @Mapping(source = "app", target = "app", qualifiedByName = "toWhiteType")
    @Named("toBO")
    ServiceWhitelistBO toBO(ServiceWhitelistDO serviceWhitelistDO);

    /**
     * 批量 DO -> BO
     *
     * @param serviceWhitelistDOList DOList
     * @return {@link List}<{@link ServiceWhitelistBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<ServiceWhitelistBO> toBOList(List<ServiceWhitelistDO> serviceWhitelistDOList);

    /**
     * 转换白名单类型枚举
     *
     * @param app 类型
     * @return {@link WhiteType}
     */
    @Named("toWhiteType")
    default WhiteType toWhiteType(String app){
        return Arrays.stream(WhiteType.values()).filter(e -> Objects.equals(e.getWhiteType(), app)).findFirst().orElse(null);
    }

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
